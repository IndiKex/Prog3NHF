package main.graphics.panels.sidebar;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import main.graphics.window.Window;

public class SideBarPanel extends JPanel {
	
	public SideBarPanel(Window window) {
		setMaximumSize(new Dimension(75, window.getFrame().getHeight()));
		setMinimumSize(new Dimension(75, window.getFrame().getHeight()));
		setPreferredSize(new Dimension(75, window.getFrame().getHeight()));
	}
	
	
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		Graphics2D g2d = (Graphics2D) g;
		
		Color colorLeft = new Color(33,0,99);
		Color colorRight = new Color(23,0,69);
		
		GradientPaint gp = new GradientPaint(0, getHeight()/2, colorLeft, getWidth(), getHeight()/2, colorRight);
		
		g2d.setPaint(gp);
		g2d.fillRect(0,0,getWidth(),getHeight());
	}
}
