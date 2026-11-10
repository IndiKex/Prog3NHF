package main;

import main.backend.Backend;
import main.backend.continuos.ContinousBackend;
import main.dbm.DataBaseManager;
import main.gui.GUI;
import main.gui.RequestSender;
import main.gui.Window;

public class Program {
	
	GUI gui = Window.get_instance();
	Backend backend = new ContinousBackend();
	
	public void runProgram() {
		initialize();
		run();
	}
	
	void initialize() {
		System.out.println("Initializing program...");
		
		gui.initialize(() -> {close();});
		RequestSender.backend = backend;
		backend.init();
	}
	
	void run() {
		System.out.println("Running program...");
		
		gui.start();
	}
	
	void close() {
		System.out.println("Closing program...");
		
		gui.close();
		
		backend.close();
		
		System.exit(0);
	}
	
}
