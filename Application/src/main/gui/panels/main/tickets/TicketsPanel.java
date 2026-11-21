package main.gui.panels.main.tickets;

import java.awt.BorderLayout;
import java.util.List;

import javax.swing.JPanel;

import main.gui.RequestSender;
import main.gui.panels.InvisibleJPanel;
import main.gui.panels.Refreshable;
import main.gui.panels.SidePanelContainer;
import main.gui.panels.TempMainPanel;
import main.gui.panels.main.tickets.config.TicketsConfigPanel;
import main.gui.panels.main.tickets.sidepanels.ticket_config.TicketSearchPanel;
import main.gui.panels.main.tickets.sidepanels.ticket_config.TicketSearchPanel.SearchData;
import main.gui.panels.main.tickets.sidepanels.ticket_invalid.TicketInvalidPanel;
import main.gui.panels.main.tickets.sidepanels.ticket_list.TicketListPanel;
import main.gui.panels.main.tickets.sidepanels.ticket_valid.TicketValidPanel;

public class TicketsPanel extends InvisibleJPanel implements Refreshable {
	private TicketsSidePanels sidePanel = TicketsSidePanels.VALID;
	
	private TicketsConfigPanel configPanel;
	
	private SidePanelContainer<TicketsSidePanels> sidePanelContainer;
	private TicketListPanel ticketListPanel;
	private TicketSearchPanel ticketSearchPanel;
	private TicketValidPanel ticketValidPanel;
	private TicketInvalidPanel ticketInvalidPanel;
	
	public TicketsPanel() {
		this.setLayout(new BorderLayout());
		
		configPanel = new TicketsConfigPanel(() -> {updateSidePanels();});
		this.add(configPanel, BorderLayout.WEST);
		
		ticketListPanel = new TicketListPanel();
		ticketSearchPanel = new TicketSearchPanel(() -> {changeSearchResultPanel();});
		ticketValidPanel = new TicketValidPanel();
		ticketInvalidPanel = new TicketInvalidPanel();
		List<JPanel> sidePanels = List.of(
			ticketValidPanel,
			ticketInvalidPanel,
			ticketSearchPanel,
			ticketListPanel
		);
		List<TicketsSidePanels> sidePanelIDs = List.of(
			TicketsSidePanels.VALID,
			TicketsSidePanels.INVALID,
			TicketsSidePanels.PURCHASE,
			TicketsSidePanels.SEARCHRESULT
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
		
		if (sidePanel == TicketsSidePanels.SEARCHRESULT) {
			ticketListPanel.addDataPanels(null, null, null, -1);
		} else if (sidePanel == TicketsSidePanels.VALID) {
			ticketValidPanel.addDataPanels(RequestSender.username);
		} else if (sidePanel == TicketsSidePanels.INVALID) {
			ticketInvalidPanel.addDataPanels(RequestSender.username);
		}
		
		sidePanelContainer.switchPanel(sidePanel);
		System.out.println("Tickets panel changed to " + sidePanel.name());
	}
	
	public void changeSearchResultPanel() {
		sidePanelContainer.switchPanel(TicketsSidePanels.SEARCHRESULT);
		SearchData searchData = ticketSearchPanel.getSearchData();
		
		ticketListPanel.addDataPanels(searchData.startStopName, searchData.destStopName, searchData.startTime, searchData.peopleNum);
	}
	
	public void refreshColors() {
		configPanel.refreshColors();
		sidePanelContainer.refreshColors();
	}
}
