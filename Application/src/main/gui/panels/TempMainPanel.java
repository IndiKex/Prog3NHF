package main.gui.panels;

import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagLayout;

import javax.swing.JLabel;

import main.gui.Window;
import main.gui.config.theme.Theme;

public class TempMainPanel extends VisibleJPanel {
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
	
}
