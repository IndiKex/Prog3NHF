package main.gui.panels.main.tickets.sidepanels.ticket_list;

import java.awt.BorderLayout;
import java.awt.Color;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import main.comm.RequestType;
import main.comm.asnwer.TicketListAnswer;
import main.comm.asnwer.TicketListAnswer.TicketData;
import main.comm.request.TicketListRequest;
import main.gui.RequestSender;
import main.gui.panels.InvisibleJPanel;
import main.gui.panels.TextButton;
import main.gui.panels.VisibleJPanel;

public class TicketListPanel extends VisibleJPanel {
	private List<TicketDataPanel> dataPanels = new ArrayList<>();
	private List<TextButton> buttons = new ArrayList<>();
	private JPanel dataHolderPanel;
	
	public TicketListPanel() {
		initPanels();
	}
	
	public void addDataPanels(String startStopName, String destStopName, LocalDateTime startTime) {
		TicketListAnswer answer = (TicketListAnswer) RequestSender.sendRequest(new TicketListRequest(startStopName, destStopName, startTime), RequestType.TICKETLIST);
		
		dataHolderPanel.removeAll();
		for (TicketData ticketData : answer.data) {
			JPanel holderPanel = new InvisibleJPanel();
			holderPanel.setOpaque(false);
			TicketDataPanel panel = new TicketDataPanel(ticketData.ticketID, ticketData.departureTime, ticketData.arrivalTime, ticketData.price);
			dataPanels.add(panel);
			
			TextButton button = new TextButton();
			button.setText("Purchase");
			buttons.add(button);

			holderPanel.add(panel);
			holderPanel.add(button);
			
			dataHolderPanel.add(holderPanel);
		}
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
		
		for (TicketDataPanel panel : dataPanels) {
			panel.refreshColors();
		}
		for (TextButton button : buttons) {
			button.refreshColors();
		}
	}
}
