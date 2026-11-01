package main.gui.panels.main;

import java.awt.CardLayout;
import java.util.List;

import javax.swing.JPanel;

import main.gui.enums.MainPanels;
import main.gui.panels.TempMainPanel;

public class MainPanelContainer extends JPanel {
	private List<TempMainPanel> mainPanels;
	
	public MainPanelContainer() {
		this.setLayout(new CardLayout());
		
		mainPanels = List.of(
			new TempMainPanel("Map"),
			new TempMainPanel("Tickets"),
			new TempMainPanel("Menu")
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
