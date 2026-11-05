package main.gui.panels.main;

import java.awt.CardLayout;
import java.util.List;

import javax.swing.JPanel;

import main.gui.Window;
import main.gui.config.theme.Theme;
import main.gui.enums.MainPanels;
import main.gui.panels.InvisibleJPanel;
import main.gui.panels.TempMainPanel;
import main.gui.panels.main.menu.MenuPanel;

public class MainPanelContainer extends InvisibleJPanel {
	private List<JPanel> mainPanels;
	
	public MainPanelContainer() {
		this.setLayout(new CardLayout());
		
		Theme theme = Window.getConfig().theme;
		//this.setBackground(theme.baseColor);
		
		mainPanels = List.of(
			new TempMainPanel("Map"),
			new TempMainPanel("Tickets"),
			new MenuPanel()
		);
		
		for (MainPanels panelID : MainPanels.values()) {
			JPanel panel = mainPanels.get(panelID.ordinal());
			this.add(panel, panelID.name());
		}
	}
	
	public void switchPanel(MainPanels panelID) {
		CardLayout cl = (CardLayout) this.getLayout();
		cl.show(this, panelID.name());
	}
}
