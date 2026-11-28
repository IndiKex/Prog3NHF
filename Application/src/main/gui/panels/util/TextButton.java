package main.gui.panels.util;

import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.border.Border;

import main.gui.Window;
import main.gui.config.theme.Theme;

public class TextButton extends JButton implements Refreshable {
	public TextButton() {
		Font font = new Font("Arial", Font.PLAIN, 20);
		this.setFont(font);

		refreshColors();
	}
	
	public void refreshColors() {
		Theme theme = Window.getConfig().theme;
		
		this.setBackground(theme.baseColor);
		this.setForeground(theme.textColor);
		
		Border border = BorderFactory.createLineBorder(theme.secondaryColor, 3);
		this.setBorder(border);
	}

	@Override
	public void refreshLanguage() {
		// TODO Auto-generated method stub
		
	}
	
}
