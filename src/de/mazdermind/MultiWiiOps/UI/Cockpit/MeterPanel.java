package de.mazdermind.MultiWiiOps.UI.Cockpit;

import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JLabel;

public class MeterPanel extends JLabel {
	private static final long serialVersionUID = 6680192111603842871L;

    public static final int MIN_FONT_SIZE = 3;
    public static final int MAX_FONT_SIZE = 240;

	public MeterPanel() {
		FontLoader.ensureFontsAreLoaded();
		setFont(new Font("Digital-7 Italic", Font.PLAIN, 72));
		setText("50.24m");
		setHorizontalAlignment(RIGHT);
	}
    
    @Override
    public Dimension getPreferredSize() {
    	return new Dimension();
    }
}
