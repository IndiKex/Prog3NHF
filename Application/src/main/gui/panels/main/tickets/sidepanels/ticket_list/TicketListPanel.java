package main.gui.panels.main.tickets.sidepanels.ticket_list;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
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
import main.gui.config.theme.Theme;
import main.gui.panels.main.tickets.sidepanels.product_panels.TicketDataPanel;
import main.gui.panels.util.InvisibleJPanel;
import main.gui.panels.util.ProductListPanel;
import main.gui.panels.util.TextButton;
import main.gui.panels.util.WrongLabel;

public class TicketListPanel extends ProductListPanel {
	private List<TextButton> buttons = new ArrayList<>();
	private List<JLabel> labels = new ArrayList<>();
	TextButton returnButton;
	private Runnable onReturnButtonRunnable;
	
	public TicketListPanel(Runnable onReturnButtonRunnable) {
		this.onReturnButtonRunnable = onReturnButtonRunnable;
		super();
		
		returnButton = new TextButton();
		returnButton.setText("<");
		//returnButton.setBorder(null);
		returnButton.setAlignmentX(Component.LEFT_ALIGNMENT);
		returnButton.setMaximumSize(new Dimension(30, 30));
		returnButton.setOpaque(false);
		returnButton.addActionListener(e -> {
			onReturnButtonRunnable.run();
		});
		JPanel buttonPanel = new InvisibleJPanel();
		buttonPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
		buttonPanel.add(Box.createRigidArea(new Dimension(20, 0)));
		buttonPanel.add(returnButton);
		this.add(buttonPanel, BorderLayout.NORTH);
		
	}
	
	
	public void addDataPanels(String startStopName, String destStopName, LocalDateTime startTime, int peopleNum) {
		TicketListAnswer answer = (TicketListAnswer) RequestSender.sendRequest(new TicketListRequest(startStopName, destStopName, startTime, peopleNum));
		
		Language lang = Window.getConfig().language;
		Theme theme = Window.getConfig().theme;
		
		clearPanel();
		
		if (answer.data.size() == 0) {
			emptyListText.setText(lang.get("ticketsSideSearchResultEmptyList"));
			this.addEmptyListText();
			return;
		}
		
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
			wrongLabel.setForeground(theme.warningColor);
			wrongLabel.setVisible(false);
			labels.add(wrongLabel);
			
			button.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					for (JLabel label : labels) {
						label.setVisible(false);
					}
					try {
						handlePurchase(ticketData);
						wrongLabel.setForeground(theme.textColor);
						wrongLabel.setText(lang.get("ticketsSidePurchaseSuccessful"));
						wrongLabel.setVisible(true);
					} catch (NoUsernameException err) {
						wrongLabel.setForeground(theme.warningColor);
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
		returnButton.refreshColors();
	}
}
