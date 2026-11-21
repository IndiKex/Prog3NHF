package main.gui.panels.main.tickets.sidepanels.ticket_list;

import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import main.comm.asnwer.TicketAddTicketAnswer;
import main.comm.asnwer.TicketListAnswer;
import main.comm.products.TicketData;
import main.comm.request.TicketAddTicketRequest;
import main.comm.request.TicketListRequest;
import main.gui.RequestSender;
import main.gui.Window;
import main.gui.panels.InvisibleJPanel;
import main.gui.panels.TextButton;
import main.gui.panels.VisibleJPanel;
import main.gui.panels.main.tickets.sidepanels.product_panels.TicketDataPanel;

public class TicketListPanel extends VisibleJPanel {
	private List<TicketDataPanel> dataPanels = new ArrayList<>();
	private List<TextButton> buttons = new ArrayList<>();
	private List<JLabel> labels = new ArrayList<>();
	private JPanel dataHolderPanel;
	
	public TicketListPanel() {
		initPanels();
	}
	
	public void addDataPanels(String startStopName, String destStopName, LocalDateTime startTime, int peopleNum) {
		TicketListAnswer answer = (TicketListAnswer) RequestSender.sendRequest(new TicketListRequest(startStopName, destStopName, startTime, peopleNum));
		
		dataHolderPanel.removeAll();
		buttons.clear();
		labels.clear();
		for (TicketData ticketData : answer.data) {
			JPanel holderPanel = new InvisibleJPanel();
			holderPanel.setOpaque(false);
			List<String> data = ticketData.getData();
			TicketDataPanel panel = new TicketDataPanel(data.get(0), LocalDateTime.parse(data.get(1)), LocalDateTime.parse(data.get(2)), Integer.parseInt(data.get(3)));
			dataPanels.add(panel);
			
			TextButton button = new TextButton();
			button.setText("Purchase");
			buttons.add(button);
			
			JLabel wrongLabel = new JLabel();
			wrongLabel.setOpaque(false);
			wrongLabel.setForeground(Window.getConfig().theme.warningColor);
			wrongLabel.setVisible(false);
			labels.add(wrongLabel);
			
			button.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					wrongLabel.setVisible(false);
					try {
						handlePurchase(ticketData);
					} catch (NoUsernameException err) {
						wrongLabel.setText("Log in to purchase tickets!");
						wrongLabel.setVisible(true);
					}
				}
			});

			holderPanel.add(panel);
			holderPanel.add(button);
			holderPanel.add(wrongLabel);
			
			dataHolderPanel.add(holderPanel);
		}
	}
	
	private void handlePurchase(TicketData ticketData) throws NoUsernameException {
		TicketAddTicketAnswer answer = (TicketAddTicketAnswer) RequestSender.sendRequest(new TicketAddTicketRequest(RequestSender.username, ticketData));
		if (!answer.successful)
			throw new NoUsernameException();
	}
	
	private class NoUsernameException extends Exception {}
	
	
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
		for (JLabel wrongLabel : labels) {
			wrongLabel.setForeground(Window.getConfig().theme.warningColor);
		}
	}
}
