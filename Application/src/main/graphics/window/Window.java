package main.graphics.window;

import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JPanel;

import main.graphics.components.MComponent;

public class Window {
	private JFrame mainFrame;
	private boolean closed = false;
	
	public void create(String frameName, int width, int height) {
		mainFrame = new JFrame(frameName);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setSize(width, height);
		
	}
	
	public void show() {
		mainFrame.setVisible(true);
	}
	
	public boolean isClosed() {
		return closed;
	}
	
	public JFrame getFrame() {
		return mainFrame;
	}
	
	public void resize(int width, int height) {
		mainFrame.setSize(width, height);
	}
	
	public void turnOnFullscreen() {
		mainFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
	}
	
	public void turnOffFullscreen() {
		mainFrame.setExtendedState(JFrame.NORMAL);
	}
	
	public void update() {
		mainFrame.revalidate();
		mainFrame.repaint();
	}
	
	public void add(JPanel jp) {
		mainFrame.getContentPane().add(jp);
	}
	
	public void add(MComponent mc) {
		mainFrame.getContentPane().add(mc.getComponent());
	}
	
	public void setBackgroundColor(int r, int g, int b) {
		mainFrame.getContentPane().setBackground(new Color(r, g, b));
	}
}
