package de.mazdermind.MultiWiiOps.UI.Cockpit;

import java.awt.Dimension;

import javax.swing.JPanel;

public class AngularPanel extends JPanel {
	private static final long serialVersionUID = -3987025138341510396L;

	public AngularPanel() {
		setOpaque(false);
	}

	/**
	 * normalize angle value to 0-360°
	 * @param angle any angle, possibly outside of 0-360°
	 * @return normalized value to the 0-350° range
	 */
	protected float normalize(float angle) {
		angle = angle % 360;
		if(angle < 0)
			angle += 360;

		return angle;
	}

	/**
	 * return empty dimensions as preferred size, thus leaving all sizing
	 * up to the layout-manager
	 */
	@Override
	public Dimension getPreferredSize() {
		return new Dimension();
	}
}
