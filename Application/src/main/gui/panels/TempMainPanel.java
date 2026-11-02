package main.gui.panels;

import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

import main.gui.Window;
import main.gui.config.theme.Theme;

public class TempMainPanel extends JPanel {
	public String text;
	
	public TempMainPanel(String text) {
		this.text = text;
		
		this.setLayout(new GridBagLayout());
		
		Theme theme = Window.getConfig().theme;
		this.setBackground(theme.baseColor);
		
		JLabel label = new JLabel(text);
		
		label.setFont(new Font("Arial", Font.BOLD, 72));
		label.setForeground(Color.WHITE);
		
		this.add(label);
	}
	
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		Graphics2D g2d = (Graphics2D) g;
		
		Theme theme = Window.getConfig().theme;
		GradientPaint gp = new GradientPaint(0, getHeight()/2, theme.gradientSecondColor, 60, getHeight()/2, theme.baseColor);
		
		g2d.setPaint(gp);
		g2d.fillRect(0, 0, getWidth(), getHeight());
		
	}
	
}
