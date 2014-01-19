package de.mazdermind.MultiWiiOps.UI.Cockpit;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JLabel;

public class StatusPanel extends JLabel {
	private static final long serialVersionUID = 5999631872010288870L;
	private StatusLedPanel acc, baro, mag, arm, stable, horizon, gps, sonar, optic, althold, headfree, headadj;

	public StatusPanel() {
		setLayout(new GridLayout(2, 6, 20, 20));
		setOpaque(false);
		
		acc = new StatusLedPanel("ACC", Color.GREEN);
		acc.setEnabled(false);
		add(acc);
		baro = new StatusLedPanel("BARO", Color.GREEN);
		baro.setEnabled(false);
		add(baro);
		mag = new StatusLedPanel("MAG", Color.GREEN);
		add(mag);
		
		arm = new StatusLedPanel("ARM", Color.YELLOW);
		add(arm);
		stable = new StatusLedPanel("STABLE", Color.YELLOW);
		add(stable);
		stable.setEnabled(false);
		horizon = new StatusLedPanel("HORIZON", Color.YELLOW);
		add(horizon);
		
		gps = new StatusLedPanel("GPS", Color.GREEN);
		add(gps);
		sonar = new StatusLedPanel("SONAR", Color.GREEN);
		add(sonar);
		optic = new StatusLedPanel("OPTIC", Color.GREEN);
		add(optic);
		
		althold = new StatusLedPanel("ALTHOLD", Color.YELLOW);
		althold .setEnabled(false);
		add(althold);
		headfree = new StatusLedPanel("HEADFREE", Color.YELLOW);
		add(headfree);
		headadj = new StatusLedPanel("HEADADJ", Color.YELLOW);
		add(headadj);
	}

}
