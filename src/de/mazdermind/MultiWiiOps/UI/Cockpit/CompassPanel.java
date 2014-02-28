package de.mazdermind.MultiWiiOps.UI.Cockpit;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.Image;
import java.awt.Polygon;
import java.awt.RenderingHints;
import java.awt.Transparency;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class CompassPanel extends AngularPanel {
	private static final long serialVersionUID = -8263909346345873374L;
	private static final Logger log = Logger.getLogger( CompassPanel.class.getName() );

	/**
	 * default background color (light yellow)
	 */
	public static final Color BACKGROUND = new Color(0xFFFDCC);

	/**
	 * default foreground color (black)
	 */
	public static final Color FOREGROUND = Color.BLACK;

    private static final Map<Integer, String> CARDINAL_NAMES;
    static {
        Map<Integer, String> cardinalNames = new HashMap<Integer, String>();
        cardinalNames.put(0, "N");
        cardinalNames.put(45, "NO");
        cardinalNames.put(90, "O");
        cardinalNames.put(135, "SO");
        cardinalNames.put(180, "S");
        cardinalNames.put(225, "SW");
        cardinalNames.put(270, "W");
        cardinalNames.put(315, "NW");
        CARDINAL_NAMES = Collections.unmodifiableMap(cardinalNames);
    }

	private float heading = 180, northing = 180;
	private boolean hasNorthing;
	private Polygon arrow;

	public CompassPanel() {
		this(false, BACKGROUND, FOREGROUND);
	}

	public CompassPanel(boolean hasNorthing) {
		this(hasNorthing, BACKGROUND, FOREGROUND);
	}

	public CompassPanel(Color bg, Color fg) {
		this(false, bg, fg);
	}

	public CompassPanel(boolean hasNorthing, Color bg, Color fg) {
		super();

		setHasNorthing(hasNorthing);
		setBackground(bg);
		setForeground(fg);

		int[] xPoly = { -2,  0, +2, 0 };
		int[] yPoly = { -2, 10, -2, 0 };
		arrow = new Polygon(xPoly,  yPoly, xPoly.length);

		// experimental mouse input routines to test pitch- & roll display
		final CompassPanel compassPanel = this;
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int d = e.getButton() == MouseEvent.BUTTON1 ? 1 : -1;
				if(e.isShiftDown())
				{
					compassPanel.setNorthing(compassPanel.getNorthing() + 5 * d);
					log.info("northing="+compassPanel.getNorthing());
				}
				else
				{
					compassPanel.setHeading(compassPanel.getHeading() + 5 * d);
					log.info("heading="+compassPanel.getHeading());
				}
			}
		});
	}

	public void paintComponent(Graphics g) {
		// g is actually a g2; it just doesn't know yet ;)
		Graphics2D g2 = (Graphics2D)g;

		// Device-Configuration used to generate the image Layer
		GraphicsConfiguration dc = g2.getDeviceConfiguration();

		// generate layers and merge them onto the canvas
		g2.drawImage(generateBackgroundLayer(dc), 0, 0, null);

		// generate layers and merge them onto the canvas
		g2.drawImage(generateHeadingLayer(dc), 0, 0, null);
	}

	private Image generateBackgroundLayer(GraphicsConfiguration dc) {
		int
			w = getWidth() - 1,
			h = getHeight() - 1,
			sz = Math.min(w,  h),
			x = (w - sz) / 2,
			y = (h - sz) / 2;

		BufferedImage img = dc.createCompatibleImage(w, h, Transparency.TRANSLUCENT);
		Graphics2D gfx = img.createGraphics();

		gfx.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		gfx.setColor(getBackground());
		gfx.fillOval(x, y, sz, sz);

		gfx.dispose();

		return img;
	}

	private Image generateHeadingLayer(GraphicsConfiguration dc) {
		int
			w = getWidth() - 1,
			h = getHeight() - 1,
			sz = Math.min(w,  h),
			sz2 = sz/2,
			x = (w - sz) / 2,
			y = (h - sz) / 2,
			cx = x + sz/2,
			cy = y + sz/2;

		BufferedImage img = dc.createCompatibleImage(w, h, Transparency.TRANSLUCENT);
		Graphics2D gfx = img.createGraphics();

		gfx.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		gfx.setColor(getForeground());
		AffineTransform transform = gfx.getTransform();

		gfx.rotate(Math.toRadians(heading), cx, cy);
		gfx.translate(cx, cy);
		gfx.scale(sz/24.5, sz/24.5);
		gfx.fillPolygon(arrow);

		gfx.setTransform(transform);

		final double deltaAngle = 22.5;
		final int extraTextSpacingX = 5;
		for(float angle = 0; angle < 360; angle += deltaAngle)
		{
			gfx.drawLine(cx, cy + sz2 - 17, cx, cy + sz2);

			int intAngle = Math.round(angle);
			if(hasNorthing && CARDINAL_NAMES.containsKey(intAngle))
			{
				String angleStr = CARDINAL_NAMES.get(intAngle);
				Rectangle2D stringBounds = gfx.getFontMetrics().getStringBounds(angleStr, gfx);

				// clear the area required by the string from the line below it
				gfx.setComposite(AlphaComposite.Clear);
				gfx.fillRect(
					cx - (int)stringBounds.getCenterX() - extraTextSpacingX,
					cy + sz2 - 3 - (int)stringBounds.getCenterY() - (int)stringBounds.getHeight(),
					(int)stringBounds.getWidth() + 2*extraTextSpacingX,
					(int)stringBounds.getHeight()-2
				);

				// draw the text into the cleared area
				gfx.setComposite(AlphaComposite.Src);
				gfx.drawString(
					angleStr,
					cx - (int)stringBounds.getCenterX(),
					cy + sz2 - 7 - (int)stringBounds.getCenterY()
				);
			}

			gfx.rotate(Math.toRadians(deltaAngle), cx, cy);
		}

		gfx.dispose();

		return img;
	}

	public float getHeading() {
		return heading;
	}

	public void setHeading(float heading) {
		this.heading = normalize(heading);
		this.repaint();
	}

	public float getNorthing() {
		return northing;
	}

	public void setNorthing(float northing) {
		this.northing = normalize(northing);
		this.repaint();
	}

	public boolean hasNorthing() {
		return hasNorthing;
	}

	public void setHasNorthing(boolean hasNorthing) {
		this.hasNorthing = hasNorthing;
	}
}
