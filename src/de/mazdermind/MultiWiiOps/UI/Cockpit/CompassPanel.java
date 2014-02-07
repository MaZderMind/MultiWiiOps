package de.mazdermind.MultiWiiOps.UI.Cockpit;

import java.awt.BasicStroke;
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
import java.awt.image.BufferedImage;
import java.util.logging.Logger;

public class CompassPanel extends AngularPanel {
	private static final long serialVersionUID = -8263909346345873374L;
	private static final Logger log = Logger.getLogger( CompassPanel.class.getName() );

	private float heading = 180, northing = 180;
	private boolean hasNorthing;
	private Polygon arrow;

	public CompassPanel() {
		super();

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
		g2.drawImage(generateHeadingLayer(dc), 0, 0, null);
		//if(hasNorthing)
		//	g2.drawImage(generateNorthingLayer(dc), 0, 0, null);
	}

	private Image generateHeadingLayer(GraphicsConfiguration dc) {
		int
			strokeWidth = 1,
			w = getWidth() - 1,
			h = getHeight() - 1,
			sz = Math.min(w,  h) - strokeWidth - 1,
			x = (w - sz) / 2,
			y = (h - sz) / 2,
			cx = x + sz/2,
			cy = y + sz/2;

		BufferedImage img = dc.createCompatibleImage(w, h, Transparency.TRANSLUCENT);
		Graphics2D gfx = img.createGraphics();

		gfx.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		gfx.setColor(Color.BLACK);

		gfx.setStroke(new BasicStroke(strokeWidth));
		
		gfx.setColor(Color.WHITE);
		gfx.fillOval(x, y, sz, sz);
		
		gfx.setColor(Color.BLACK);
		gfx.drawOval(x, y, sz, sz);

		gfx.rotate(Math.toRadians(heading), cx, cy);
		gfx.translate(cx, cy);
		gfx.scale(sz/20, sz/20);
		gfx.fillPolygon(arrow);

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
