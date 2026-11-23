package main.gui.panels.main.tickets.sidepanels.ticket_list;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;

import main.comm.asnwer.TicketAddTicketAnswer;
import main.comm.asnwer.TicketListAnswer;
import main.comm.products.TicketData;
import main.comm.request.TicketAddTicketRequest;
import main.comm.request.TicketListRequest;
import main.gui.RequestSender;
import main.gui.Window;
import main.gui.config.lang.Language;
import main.gui.panels.main.tickets.sidepanels.product_panels.TicketDataPanel;
import main.gui.panels.util.InvisibleJPanel;
import main.gui.panels.util.ProductListPanel;
import main.gui.panels.util.TextButton;
import main.gui.panels.util.WrongLabel;

public class TicketListPanel extends ProductListPanel {
	private List<TextButton> buttons = new ArrayList<>();
	private List<JLabel> labels = new ArrayList<>();
	
	public void addDataPanels(String startStopName, String destStopName, LocalDateTime startTime, int peopleNum) {
		TicketListAnswer answer = (TicketListAnswer) RequestSender.sendRequest(new TicketListRequest(startStopName, destStopName, startTime, peopleNum));
		
		Language lang = Window.getConfig().language;
		
		clearPanel();
		
		buttons.clear();
		labels.clear();
		for (TicketData ticketData : answer.data) {
			JPanel holderPanel = new InvisibleJPanel();
			holderPanel.setOpaque(false);
			List<String> data = ticketData.getData();
			TicketDataPanel panel = new TicketDataPanel(data.get(0), data.get(1), data.get(2), LocalDateTime.parse(data.get(3)), LocalDateTime.parse(data.get(4)), Integer.parseInt(data.get(5)));
			
			TextButton button = new TextButton();
			button.setText(lang.get("ticketsSideListPurchaseButton"));
			buttons.add(button);
			
			JLabel wrongLabel = new WrongLabel("");
			wrongLabel.setOpaque(false);
			wrongLabel.setForeground(Window.getConfig().theme.warningColor);
			wrongLabel.setVisible(false);
			labels.add(wrongLabel);
			
			button.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					for (JLabel label : labels) {
						label.setVisible(false);
					}
					try {
						handlePurchase(ticketData);
					} catch (NoUsernameException err) {
						wrongLabel.setText(lang.get("ticketsSideListPurchaseWarningNotLggedIn"));
						wrongLabel.setVisible(true);
					}
				}
			});

			this.addProductDataPanel(holderPanel, panel);
			holderPanel.add(button);
			holderPanel.add(wrongLabel);
			
			this.addToDataHolderPanel(holderPanel);
		}
	}
	
	private void handlePurchase(TicketData ticketData) throws NoUsernameException {
		TicketAddTicketAnswer answer = (TicketAddTicketAnswer) RequestSender.sendRequest(new TicketAddTicketRequest(RequestSender.username, ticketData));
		if (!answer.successful)
			throw new NoUsernameException();
	}
	
	private class NoUsernameException extends Exception {}
	
	
	public void refreshColors() {
		super.refreshColors();
		
		for (TextButton button : buttons) {
			button.refreshColors();
		}
		for (JLabel wrongLabel : labels) {
			wrongLabel.setForeground(Window.getConfig().theme.warningColor);
		}
	}
}
