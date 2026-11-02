package main.gui.panels.main.menu.sidepanels;

import java.awt.CardLayout;
import java.util.List;

import javax.swing.JPanel;

import main.gui.panels.TempMainPanel;
import main.gui.panels.main.menu.MenuSideMenus;

public class SidePanelContainer extends JPanel {
	List<JPanel> sidePanels;
	
	public SidePanelContainer() {
		this.setLayout(new CardLayout());
		
		sidePanels = List.of(
				new TempMainPanel("User"),
				new TempMainPanel("Settings"),
				new TempMainPanel("About")
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
