package main.gui.panels.main.menu.sidepanels.settings;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;

import main.gui.Window;
import main.gui.config.lang.Language;
import main.gui.panels.util.Refreshable;

public class SettingsLanguageMenu extends SettingsMenu implements Refreshable {
	private File folder = new File("assets/configs/languages");
	
	
	private class MenuItemActionListener extends AbstractMenuItemActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				Window.getConfig().language = Language.load(file);
				//Window.get_instance().refreshLanguage();
			} catch (IOException err) {
				err.printStackTrace();
			}
		}
	}
	
	public SettingsLanguageMenu(String text) {
		super(text);
	}
	
	public void addMenuItems() {
		super.addMenuItems(folder);
	}

	public AbstractMenuItemActionListener createActionListener() {
		return new MenuItemActionListener();
	}
}
