package de.mazdermind.MultiWiiOps.UI.Cockpit;

import java.awt.GridLayout;

import javax.swing.JPanel;

import de.mazdermind.MultiWiiOps.UI.RoundTestPanel;

public class BearingPanel extends JPanel {
	private static final long serialVersionUID = -7197088755218969321L;

	public BearingPanel() {
		setLayout(new GridLayout(1, 2, 2*5, 0));
		setOpaque(false);

		add(new HorizonPanel());
		add(new RoundTestPanel("compass"));
	}
}
