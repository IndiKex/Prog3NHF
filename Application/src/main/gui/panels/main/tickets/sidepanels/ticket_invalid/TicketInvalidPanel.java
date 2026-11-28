package main.gui.panels.main.tickets.sidepanels.ticket_invalid;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import main.comm.asnwer.TicketInvalidListAnswer;
import main.comm.products.FareData;
import main.comm.products.TicketData;
import main.comm.request.TicketInvalidListRequest;
import main.gui.RequestSender;
import main.gui.Window;
import main.gui.config.lang.Language;
import main.gui.panels.main.tickets.sidepanels.product_panels.FareDataPanel;
import main.gui.panels.main.tickets.sidepanels.product_panels.TicketDataPanel;
import main.gui.panels.util.ProductListPanel;
import main.util.FareCalculator.FareValidityLength;

public class TicketInvalidPanel extends ProductListPanel {
	
	public void addDataPanels(String username) {
		TicketInvalidListAnswer answer = (TicketInvalidListAnswer) RequestSender.sendRequest(new TicketInvalidListRequest(username));
		Language lang = Window.getConfig().language;
		
		clearPanel();
		
		if (answer.fareDatas.size() == 0 && answer.ticketDatas.size() == 0) {
			emptyListText.setText(lang.get("ticketsSideInvalidEmptyList"));
			this.addEmptyListText();
			return;
		}
		
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
		FareDataPanel panel = new FareDataPanel(data.get(0), LocalDate.parse(data.get(1)), FareValidityLength.valueOf(data.get(2)), Integer.parseInt(data.get(3)));
		this.addProductDataPanel(panel);
	}
	
}
