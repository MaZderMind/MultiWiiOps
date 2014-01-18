package de.mazdermind.MultiWiiOps.UI;

import java.awt.Color;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.JLabel;

public class TestPanel extends JLabel {
	private static final long serialVersionUID = -5887360703631794529L;
	private String name = "";

	public TestPanel() {
		Color bg = new Color(
			(int) (Math.random() * Math.pow(2, 16))
		);
		init(bg);
	}

	public TestPanel(Color bg)
	{
		init(bg);
	}

	public TestPanel(String name)
	{
		this();
		this.name = name + " - ";
	}

	public TestPanel(String name, Color bg)
	{
		this(bg);
		this.name = name + " - ";
	}
	
	private int calculateBrightness(Color c)
	{
	   return (int)Math.sqrt(
	      c.getRed() * c.getRed() * .241 + 
	      c.getGreen() * c.getGreen() * .691 + 
	      c.getBlue() * c.getBlue() * .068);
	}

	private void init(Color bg) {
		setBackground(bg);
		if(calculateBrightness(bg) < 130)
			setForeground(Color.WHITE);
		
		setHorizontalAlignment(CENTER);
		setOpaque(true);
		
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				JLabel label = (JLabel)e.getSource();
				
				label.setText(name + label.getWidth() + "x" + label.getHeight());
			}
		});
	}
	
}
