package main.gui.panels.main.tickets.sidepanels.ticket_valid;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import main.comm.asnwer.TicketValidListAnswer;
import main.comm.products.FareData;
import main.comm.products.TicketData;
import main.comm.request.TicketValidListRequest;
import main.gui.RequestSender;
import main.gui.panels.main.tickets.sidepanels.product_panels.FareDataPanel;
import main.gui.panels.main.tickets.sidepanels.product_panels.TicketDataPanel;
import main.gui.panels.util.ProductListPanel;

public class TicketValidPanel extends ProductListPanel {
	
	
	public void addDataPanels(String username) {
		TicketValidListAnswer answer = (TicketValidListAnswer) RequestSender.sendRequest(new TicketValidListRequest(username));
		
		clearPanel();
		
		for (FareData fareData : answer.fareDatas) {
			addFareDataPanel(fareData);
		}
		
		for (TicketData ticketData : answer.ticketDatas) {
			addTicketDataPanel(ticketData);
		}
	}
	
	private void addTicketDataPanel(TicketData ticketData) {
		List<String> data = ticketData.getData();
		TicketDataPanel panel = new TicketDataPanel(data.get(0), data.get(1), data.get(2), LocalDateTime.parse(data.get(3)), LocalDateTime.parse(data.get(4)), Integer.parseInt(data.get(5)));
		this.addProductDataPanel(panel);
	}
	
	private void addFareDataPanel(FareData fareData) {
		List<String> data = fareData.getData();
		FareDataPanel panel = new FareDataPanel(data.get(0), LocalDate.parse(data.get(1)), data.get(2), Integer.parseInt(data.get(3)));
		addProductDataPanel(panel);
	}
	
}
