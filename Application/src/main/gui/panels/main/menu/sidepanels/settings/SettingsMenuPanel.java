package main.gui.panels.main.menu.sidepanels.settings;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import main.gui.Window;
import main.gui.config.lang.Language;
import main.gui.panels.util.Refreshable;
import main.gui.panels.util.VisibleJPanel;

public class SettingsMenuPanel extends VisibleJPanel implements Refreshable {
	JPanel buttonPanel;
	JMenuBar menuBar;
	List<JMenu> menus;
	
	SettingsThemeMenu settingsThemeMenu;
	SettingsLanguageMenu settingsLanguageMenu;
	
	public SettingsMenuPanel() {
		this.setBorder(new EmptyBorder(220, 35, 0, 0)); // top, left, bottom, right
		this.setLayout(new BorderLayout());
		
		initializeButtonPanel();
	}
	
	private void initializeButtonPanel() {
		buttonPanel = new JPanel();
		buttonPanel.setOpaque(false);
		
		initializeMenuBar();
		
		this.add(buttonPanel, BorderLayout.WEST);
	}
	
	private void initializeMenuBar() {
		menuBar = new JMenuBar();
		menuBar.setLayout(new BoxLayout(menuBar, BoxLayout.Y_AXIS));
		menuBar.setOpaque(false);
		menuBar.setBorder(null);
		
		Language lang = Window.getConfig().language;
		
		List<String> menuLabels = List.of(
				lang.get("menuSideSettingsThemeLabel"),
				lang.get("menuSideSettingsLanguageLabel")
		);
		
		List<List<String>> menusItemLabels = List.of(
				List.of("Light", "Dark", "MAV"),
				List.of("English", "Hungarian")
		);
		
		
		addMenus(menuLabels, menusItemLabels);
		
		buttonPanel.add(menuBar);
	}
	
	private void addMenus(List<String> menuLabels, List<List<String>> menusItemLabels) {
		menus = new ArrayList<>();

		settingsThemeMenu = new SettingsThemeMenu(menuLabels.get(0));
		menus.add(settingsThemeMenu);
		settingsThemeMenu.addMenuItems();
		
		settingsLanguageMenu = new SettingsLanguageMenu(menuLabels.get(1));
		menus.add(settingsLanguageMenu);
		settingsLanguageMenu.addMenuItems();
		
		menuBar.add(settingsThemeMenu);
		menuBar.add(Box.createRigidArea(new Dimension(0, 20)));
		menuBar.add(settingsLanguageMenu);
		menuBar.add(Box.createRigidArea(new Dimension(0, 20)));
		
	}
	
	public void refreshColors() {
		super.refreshColors();
		settingsThemeMenu.refreshColors();
		settingsLanguageMenu.refreshColors();
	}
}
