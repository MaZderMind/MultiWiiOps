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
import java.util.logging.Logger;

public class HorizonPanel extends AngularPanel {
	private static final long serialVersionUID = -8537524917689154790L;
	private static final Logger log = Logger.getLogger( HorizonPanel.class.getName() );

	/**
	 * default air color (light blue)
	 */
	public static final Color AIR = new Color(183, 225, 236);

	/**
	 * default earth color (light brown)
	 */
	public static final Color EARTH = new Color(118, 86, 57);

	/**
	 * PITCH_SCALE=1 would display 360° (-180° to +180°) from top to bottom of the artificial horizon
	 * PITCH_SCALE=4 displays 90° (-45° to 45°).
	 *
	 * The Pitch-Arrow to the right always shows the full 360° scale
	 */
	private static final int PITCH_SCALE = 4;

	private float roll = 180, pitch = 180;
	private Polygon triangle;

	/**
	 * setup and initialize colors to defaults
	 */
	public HorizonPanel() {
		this(EARTH, AIR);
	}

	/**
	 * setup and initialize colors
	 */
	public HorizonPanel(Color earth, Color air) {
		super();
		setBackground(earth);
		setForeground(air);


		int xPoly[] = {0, 5, 10};
		int yPoly[] = {0, 10, 0};
		triangle = new Polygon(xPoly, yPoly, xPoly.length);

		// experimental mouse input routines to test pitch- & roll display
		final HorizonPanel horizonPanel = this;
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int d = e.getButton() == MouseEvent.BUTTON1 ? 1 : -1;
				if(e.isShiftDown())
				{
					horizonPanel.setPitch(horizonPanel.getPitch() + 5 * d);
					log.info("pitch="+horizonPanel.getPitch());
				}
				else
				{
					horizonPanel.setRoll(horizonPanel.getRoll() + 5 * d);
					log.info("roll="+horizonPanel.getRoll());
				}
			}
		});
	}

	/**
	 * Paint all Layers that constitutes the artificial Horizon
	 */
	public void paintComponent(Graphics g) {
		// g is actually a g2; it just doesn't know yet ;)
		Graphics2D g2 = (Graphics2D)g;

		// Device-Configuration used to generate the image Layer
		GraphicsConfiguration dc = g2.getDeviceConfiguration();

		// generate layers and merge them onto the canvas
		g2.drawImage(generateHorizonLayer(dc), 0, 0, null);
		g2.drawImage(generateTextLayer(dc), 0, 0, null);
		g2.drawImage(generateArrowLayer(dc), 0, 0, null);
	}





	/**
	 * Generate an Image-Layer containing the Earth- and the Water-Area, cropped to a circle
	 * @param dc Graphics-Device-Configuration the returned Image should be compatible to
	 * @return Image-Layer with Earth- and the Water-Area
	 */
	private Image generateHorizonLayer(GraphicsConfiguration dc) {
		int
			w = getWidth() - 1,
			h = getHeight() - 1,
			sz = Math.min(w,  h),
			x = (w - sz) / 2,
			y = (h - sz) / 2;

		BufferedImage img = dc.createCompatibleImage(w, h, Transparency.TRANSLUCENT);
		Graphics2D gfx = img.createGraphics();

		gfx.setComposite(AlphaComposite.Src);
		gfx.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		gfx.setColor(Color.WHITE);
		gfx.fillOval(x, y, sz, sz);
		gfx.setComposite(AlphaComposite.SrcAtop);

		gfx.rotate(Math.toRadians(180 - roll), w/2, h/2);

		gfx.setColor(getBackground());
		gfx.fillRect(0, 0, w, h);

		gfx.setColor(getForeground());
		gfx.fillRect(0, 0, w, (int)(h/2 + ((pitch-180) / 360 * sz * PITCH_SCALE)));

		gfx.dispose();

		return img;
	}

	/**
	 * Generate an Image-Layer with the Text inside the Horizon (Degree-Numbers and Lines)
	 * @param dc Graphics-Device-Configuration the returned Image should be compatible to
	 * @return Image-Layer with the Horizon-Text
	 */
	private Image generateTextLayer(GraphicsConfiguration dc) {
		// Useful geometric calculations
		int
			w = getWidth() - 1,    // usable width
			h = getHeight() - 1,   // usable height
			sz = Math.min(w,  h), // center-circle-size
			sz2 = sz/2,            // half the size of the center-circle
			x = (w - sz) / 2,      // horizontal center
			w4 = sz/4, w43 = w4*3, // 1/4 and 3/4 as well as
			w3 = sz/3, w32 = w3*2; // 1/3 and 2/3 of the center circle width

		float h360 = (float)sz/360*PITCH_SCALE;

		// Initialize Layer-Image
		BufferedImage img = dc.createCompatibleImage(w, h, Transparency.TRANSLUCENT);
		Graphics2D gfx = img.createGraphics();

		gfx.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		gfx.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

		// Rotate canvas to matching roll-axis
		gfx.rotate(Math.toRadians(180 - roll), w/2, h/2);

		// draw center-Line
		gfx.setColor(Color.WHITE);
		gfx.drawLine(x+w3, h/2, x+w32, h/2);

		// draw names and lines fot the following angles
		int angles[] = {150,160,170,190,200,210};

		final int extraTextSpacingX = 5;

		for (int i = 0; i < angles.length; i++) {
			int angle = angles[i];

			// y-location of the line
			int txty = (int)(h360 * (angle - 180)) + sz2;

			// first and last line is drawn shorter (1/3 of the available width)
			if(i == 0 || i == angles.length-1)
				gfx.drawLine(w3+x, txty, w32+x, txty);

			// other lines are drawn longer (3/4 of the width)
			else
				gfx.drawLine(w4+x, txty, w43+x, txty);

			// angular string
			String angleStr = Integer.toString(angle);
			Rectangle2D stringBounds = gfx.getFontMetrics().getStringBounds(angleStr, gfx);

			// clear the area required by the string from the line below it
			gfx.setComposite(AlphaComposite.Clear);
			gfx.fillRect(x + sz2 - (int)stringBounds.getCenterX() - extraTextSpacingX, txty + (int)stringBounds.getCenterY(), (int)stringBounds.getWidth() + 2*extraTextSpacingX, (int)stringBounds.getHeight());

			// draw the text into the cleared area
			gfx.setComposite(AlphaComposite.Src);
			gfx.drawString(angleStr, x + sz2 - (int)stringBounds.getCenterX(), txty - (int)stringBounds.getCenterY());
		}

		// return the Layer-Image
		gfx.dispose();
		return img;
	}

	/**
	 * Generate an Image-Layer with the Roll- & Pitch-Lines and the respective Arrows on it
	 * @param dc Graphics-Device-Configuration the returned Image should be compatible to
	 * @return Image-Layer with the lines & arrows painted on it
	 */
	private Image generateArrowLayer(GraphicsConfiguration dc) {
		// Useful geometric calculations
		int
			w = getWidth() - 1,
			h = getHeight() - 1,
			sz = Math.min(w,  h),
			x = (w - sz) / 2,
			y = (h - sz) / 2,
			arrowX, arrowY;

		// Initialize Layer-Image
		BufferedImage img = dc.createCompatibleImage(w, h, Transparency.TRANSLUCENT);
		Graphics2D gfx = img.createGraphics();
		gfx.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		// Draw Lines
		gfx.setColor(Color.BLACK);
		gfx.drawLine(x, y+sz-1, x+sz, y+sz-1);
		gfx.drawLine(x+sz, y, x+sz, y+sz);

		// save Transformation
		AffineTransform oldTransform = gfx.getTransform();

		// Roll-Arrow on the horizontal Arrow
		arrowX = (int) (x - 5 + ((1 - roll / 360) * sz));
		arrowY = y+sz-10;

		// Translate into place and paint
		gfx.translate(arrowX, arrowY);
		gfx.fillPolygon(triangle);

		// restore Transformation
		gfx.setTransform(oldTransform);

		// Pitch-Arrow on the vertical Arrow
		arrowX = x + sz - 10;
		arrowY = (int) (y + 5 + ((pitch / 360) * sz));

		// Translate & Rotate into place and paint
		gfx.rotate(Math.toRadians(-90), arrowX, arrowY);
		gfx.translate(arrowX, arrowY);
		gfx.fillPolygon(triangle);

		// return the Layer-Image
		gfx.dispose();
		return img;
	}




	/**
	 * get the currently displayed roll-value in degrees (0-360°)
	 * @return float roll in degrees
	 */
	public float getRoll() {
		return roll;
	}

	/**
	 * update display to new roll-value in degrees (0-360°) and refresh the display
	 * @param roll new roll in degrees
	 */
	public void setRoll(float roll) {
		this.roll = normalize(roll);
		this.repaint();
	}

	/**
	 * get the currently displayed pitch-value in degrees (0-360°)
	 * @return float pitch in degrees
	 */
	public float getPitch() {
		return pitch;
	}

	/**
	 * update display to new pitch-value in degrees (0-360°) and refresh the display
	 * @param pitch new pitch in degrees
	 */
	public void setPitch(float pitch) {
		this.pitch = normalize(pitch);
		this.repaint();
	}
}
