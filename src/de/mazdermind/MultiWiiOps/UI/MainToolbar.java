package de.mazdermind.MultiWiiOps.UI;

import java.util.ResourceBundle;

import javax.swing.JButton;
import javax.swing.JToolBar;

public class MainToolbar extends JToolBar {
	private static final long serialVersionUID = 7322172070219852403L;
	ResourceBundle l18n = ResourceBundle.getBundle("l18n.GUI");

	public MainToolbar() {
		
		setFloatable(false);
		add(new JButton(l18n.getString("foo")));
	}
}
