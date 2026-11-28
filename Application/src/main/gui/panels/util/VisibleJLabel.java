package main.gui.panels.util;

import java.awt.Font;

import javax.swing.JLabel;

import main.gui.Window;
import main.gui.config.theme.Theme;

public class VisibleJLabel extends JLabel implements Refreshable {
	public VisibleJLabel(String text) {
		super(text);
		this.setOpaque(false);
		
		Font font = new Font("Arial", Font.PLAIN, 20);
		this.setFont(font);
		
		Theme theme = Window.getConfig().theme;
		this.setForeground(theme.textColor);
	}
	
	public void refreshColors() {
		Theme theme = Window.getConfig().theme;
		this.setForeground(theme.textColor);
	}

	@Override
	public void refreshLanguage() {
		// TODO Auto-generated method stub
		
	}
}
