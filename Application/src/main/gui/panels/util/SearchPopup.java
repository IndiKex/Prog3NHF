package main.gui.panels.util;

import javax.swing.BorderFactory;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import main.gui.Window;
import main.gui.config.theme.Theme;

public class SearchPopup extends JPopupMenu implements Refreshable {
	public SearchPopup() {
		Theme theme = Window.getConfig().theme;
		
		this.setBackground(theme.baseColor);
		this.setBorder(BorderFactory.createLineBorder(theme.secondaryColor, 2));
		
		
	}
	
	@Override
	public JMenuItem add(JMenuItem item) {
		Theme theme = Window.getConfig().theme;
		item.setOpaque(false);
		item.setForeground(theme.textColor);
		super.add(item);
		return item;
	}
	
	@Override
	public void refreshColors() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void refreshLanguage() {
		// TODO Auto-generated method stub
		
	}
	
}
