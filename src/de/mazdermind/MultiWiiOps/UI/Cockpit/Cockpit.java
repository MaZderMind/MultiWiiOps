package de.mazdermind.MultiWiiOps.UI.Cockpit;

import java.awt.GridBagLayout;

import javax.swing.JPanel;

import com.horstmann.corejava.GBC;

import de.mazdermind.MultiWiiOps.UI.TestPanel;

public class Cockpit extends JPanel {
	private static final long serialVersionUID = 7924853136747433313L;
	private BearingPanel bearingPanel;
	private StatusPanel statusPanel;
	private AltitudePanel altitudePanel;
	private EnergyPanel voltsPanel;

	public Cockpit() {
		setLayout(new GridBagLayout());
		
		bearingPanel = new BearingPanel();
		add(bearingPanel, 
			new GBC(0, 0)
			.setInsets(5)
			.setSpan(3, 1)
			.setWeight(2, 3)
			.setFill(GBC.BOTH));
		
		statusPanel = new StatusPanel();
		add(statusPanel, 
			new GBC(0, 1)
			.setInsets(5)
			.setSpan(3, 1)
			.setWeight(2, 1.5)
			.setFill(GBC.BOTH));

		voltsPanel = new EnergyPanel();
		add(voltsPanel,
				new GBC(0, 2)
				.setInsets(5)
				.setWeight(2, 0)
				.setFill(GBC.BOTH));

		altitudePanel = new AltitudePanel();
		add(altitudePanel,
				new GBC(1, 2)
				.setInsets(5)
				.setWeight(1.3, 0)
				.setFill(GBC.HORIZONTAL));
		

		TestPanel gpsPanel = new TestPanel("gps");
		add(gpsPanel,
				new GBC(3, 0)
				.setInsets(5)
				.setSpan(1, 3)
				.setWeight(2, 1)
				.setFill(GBC.BOTH));
	}
}
