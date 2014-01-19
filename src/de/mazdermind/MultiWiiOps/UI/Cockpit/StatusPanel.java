package de.mazdermind.MultiWiiOps.UI.Cockpit;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JLabel;

import de.mazdermind.MultiWiiOps.UI.TestPanel;

public class StatusPanel extends JLabel {
	private static final long serialVersionUID = 5999631872010288870L;

	public StatusPanel() {
		setLayout(new GridLayout(2, 2, 20, 20));
		setOpaque(false);

		add(new TestPanel("ACC", Color.GREEN));
		add(new TestPanel("BARO", Color.GREEN));
		add(new TestPanel("MAG", Color.GREEN));
		
		add(new TestPanel("ARM", Color.YELLOW));
		add(new TestPanel("STABLE", Color.YELLOW));
		add(new TestPanel("HORIZON", Color.YELLOW));
		
		add(new TestPanel("GPS", Color.GREEN));
		add(new TestPanel("SONAR", Color.GREEN));
		add(new TestPanel("OPTIC", Color.GREEN));
		
		add(new TestPanel("ALTHOLD", Color.YELLOW));
		add(new TestPanel("HEADFREE", Color.YELLOW));
		add(new TestPanel("HEADADJ", Color.YELLOW));
	}

}
