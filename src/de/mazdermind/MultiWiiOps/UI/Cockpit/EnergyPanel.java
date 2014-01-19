package de.mazdermind.MultiWiiOps.UI.Cockpit;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagLayout;

import javax.swing.JPanel;

import com.horstmann.corejava.GBC;

public class EnergyPanel extends JPanel {
	private static final long serialVersionUID = 761453573457442065L;

	private MeterPanel voltage, current, power, charge;

	public EnergyPanel() {
		super();
		setLayout(new GridBagLayout());
		setForeground(new Color(0, 183, 0));
		
		voltage = new MeterPanel(72, 80);
		voltage.setText("14.3 V");
		voltage.setForeground(getForeground());
		add(voltage, new GBC(0, 0)
			.setWeight(1, 1)
			.setFill(GBC.BOTH));
		
		setForeground(new Color(193, 132, 0));
		
		current = new MeterPanel(72, 80);
		current.setText("2.35 A");
		current.setForeground(getForeground());
		add(current, new GBC(1, 0)
			.setWeight(1, 1)
			.setFill(GBC.BOTH));

		power = new MeterPanel(30, 25);
		power.setText("33.6 W");
		power.setForeground(getForeground());
		add(power, new GBC(0, 1)
			.setWeight(1, 1)
			.setFill(GBC.BOTH));
		
		charge = new MeterPanel(30, 25);
		charge.setText("1700 mAh");
		charge.setForeground(getForeground());
		add(charge, new GBC(1, 1)
			.setWeight(1, 1)
			.setFill(GBC.BOTH));
	}
	
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(0, 125);
	}
}
