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

public class TempMainPanel extends VisibleJPanel implements Refreshable {
	public String text;
	private JLabel label;
	
	public TempMainPanel(String text) {
		this.text = text;
		
		this.setLayout(new GridBagLayout());
		
		Theme theme = Window.getConfig().theme;
		this.setBackground(theme.baseColor);
		
		label = new JLabel(text);
		
		label.setFont(new Font("Arial", Font.BOLD, 72));
		label.setForeground(theme.textColor);
		
		this.add(label);
	}
	
	public void refreshColors() {
		super.refreshColors();
		Theme theme = Window.getConfig().theme;
		label.setForeground(theme.textColor);
	}
	
}
