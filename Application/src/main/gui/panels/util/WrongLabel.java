package main.gui.panels.util;

import java.awt.Font;

import main.gui.Window;
import main.gui.config.theme.Theme;

public class WrongLabel extends VisibleJLabel {

	public WrongLabel(String text) {
		super(text);
		
		Theme theme = Window.getConfig().theme;
		Font font = new Font("Arial", Font.BOLD, 15);
		
		this.setForeground(theme.warningColor);
		this.setFont(font);
	}
	
}
