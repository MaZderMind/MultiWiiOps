package de.mazdermind.MultiWiiOps.UI.Cockpit;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagLayout;

import javax.swing.JPanel;

import com.horstmann.corejava.GBC;


public class AltitudePanel extends JPanel {
	private static final long serialVersionUID = -6180925050383210550L;
	private MeterPanel altitude;
	private MeterPanel preasure;

	public AltitudePanel() {
		super();
		setLayout(new GridBagLayout());
		setForeground(new Color(0, 0, 128));
		
		altitude = new MeterPanel(72, 80);
		altitude.setText("52.25 m");
		altitude.setForeground(getForeground());
		add(altitude, new GBC(0, 0)
			.setWeight(1, 1)
			.setFill(GBC.BOTH));
	

		preasure = new MeterPanel(30, 25);
		preasure.setText("2048 mBar");
		preasure.setForeground(getForeground());
		add(preasure, new GBC(0, 1)
			.setWeight(1, 1)
			.setFill(GBC.BOTH));
	}
	
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(0, 125);
	}
}
