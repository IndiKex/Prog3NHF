package main;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import main.accountSystem.AccountData;
import main.graphics.window.Window;

public class Program {
	AccountData accountData = new AccountData();
	
	public void runProgram() {
		initialize();
		run();
	}
	
	void initialize() {
		System.out.println("Initializing program...");
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
