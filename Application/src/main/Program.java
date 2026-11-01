package main;

import main.gui.GUI;
import main.gui.Window;

public class Program {
	
	GUI gui = new Window();
	
	public void runProgram() {
		initialize();
		run();
	}
	
	void initialize() {
		System.out.println("Initializing program...");
	}
	
	void run() {
		System.out.println("Running program...");
		
		gui.start(() -> {close();});
	}
	
	void close() {
		System.out.println("Closing program...");
	}
	
}
