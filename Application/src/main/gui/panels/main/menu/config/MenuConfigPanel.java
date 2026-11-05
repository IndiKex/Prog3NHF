package main.gui.panels.main.menu.config;

import java.io.File;
import java.util.List;

import main.gui.Window;
import main.gui.config.lang.Language;
import main.gui.panels.SwitchConfigPanel;
import main.gui.panels.main.menu.MenuSideMenus;

public class MenuConfigPanel extends SwitchConfigPanel<MenuSideMenus> {
	public MenuConfigPanel(Runnable onChooseFunction) {
		Language lang = Window.getConfig().language;
		List<String> buttonTexts = List.of(
				lang.get("menuConfigButtonUser"),
				lang.get("menuConfigButtonSettings"),
				lang.get("menuConfigButtonAbout")
		);
		
		List<File> iconFiles = List.of(
				new File("assets/graphics/menu/user_button_icon.png"),
				new File("assets/graphics/menu/settings_button_icon.png"),
				new File("assets/graphics/menu/about_button_icon.png")
		);
		
		List<MenuSideMenus> buttonEnumValues = List.of(
				MenuSideMenus.USER,
				MenuSideMenus.SETTINGS,
				MenuSideMenus.ABOUT
		);
		
		super(MenuSideMenus.USER, onChooseFunction, buttonTexts, iconFiles, buttonEnumValues);
	}
}
