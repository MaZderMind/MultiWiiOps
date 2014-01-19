package de.mazdermind.MultiWiiOps.UI.Cockpit;

import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JLabel;

public class MeterPanel extends JLabel {
	private static final long serialVersionUID = 6680192111603842871L;

    public static final int MIN_FONT_SIZE = 3;
    public static final int MAX_FONT_SIZE = 240;

	private int preferredHeigh;

    public MeterPanel() {
    	this(72, 100);
    }
    
	public MeterPanel(int fontSize, int preferredHeigh) {
		FontLoader.ensureFontsAreLoaded();
		
		this.preferredHeigh = preferredHeigh;

		setFont(new Font("Digital-7 Italic", Font.PLAIN, fontSize));
		setHorizontalAlignment(RIGHT);
	}
    
    @Override
    public Dimension getPreferredSize() {
    	return new Dimension(0, preferredHeigh);
    }
}
