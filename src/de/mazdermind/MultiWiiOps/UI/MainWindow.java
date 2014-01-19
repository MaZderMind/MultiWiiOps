package de.mazdermind.MultiWiiOps.UI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.ResourceBundle;

import javax.swing.JFrame;

import de.mazdermind.MultiWiiOps.UI.Cockpit.Cockpit;

public class MainWindow extends JFrame {
	private static final long serialVersionUID = 4338065427139820624L;
	private static final ResourceBundle l18n = ResourceBundle.getBundle("l18n.GUI");

	private MainToolbar toolbar;
	private Cockpit content;
	private MainStatusbar statusbar;

	public MainWindow() {
		setTitle(l18n.getString("app"));
		setMinimumSize(new Dimension(900, 600));
		setLayout(new BorderLayout());
		
		toolbar = new MainToolbar();
		add(toolbar, BorderLayout.NORTH);
		
		content = new Cockpit();
		add(content, BorderLayout.CENTER);
		
		statusbar = new MainStatusbar();
		add(statusbar, BorderLayout.SOUTH);
	}
}
