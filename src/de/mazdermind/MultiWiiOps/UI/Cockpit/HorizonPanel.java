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
import java.awt.image.BufferedImage;
import java.util.logging.Logger;

import javax.swing.JPanel;

public class HorizonPanel extends JPanel {
	private static final Logger log = Logger.getLogger( HorizonPanel.class.getName() );
	private static final long serialVersionUID = -8537524917689154790L;
	public static final Color AIR = new Color(117, 255, 243);
	public static final Color EARTH = new Color(186, 142, 0);

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
		drawIndicators(g2);
	}

	/**
	 * draws the roll and pitch indicators at the bottom and right side
	 * @param g2 Graphics-Context to operate on
	 */
	private void drawIndicators(Graphics2D g2) {
		int
			w = getWidth() - 1,
			h = getHeight() - 1,
			sz = Math.min(w,  h),
			x = (w - sz) / 2,
			y = (h - sz) / 2;

		g2.setColor(Color.BLACK);
		g2.drawLine(x, y+sz, x+sz, y+sz);
		g2.drawLine(x+sz, y, x+sz, y+sz);

		g2.translate(x - 5 + ((roll / 360) * sz), y+sz-8);
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
		gfx.rotate(Math.toRadians(roll), w/2, h/2);

		// paint content
		gfx.setColor(getBackground());
		gfx.fillRect(0, 0, w, h);

		gfx.setColor(getForeground());
		gfx.fillRect(0, (int)(h/2 + ((pitch-180) / 360 * sz)), w, h);

		gfx.dispose();

		// Copy our intermediate image to the screen
		g2.drawImage(img, 0, 0, null);
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
		this.roll = roll % 360;
		if(this.roll < 0) this.roll = 360 + this.roll;
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
		this.pitch = pitch % 360;
		this.repaint();
	}
}
