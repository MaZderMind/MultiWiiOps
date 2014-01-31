package de.mazdermind.MultiWiiOps.UI.Cockpit;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.Polygon;
import java.awt.RenderingHints;
import java.awt.Transparency;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.logging.Logger;

import javax.swing.JPanel;

public class HorizonPanel extends JPanel {
	private static final Logger log = Logger.getLogger( HorizonPanel.class.getName() );
	private static final long serialVersionUID = -8537524917689154790L;
	public static final Color AIR = new Color(183, 225, 236);
	public static final Color EARTH = new Color(118, 86, 57);
	private static final int YSCALE = 4;

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
		setBackground(earth);
		setForeground(air);
		setOpaque(false);

		int xPoly[] = {0, 5, 10};
		int yPoly[] = {0, 10, 0};
		triangle = new Polygon(xPoly, yPoly, xPoly.length);

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
	 * return empty dimensions as preferred size, thus leaving all sizing
	 * up to the layout-manager
	 */
	@Override
	public Dimension getPreferredSize() {
		return new Dimension();
	}

	/**
	 * custom paint routine
	 */
	public void paintComponent(Graphics g) {
		// g is actually a g2; it just doesn't know yet ;)
		Graphics2D g2 = (Graphics2D)g;

		drawHorizon(g2);
		drawArrows(g2);
	}

	/**
	 * draws the roll and pitch indicators at the bottom and right side
	 * @param g2 Graphics-Context to operate on
	 */
	private void drawArrows(Graphics2D g2) {
		int
			w = getWidth() - 1,
			h = getHeight() - 1,
			sz = Math.min(w,  h),
			x = (w - sz) / 2,
			y = (h - sz) / 2;

		g2.setColor(Color.BLACK);
		g2.drawLine(x, y+sz, x+sz, y+sz);
		g2.drawLine(x+sz, y, x+sz, y+sz);

		AffineTransform oldTransform = g2.getTransform();

		g2.translate(x - 5 + ((1 - roll / 360) * sz), y+sz-10);
		g2.fillPolygon(triangle);

		g2.setTransform(oldTransform);

		int myx = x + sz - 10;
		int myy = (int) (y + 5 + ((pitch / 360) * sz));
		g2.rotate(Math.toRadians(-90), myx, myy);
		g2.translate(myx, myy);
		g2.fillPolygon(triangle);
	}

	/**
	 * draws the virtual horizon as rotated and clipped areas
	 * TODO: also draw textual and center markers
	 * @param g2 Graphics-Context to operate on
	 */
	private void drawHorizon(Graphics2D g2) {
		int
			w = getWidth() - 1,
			h = getHeight() - 1,
			sz = Math.min(w,  h) - 3,
			x = (w - sz) / 2,
			y = (h - sz) / 2;

		// code borrowed from https://weblogs.java.net/blog/campbell/archive/2006/07/java_2d_tricker.html
		// thank you, Chris Campbell!

		// Create a translucent intermediate image in which we can perform
		// the soft clipping
		GraphicsConfiguration gc = g2.getDeviceConfiguration();
		BufferedImage img = gc.createCompatibleImage(w, h, Transparency.TRANSLUCENT);
		Graphics2D gfx = img.createGraphics();

		// Clear the image so all pixels have zero alpha
		gfx.setComposite(AlphaComposite.Clear);
		gfx.fillRect(0, 0, w, h);

		// Render our clip shape into the image.  Note that we enable
		// anti-aliasing to achieve the soft clipping effect.  Try
		// commenting out the line that enables anti-aliasing, and
		// you will see that you end up with the usual hard clipping.
		gfx.setComposite(AlphaComposite.Src);
		gfx.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		gfx.setColor(Color.WHITE);
		gfx.fillOval(x, y, sz, sz);

		// Here's the trick... We use SrcAtop, which effectively uses the
		// alpha value as a coverage value for each pixel stored in the
		// destination.  For the areas outside our clip shape, the destination
		// alpha will be zero, so nothing is rendered in those areas.  For
		// the areas inside our clip shape, the destination alpha will be fully
		// opaque, so the full color is rendered.  At the edges, the original
		// anti-aliasing is carried over to give us the desired soft clipping
		// effect.
		gfx.setComposite(AlphaComposite.SrcAtop);
		gfx.rotate(Math.toRadians(180 - roll), w/2, h/2);

		// paint content
		drawHorizonContent(x, w, h, sz, gfx);

		gfx.dispose();

		// Copy our intermediate image to the screen
		g2.drawImage(img, 0, 0, null);
	}

	private void drawHorizonContent(int x, int w, int h, int sz, Graphics2D gfx) {
		gfx.setColor(getBackground());
		gfx.fillRect(0, 0, w, h);

		gfx.setColor(getForeground());
		gfx.fillRect(0, 0, w, (int)(h/2 + ((pitch-180) / 360 * sz * YSCALE)));

		GraphicsConfiguration gc = gfx.getDeviceConfiguration();
		BufferedImage imgText = gc.createCompatibleImage(w, h, Transparency.TRANSLUCENT);
		Graphics2D gfxText = imgText.createGraphics();

		// anti alias is working when painting on gfx but not on gfxText
		gfxText.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		gfxText.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

		float h360 = (float)sz/360*YSCALE;
		int w2 = sz/2, h2 = sz/2;
		int w4 = sz/4, w43 = w4*3;
		int w3 = sz/3, w32 = w3*2;

		gfx.setColor(Color.WHITE);
		gfxText.setColor(Color.WHITE);

		gfxText.drawLine(x+w3, h/2, x+w32, h/2);

		int angles[] = {150,160,170,190,200,210};
		for (int i = 0; i < angles.length; i++) {
			int angle = angles[i];
			int y = (int)(h360 * (angle - 180)) + h2;

			if(i == 0 || i == angles.length-1)
				gfxText.drawLine(w3+x, y, w32+x, y);
			else
				gfxText.drawLine(w4+x, y, w43+x, y);

			String angleStr = String.valueOf(angle);
			Rectangle2D stringBounds = gfxText.getFontMetrics().getStringBounds(angleStr, gfx);

			gfxText.setComposite(AlphaComposite.Clear);
			gfxText.fillRect(x + w2 - (int)stringBounds.getCenterX() - 5, y - 4 + (int)stringBounds.getCenterY(), (int)stringBounds.getWidth() + 10, (int)stringBounds.getHeight());

			gfxText.setComposite(AlphaComposite.Src);
			gfxText.drawString(angleStr, x + w2 - (int)stringBounds.getCenterX(), y - (int)stringBounds.getCenterY());
		}

		gfxText.dispose();
		gfx.drawImage(imgText, 0, 0, null);
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

	/**
	 * normalize angle value to 0-360°
	 * @param angle any angle, possibly outside of 0-360°
	 * @return normalized value to the 0-350° range
	 */
	private float normalize(float angle) {
		angle = angle % 360;
		if(angle < 0)
			angle += 360;

		return angle;
	}
}
