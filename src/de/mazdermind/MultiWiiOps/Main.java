package de.mazdermind.MultiWiiOps;

import java.util.logging.Logger;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.WindowConstants;

import de.mazdermind.MultiWiiOps.IO.IOThread;
import de.mazdermind.MultiWiiOps.UI.MainWindow;

public class Main {
	private static final Logger log = Logger.getLogger( Main.class.getName() );

	public static void main(String[] args) {
		log.finest("main() starting up");
		
		// start IOThread
		log.finer("starting iothread");
		final Thread iothread = new IOThread();
		iothread.start();
		
		// Shutdown-Hook to terminate the background threads
		log.finer("ading shutdown-hook to interrupt iothread on application exit");
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				log.finer("shutdown-hook called, interrupting iothread");
				iothread.interrupt();
			}
		});
		
		// place the UI Code on the event dispatch thread
		log.finer("starting ui thread");
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
            	// setting LAF
            	log.finer("setting native LAF");
            	try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
				} catch (Exception e) {
					//
				}

            	// now we're running on the event dispatch thread and can work on/with the UI
            	log.finer("creating main window");
            	JFrame frame = new MainWindow();
            	frame.setVisible(true);
            	frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            }
        });
        
        log.finest("main() ended");
	}

}
