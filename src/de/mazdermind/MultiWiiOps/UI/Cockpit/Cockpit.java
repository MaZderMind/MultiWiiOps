package de.mazdermind.MultiWiiOps.UI.Cockpit;

import java.awt.GridBagLayout;

import com.horstmann.corejava.GBC;

import de.mazdermind.MultiWiiOps.UI.TestPanel;

public class Cockpit extends TestPanel /*Panel*/ {
	private static final long serialVersionUID = 7924853136747433313L;

	public Cockpit() {
		setLayout(new GridBagLayout());
		
		TestPanel bearingPanel = new TestPanel("bearing");
		add(bearingPanel, 
			new GBC(0, 0)
			.setInsets(5)
			.setSpan(3, 1)
			.setWeight(2, 3)
			.setFill(GBC.BOTH));

		TestPanel voltsPanel = new TestPanel("volts");
		add(voltsPanel,
				new GBC(0, 1)
				.setInsets(5)
				.setWeight(1, 1)
				.setFill(GBC.BOTH));

		TestPanel altitudePanel = new TestPanel("altitude");
		add(altitudePanel,
				new GBC(1, 1)
				.setInsets(5)
				.setWeight(2, 1)
				.setFill(GBC.BOTH));
		

		TestPanel gpsPanel = new TestPanel("gps");
		add(gpsPanel,
				new GBC(3, 0)
				.setInsets(5)
				.setSpan(1, 2)
				.setWeight(1, 1)
				.setFill(GBC.BOTH));
		
	}
}
