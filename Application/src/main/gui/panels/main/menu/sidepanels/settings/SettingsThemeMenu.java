package main.gui.panels.main.menu.sidepanels.settings;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.AbstractAction;
import javax.swing.JMenuItem;

import main.gui.Window;
import main.gui.config.theme.Theme;
import main.gui.panels.Refreshable;

public class SettingsThemeMenu extends SideMenu implements Refreshable {
	private File themesFolder = new File("assets/configs/themes");
	
	private List<MenuItem> menuItems = new ArrayList<>();
	private Map<String, File> themeFileMap = new HashMap<>();
	
	private class MenuItemActionListener extends AbstractAction {
		private File themeFile;
		
		public MenuItemActionListener(File themeFile) {
			this.themeFile = themeFile;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				Window.getConfig().theme = Theme.load(themeFile);
				Window.get_instance().refreshColors();
			} catch (IOException err) {
				err.printStackTrace();
			}
		}
	}
	
	private class MenuItem extends JMenuItem implements Refreshable {
		public MenuItem(String text, Dimension menuItemSize, Font font) {
			super(text);
			this.setBorderPainted(false);
			this.setPreferredSize(menuItemSize);
			this.setFont(font);
			refreshColors();
		}
		
		public void refreshColors() {
			Theme theme = Window.getConfig().theme;
			this.setBackground(theme.baseColor);
			this.setForeground(theme.textColor);
		}
	}
	
	public SettingsThemeMenu(String text) {
		super(text);
	}
	
	public void addMenuItems() {
		loadThemes();
		
		Dimension menuItemSize = new Dimension(120, 40);
		Font font = new Font("Arial", Font.PLAIN, 20);
		
		for (String menuItemLabel : themeFileMap.keySet()) {
			
			MenuItem menuItem = new MenuItem(menuItemLabel, menuItemSize, font);
			menuItem.addActionListener(new MenuItemActionListener(themeFileMap.get(menuItemLabel)));
			
			menuItems.add(menuItem);
			this.add(menuItem);
		}
	}
	
	public void refreshColors() {
		super.refreshColors();
		for (MenuItem menuItem : menuItems) {
			menuItem.refreshColors();
		}
	}
	
	private void loadThemes() {
		File[] themeFiles = themesFolder.listFiles();
		for (File themeFile : themeFiles) {
			themeFileMap.put(themeFile.getName(), themeFile);
		}
	}
}
