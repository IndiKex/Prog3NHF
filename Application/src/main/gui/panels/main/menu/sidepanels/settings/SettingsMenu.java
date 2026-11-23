package main.gui.panels.main.menu.sidepanels.settings;

import java.awt.Dimension;
import java.awt.Font;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.AbstractAction;
import javax.swing.JMenuItem;

import main.gui.Window;
import main.gui.config.theme.Theme;
import main.gui.panels.util.Refreshable;

public abstract class SettingsMenu extends SideMenu implements Refreshable {
	private List<MenuItem> menuItems = new ArrayList<>();
	private Map<String, File> fileMap = new HashMap<>();
	
	protected abstract class AbstractMenuItemActionListener extends AbstractAction {
		protected File file;
		
		public void setFile(File file) {
			this.file = file;
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
	
	public SettingsMenu(String text) {
		super(text);
	}
	
	public abstract void addMenuItems();
	public abstract AbstractMenuItemActionListener createActionListener();
	
	public void addMenuItems(File folder) {
		loadFiles(folder);
		
		Dimension menuItemSize = new Dimension(120, 40);
		Font font = new Font("Arial", Font.PLAIN, 20);
		
		for (String menuItemLabel : fileMap.keySet()) {
			
			MenuItem menuItem = new MenuItem(menuItemLabel, menuItemSize, font);
			AbstractMenuItemActionListener actionListener = createActionListener();
			actionListener.setFile(fileMap.get(menuItemLabel));
			menuItem.addActionListener(actionListener);
			
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
	
	private void loadFiles(File folder) {
		File[] files = folder.listFiles();
		for (File file : files) {
			fileMap.put(file.getName(), file);
		}
	}
}
