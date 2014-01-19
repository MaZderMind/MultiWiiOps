package de.mazdermind.MultiWiiOps.UI.Cockpit;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

public class StatusLedPanel extends JPanel {
	private static final long serialVersionUID = 7663175559342904288L;

	public StatusLedPanel(String name, Color color) {
		setBackground(color);
		//setEnabled(false);
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension();
	}
	

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
	}
}
