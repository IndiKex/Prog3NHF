package main;

import main.accountSystem.AccountData;

public class Program {
	AccountData accountData = new AccountData();
	
	public void runProgram() {
		initialize();
		run();
	}
	
	void initialize() {
		System.out.println("Initializing program...");
		DataBaseOrganizer.readData();
	}
	
	void run() {
		System.out.println("Running program...");
		
		GraphicalProgram GR = new GraphicalProgram(this);
		GR.startGraphicalApplication();
	}
	
	void close() {
		System.out.println("Closing program...");
	}
	
}
