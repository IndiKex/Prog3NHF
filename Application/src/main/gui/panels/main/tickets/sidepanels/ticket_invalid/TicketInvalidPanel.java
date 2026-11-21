package main.gui.panels.main.tickets.sidepanels.ticket_invalid;

import java.awt.BorderLayout;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import main.comm.asnwer.TicketInvalidListAnswer;
import main.comm.products.FareData;
import main.comm.products.TicketData;
import main.comm.request.TicketInvalidListRequest;
import main.gui.RequestSender;
import main.gui.panels.InvisibleJPanel;
import main.gui.panels.TextButton;
import main.gui.panels.VisibleJPanel;
import main.gui.panels.main.tickets.sidepanels.product_panels.FareDataPanel;
import main.gui.panels.main.tickets.sidepanels.product_panels.ProductDataPanel;
import main.gui.panels.main.tickets.sidepanels.product_panels.TicketDataPanel;

public class TicketInvalidPanel extends VisibleJPanel {
	private List<ProductDataPanel> dataPanels = new ArrayList<>();
	private List<TextButton> buttons = new ArrayList<>();
	private JPanel dataHolderPanel;
	
	public TicketInvalidPanel() {
		initPanels();
	}
	
	public void addDataPanels(String username) {
		TicketInvalidListAnswer answer = (TicketInvalidListAnswer) RequestSender.sendRequest(new TicketInvalidListRequest(username));
		
		dataHolderPanel.removeAll();
		
		for (FareData fareData : answer.fareDatas) {
			addFareDataPanel(fareData);
		}
		
		for (TicketData ticketData : answer.ticketDatas) {
			addTicketDataPanel(ticketData);
		}
	}
	
	private void addTicketDataPanel(TicketData ticketData) {
		List<String> data = ticketData.getData();
		TicketDataPanel panel = new TicketDataPanel(data.get(0), LocalDateTime.parse(data.get(1)), LocalDateTime.parse(data.get(2)), Integer.parseInt(data.get(3)));
		dataPanels.add(panel);

		dataHolderPanel.add(panel);
	}
	
	private void addFareDataPanel(FareData fareData) {
		List<String> data = fareData.getData();
		FareDataPanel panel = new FareDataPanel(data.get(0), LocalDate.parse(data.get(1)), data.get(2), Integer.parseInt(data.get(3)));
		dataPanels.add(panel);

		dataHolderPanel.add(panel);
	}
	
	private void initPanels() {
		dataHolderPanel = new InvisibleJPanel();
		dataHolderPanel.setLayout(new BoxLayout(dataHolderPanel, BoxLayout.Y_AXIS));
		
		JScrollPane scrollPane = new JScrollPane(dataHolderPanel);
		scrollPane.setPreferredSize(this.getSize());
		scrollPane.setOpaque(false);
		scrollPane.getViewport().setOpaque(false);
		
		this.setLayout(new BorderLayout());
		this.add(scrollPane, BorderLayout.CENTER);
		
	}
	
	public void refreshColors() {
		super.refreshColors();
		
		for (ProductDataPanel panel : dataPanels) {
			panel.refreshColors();
		}
		for (TextButton button : buttons) {
			button.refreshColors();
		}
	}
}
