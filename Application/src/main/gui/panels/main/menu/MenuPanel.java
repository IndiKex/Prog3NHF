package main.gui.panels.main.menu;

import javax.swing.BoxLayout;

import main.gui.Window;
import main.gui.config.theme.Theme;
import main.gui.panels.InvisibleJPanel;
import main.gui.panels.main.menu.config.MenuConfigPanel;
import main.gui.panels.main.menu.sidepanels.SidePanelContainer;

public class MenuPanel extends InvisibleJPanel {
	MenuSideMenus sideMenu = MenuSideMenus.USER;
	
	MenuConfigPanel configPanel;
	
	SidePanelContainer sidePanelContainer;
	
	public MenuPanel() {
		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		
		Theme theme = Window.getConfig().theme;
		//this.setBackground(theme.baseColor);
		
		configPanel = new MenuConfigPanel(() -> {updateSideMenu();});
		configPanel.setEnumValue(sideMenu);
		this.add(configPanel);
		
		sidePanelContainer = new SidePanelContainer();
		sidePanelContainer.switchPanel(sideMenu);
		this.add(sidePanelContainer);
		
	}
	
	private void updateSideMenu() {
		if (configPanel.getMenuSideMenu().equals(sideMenu)) {
			return;
		}
		
		sideMenu = configPanel.getMenuSideMenu();
		sidePanelContainer.switchPanel(sideMenu);
		System.out.println("Menu panel changed to " + sideMenu.name());
	}
}
