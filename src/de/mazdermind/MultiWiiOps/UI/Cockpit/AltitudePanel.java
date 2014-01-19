package de.mazdermind.MultiWiiOps.UI.Cockpit;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;


public class AltitudePanel extends JPanel {
	private static final long serialVersionUID = -6180925050383210550L;
	private MeterPanel altitude;
	private MeterPanel preasure;

	public AltitudePanel() {
		super();
		setLayout(new BorderLayout());
		setForeground(new Color(0, 0, 128));
		
		altitude = new MeterPanel(72, 80);
		altitude.setText("52.25 m");
		altitude.setForeground(getForeground());
		add(altitude, BorderLayout.CENTER);

		preasure = new MeterPanel(30, 25);
		preasure.setText("2048 mBar");
		preasure.setForeground(getForeground());
		add(preasure, BorderLayout.SOUTH);
	}
	
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(0, 125);
	}
}
