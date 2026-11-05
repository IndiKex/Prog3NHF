package main.gui.panels.main.tickets;

import java.awt.BorderLayout;
import java.util.List;

import javax.swing.JPanel;

import main.gui.panels.InvisibleJPanel;
import main.gui.panels.SidePanelContainer;
import main.gui.panels.TempMainPanel;
import main.gui.panels.main.tickets.config.TicketsConfigPanel;

public class TicketsPanel extends InvisibleJPanel {
	private TicketsSidePanels sidePanel = TicketsSidePanels.VALID;
	
	private TicketsConfigPanel configPanel;
	
	private SidePanelContainer<TicketsSidePanels> sidePanelContainer;
	
	public TicketsPanel() {
		this.setLayout(new BorderLayout());
		
		configPanel = new TicketsConfigPanel(() -> {updateSidePanels();});
		this.add(configPanel, BorderLayout.WEST);
		
		List<JPanel> sidePanels = List.of(
			new TempMainPanel("Valid tickets"),
			new TempMainPanel("Invalid tickets"),
			new TempMainPanel("Purchase tickets")
		);
		List<TicketsSidePanels> sidePanelIDs = List.of(
			TicketsSidePanels.VALID,
			TicketsSidePanels.INVALID,
			TicketsSidePanels.PURCHASE
		);
		sidePanelContainer = new SidePanelContainer<>(sidePanels, sidePanelIDs);
		sidePanelContainer.switchPanel(sidePanel);
		this.add(sidePanelContainer);
	}
	
	public void updateSidePanels() {
		if (configPanel.getEnumValue().equals(sidePanel)) {
			return;
		}
		sidePanel = configPanel.getEnumValue();
		
		sidePanelContainer.switchPanel(sidePanel);
		System.out.println("Tickets panel changed to " + sidePanel.name());
	}
}
