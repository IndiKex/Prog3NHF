package main;

import main.backend.Backend;
import main.backend.continuos.ContinousBackend;
import main.gui.GUI;
import main.gui.RequestSender;
import main.gui.Window;

/**
 * Initializes, runs, and closes the application.
 */
public class Program {
	
	GUI gui = Window.get_instance();
	Backend backend = ContinousBackend.get_instance();
	
	/**
	 * Initializes, runs, and closes the application.
	 * Implements API for the outside.
	 */
	public void runProgram() {
		initialize();
		run();
	}
	
	/**
	 * Initializes the application. Starts the backend and gui.
	 */
	private void initialize() {
		System.out.println("Initializing program...");

		backend.init();
		gui.initialize(backend, () -> {close();});
	}
	
	/**
	 * Runs the program. Starts the gui.
	 */
	private void run() {
		System.out.println("Running program...");
		
		gui.start();
	}
	
	/**
	 * Closes the application. Closes gui and backend.
	 */
	private void close() {
		System.out.println("Closing program...");
		
		gui.close();
		
		backend.close();
		
		System.exit(0);
	}
	
}
