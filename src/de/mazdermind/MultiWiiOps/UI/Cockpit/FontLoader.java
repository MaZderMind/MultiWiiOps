package de.mazdermind.MultiWiiOps.UI.Cockpit;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

public class FontLoader {
	private static final Logger log = Logger.getLogger( FontLoader.class.getName() );
	private static boolean fontsLoaded = false;

	public static void ensureFontsAreLoaded()
	{
		if(fontsLoaded) return;
		fontsLoaded = true;

		List<String> fontNames = Arrays.asList(
				"digital-7.ttf",
				"digital-7-italic.ttf",
				"digital-7-mono.ttf",
				"digital-7-mono-italic.ttf");

		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		for (String fontName : fontNames) {
			log.fine("loading font "+fontName);
			try {
				ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, System.class.getResource("/res/font/"+fontName).openStream() ));
			} catch (FontFormatException | IOException | NullPointerException e) {
				log.warning("loading font "+fontName+" failed: "+e.getMessage());
			}
		}
		
		log.fine("available font-names are now: "+  Arrays.asList(ge.getAvailableFontFamilyNames()).toString());
	}
}
