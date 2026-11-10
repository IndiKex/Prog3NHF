package main.gui.panels.main.menu;

import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import main.gui.Window;
import main.gui.config.theme.Theme;
import main.gui.panels.InvisibleJPanel;
import main.gui.panels.Refreshable;
import main.gui.panels.SidePanelContainer;
import main.gui.panels.main.menu.config.MenuConfigPanel;
import main.gui.panels.main.menu.sidepanels.about.AboutSidePanel;
import main.gui.panels.main.menu.sidepanels.settings.SettingsMenuPanel;
import main.gui.panels.main.menu.sidepanels.user.UserMenuPanel;

public class MenuPanel extends InvisibleJPanel implements Refreshable {
	MenuSideMenus sideMenu = MenuSideMenus.USER;
	
	MenuConfigPanel configPanel;
	
	SidePanelContainer<MenuSideMenus> sidePanelContainer;
	
	public MenuPanel() {
		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		
		configPanel = new MenuConfigPanel(() -> {updateSideMenu();});
		configPanel.setEnumValue(sideMenu);
		this.add(configPanel);
		
		List<JPanel> sidePanels = List.of(
				new UserMenuPanel(),
				new SettingsMenuPanel(),
				new AboutSidePanel()
		);
		
		List<MenuSideMenus> sidePanelIDs = List.of(
				MenuSideMenus.USER,
				MenuSideMenus.SETTINGS,
				MenuSideMenus.ABOUT
		);
		sidePanelContainer = new SidePanelContainer<>(sidePanels, sidePanelIDs);
		sidePanelContainer.switchPanel(sideMenu);
		this.add(sidePanelContainer);
		
	}
	
	private void updateSideMenu() {
		if (configPanel.getEnumValue().equals(sideMenu)) {
			return;
		}
		
		sideMenu = configPanel.getEnumValue();
		sidePanelContainer.switchPanel(sideMenu);
		System.out.println("Menu panel changed to " + sideMenu.name());
	}
	
	public void refreshColors() {
		configPanel.refreshColors();
		sidePanelContainer.refreshColors();
	}
}
