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
		
		Color sensorColor = new Color(0, 255, 32);
		Color statusColor = new Color(255, 192, 32);

		acc = new StatusLedPanel("ACC", sensorColor);
		acc.setEnabled(false);
		add(acc);
		baro = new StatusLedPanel("BARO", sensorColor);
		baro.setEnabled(false);
		add(baro);
		mag = new StatusLedPanel("MAG", sensorColor);
		add(mag);
		
		arm = new StatusLedPanel("ARM", statusColor);
		add(arm);
		stable = new StatusLedPanel("STABLE", statusColor);
		add(stable);
		stable.setEnabled(false);
		horizon = new StatusLedPanel("HORIZON", statusColor);
		add(horizon);
		
		gps = new StatusLedPanel("GPS", sensorColor);
		add(gps);
		sonar = new StatusLedPanel("SONAR", sensorColor);
		add(sonar);
		optic = new StatusLedPanel("OPTIC", sensorColor);
		add(optic);
		
		althold = new StatusLedPanel("ALTHOLD", statusColor);
		althold .setEnabled(false);
		add(althold);
		headfree = new StatusLedPanel("HEADFREE", statusColor);
		add(headfree);
		headadj = new StatusLedPanel("HEADADJ", statusColor);
		add(headadj);
	}

}
