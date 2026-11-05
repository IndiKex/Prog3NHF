package main.gui.panels.main.menu.sidepanels;

import java.awt.CardLayout;
import java.util.List;

import javax.swing.JPanel;

import main.gui.panels.InvisibleJPanel;
import main.gui.panels.main.menu.MenuSideMenus;
import main.gui.panels.main.menu.sidepanels.about.AboutSidePanel;
import main.gui.panels.main.menu.sidepanels.settings.SettingsMenuPanel;
import main.gui.panels.main.menu.sidepanels.user.UserMenuPanel;

public class SidePanelContainer extends InvisibleJPanel {
	List<JPanel> sidePanels;
	
	public SidePanelContainer() {
		this.setLayout(new CardLayout());
		
		sidePanels = List.of(
				new UserMenuPanel(),
				new SettingsMenuPanel(),
				new AboutSidePanel()
		);
		
		for (MenuSideMenus sideMenuID : MenuSideMenus.values()) {
			JPanel panel = sidePanels.get(sideMenuID.ordinal());
			this.add(panel, sideMenuID.name());
		}
	}
	
	public void switchPanel(MenuSideMenus panelID) {
		CardLayout cl = (CardLayout)this.getLayout();
		cl.show(this, panelID.name());
	}
}
