package de.mazdermind.MultiWiiOps.UI.Cockpit;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;

import javax.swing.JLabel;
import javax.swing.JPanel;

import com.sun.corba.se.spi.legacy.connection.GetEndPointInfoAgainException;

public class StatusLedPanel extends JLabel {
	private static final long serialVersionUID = 7663175559342904288L;

	public StatusLedPanel(String text, Color color) {
		setBackground(color);
		setText(text);
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension();
	}
	

	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D)g;
		String text = getText();
		Color color = getBackground();

		Rectangle2D stringBounds = g2d.getFontMetrics().getStringBounds(text, g2d);

		int
			w = getWidth(),
			h = getHeight(),
			tx = (int)((w - stringBounds.getWidth()) / 2),
			ty = (int)(w/5);
		
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		
		g2d.setColor(isEnabled() ? Color.BLACK : Color.DARK_GRAY);
		g2d.fillRoundRect(0, 0, w, h, 10, 10);
		
		g2d.setColor(isEnabled() ? color : color.darker().darker());
		g2d.fillRoundRect(20, (int)(w/3.3), w-35, (int)(h/3.5), 3, 3);
		
		g2d.setColor(isEnabled() ? Color.WHITE : Color.LIGHT_GRAY);
		g2d.drawString(getText(), tx, ty);
	}
}
