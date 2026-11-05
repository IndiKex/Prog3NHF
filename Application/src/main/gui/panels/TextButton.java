package main.gui.panels;

import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.border.Border;

import main.gui.Window;
import main.gui.config.theme.Theme;

public class TextButton extends JButton {
	public TextButton() {
		Font font = new Font("Arial", Font.PLAIN, 20);
		this.setFont(font);

		refreshColors();
	}
	
	private void refreshColors() {
		Theme theme = Window.getConfig().theme;
		
		this.setBackground(theme.baseColor);
		this.setForeground(theme.textColor);
		
		Border border = BorderFactory.createLineBorder(theme.secondaryColor, 3);
		this.setBorder(border);
	}
	
}
