package de.mazdermind.MultiWiiOps.UI.Cockpit;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JLabel;

public class StatusPanel extends JLabel {
	private static final long serialVersionUID = 5999631872010288870L;

	public StatusPanel() {
		setLayout(new GridLayout(2, 6, 20, 20));
		setOpaque(false);

		add(new StatusLedPanel("ACC", Color.GREEN));
		add(new StatusLedPanel("BARO", Color.GREEN));
		add(new StatusLedPanel("MAG", Color.GREEN));
		
		add(new StatusLedPanel("ARM", Color.YELLOW));
		add(new StatusLedPanel("STABLE", Color.YELLOW));
		add(new StatusLedPanel("HORIZON", Color.YELLOW));
		
		add(new StatusLedPanel("GPS", Color.GREEN));
		add(new StatusLedPanel("SONAR", Color.GREEN));
		add(new StatusLedPanel("OPTIC", Color.GREEN));
		
		add(new StatusLedPanel("ALTHOLD", Color.YELLOW));
		add(new StatusLedPanel("HEADFREE", Color.YELLOW));
		add(new StatusLedPanel("HEADADJ", Color.YELLOW));
	}

}
