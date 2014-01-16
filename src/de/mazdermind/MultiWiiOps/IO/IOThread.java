package de.mazdermind.MultiWiiOps.IO;

import java.util.logging.Logger;

public class IOThread extends Thread {
	private static final Logger log = Logger.getLogger( IOThread.class.getName() );

	@Override
	public void run() {
		try {
			while(true) this.loop();
		}
		catch(InterruptedException e) {
			log.info("IOThread interrupted, exitting");
			return;
		}
	}

	private void loop() throws InterruptedException {
		log.fine("IO-Thread running");
		sleep(10000);
	}
}
