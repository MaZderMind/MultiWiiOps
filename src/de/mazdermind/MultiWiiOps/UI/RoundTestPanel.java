package de.mazdermind.MultiWiiOps.UI;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;

public class RoundTestPanel extends TestPanel {
	private static final long serialVersionUID = 4813133879294967482L;

	public RoundTestPanel() {
		super();
		setOpaque(false);
	}

	public RoundTestPanel(Color bg) {
		super(bg);
		setOpaque(false);
	}

	public RoundTestPanel(String label, Color bg) {
		super(label, bg);
		setOpaque(false);
	}

	public RoundTestPanel(String label) {
		super(label);
		setOpaque(false);
	}

	public void paintComponent(Graphics g) {
		Rectangle2D stringBounds = g.getFontMetrics().getStringBounds(getText(), g);
		int
			w = getWidth() - 1,
			h = getHeight() - 1,
			sz = Math.min(w,  h),
			x = (w - sz) / 2,
			y = (h - sz) / 2,
			tx = (int) ((w - stringBounds.getWidth()) / 2),
			ty = (int) ((h + stringBounds.getHeight()) / 2);

		Color bg = getBackground();
		g.setColor(bg);
		g.fillArc(x, y, sz, sz, 0, 360);
		
		g.setColor(getForeground());
		g.drawString(getText(), tx, ty);
   }
}
