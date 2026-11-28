package main.gui.panels.main.menu.sidepanels.settings;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;

import main.gui.Window;
import main.gui.config.theme.Theme;
import main.gui.panels.util.Refreshable;

public class SettingsThemeMenu extends SettingsMenu implements Refreshable {
	private File themesFolder = new File("assets/configs/themes");
	
	
	private class MenuItemActionListener extends AbstractMenuItemActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				Window.getConfig().theme = Theme.load(file);
				Window.get_instance().refreshColors();
			} catch (IOException err) {
				err.printStackTrace();
			}
		}
	}
	
	public SettingsThemeMenu(String text) {
		super(text);
	}
	
	public void addMenuItems() {
		super.addMenuItems(themesFolder);
	}
	
	public AbstractMenuItemActionListener createActionListener() {
		return new MenuItemActionListener();
	}

}
