package main.gui.panels.util;

import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JPanel;

import main.gui.Window;
import main.gui.config.theme.Theme;

public class VisibleJPanel extends JPanel implements Refreshable {
	private Theme theme;
	
	public VisibleJPanel() {
		this.setOpaque(false);
		theme = Window.getConfig().theme;
	}
	
	public void refreshColors() {
		theme = Window.getConfig().theme;
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		Graphics2D g2d = (Graphics2D) g;
		
		GradientPaint gp = new GradientPaint(0, getHeight()/2, theme.gradientSecondColor, 60, getHeight()/2, theme.baseColor);
		
		
		int arcSize = Math.min(getWidth() / 2, 100);
		
		g2d.setPaint(gp);
		g2d.fillRoundRect(0, 0, getWidth(), getHeight(), arcSize, arcSize);
		g2d.fillRect(0, 0, arcSize, getHeight());
		
	}
}
