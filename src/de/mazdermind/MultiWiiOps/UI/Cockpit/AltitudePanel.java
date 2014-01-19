package de.mazdermind.MultiWiiOps.UI.Cockpit;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JPanel;

import de.mazdermind.MultiWiiOps.UI.RoundTestPanel;
import de.mazdermind.MultiWiiOps.UI.TestPanel;

public class AltitudePanel extends JPanel {
	private static final long serialVersionUID = -6180925050383210550L;

	public AltitudePanel() {
		setLayout(new GridLayout(1, 2, 10, 10));
		setOpaque(false);

		add(new RoundTestPanel("ALT"));
		add(new TestPanel("ALT"));

	}
}
