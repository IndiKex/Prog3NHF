package main.gui.panels.main.tickets.sidepanels.ticket_config;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import main.comm.asnwer.StopNameAnswer;
import main.comm.asnwer.TicketAddFareAnswer;
import main.comm.products.FareData;
import main.comm.request.StopNameRequest;
import main.comm.request.TicketAddFareRequest;
import main.gui.RequestSender;
import main.gui.Window;
import main.gui.config.theme.Theme;
import main.gui.panels.InvisibleJPanel;
import main.gui.panels.Refreshable;
import main.gui.panels.TextButton;
import main.gui.panels.TextField;
import main.gui.panels.VisibleJLabel;
import main.gui.panels.VisibleJPanel;
import main.util.FareCalculator;
import main.util.StopNameSearch;

public class TicketSearchPanel extends VisibleJPanel {
	public static class SearchData {
		public String startStopName;
		public String destStopName;
		public LocalDateTime startTime;
		public int peopleNum;
		
		public SearchData(String startStopName, String destStopName, LocalDateTime startTime, int peopleNum) {
			this.startStopName = startStopName;
			this.destStopName = destStopName;
			this.startTime = startTime;
			this.peopleNum = peopleNum;
		}
	}
	
	private class StopSearchDocumentListener implements DocumentListener {
		private TextField textField;
		private JPopupMenu popup;
		private boolean ignoreNextUpdate = false;
		
		public StopSearchDocumentListener(TextField textField) {
			this.textField = textField;
			popup = new JPopupMenu();
			popup.setFocusable(false);
		}
		
		public void insertUpdate(DocumentEvent e) {
			if (!ignoreNextUpdate) {
				onTextChanged();
				ignoreNextUpdate = false;
			}
		}

		public void removeUpdate(DocumentEvent e) {
			if (!ignoreNextUpdate) {
				onTextChanged();
				ignoreNextUpdate = false;
			}
		}

		public void changedUpdate(DocumentEvent e) {
			if (!ignoreNextUpdate) {
				onTextChanged();
				ignoreNextUpdate = false;
			}
		}
		
		private void onTextChanged() {
			String currentText = textField.getText();
			List<String> res = StopNameSearch.search(currentText, stopNames);
			res = res.subList(0, Integer.min(res.size(), 10));
			
			popup.removeAll();
			
			if (currentText.isEmpty() || res.isEmpty()) {
				popup.setVisible(false);
				return;
			}
			
			for (String stopName : res) {
				JMenuItem item = new JMenuItem(stopName);
				
				item.addActionListener(e -> {
					textField.setText(stopName);
					popup.setVisible(false);
					ignoreNextUpdate = true;
				});
				
				popup.add(item);
			}
			
			popup.pack();
			popup.show(textField, 0, textField.getHeight());
		}
	}
	
	private enum TicketType {
		TICKET,
		FARE
	}
	
	private List<String> stopNames;
	
	private List<Refreshable> refreshables = new ArrayList<>();
	private TextField startStopField;
	private TextField destStopField;
	private TextField timeField;
	private TextField peopleField;
	
	private String[] areaStrings = {"Bács-Kiskun",
									"Baranya", 
									"Békés", 
									"Borsod-Abaúj-Zemplén", 
									"Csongrád-Csanád", 
									"Fejér", 
									"Győr-Moson-Sopron", 
									"Hajdú-Bihar", 
									"Heves", 
									"Jász-Nagykun-Szolnok", 
									"Komárom-Esztergom", 
									"Nógrád", 
									"Pest", 
									"Somogy", 
									"Szabolcs-Szatmár-Bereg", 
									"Tolna", 
									"Vas", 
									"Veszprém", 
									"Zala"};
	private String[] validLengthStrings = {"2 hét", "1 hónap", "1 év"};
	private JComboBox areaBox;
	private TextField validTimeField;
	private JComboBox validLengthBox;
	private VisibleJLabel priceLabel;
	private JLabel wrongLabel;
	
	private JPanel switchPanel;
	
	private TextButton ticketButton;
	private TextButton fareButton;
	
	private SearchData searchData = new SearchData(null, null, null, -1);
	private Runnable onSearchRunnable;
	
	public TicketSearchPanel(Runnable onSearchRunnable) {
		this.onSearchRunnable = onSearchRunnable;
		
		StopNameAnswer answer = (StopNameAnswer) RequestSender.sendRequest(new StopNameRequest()); 
		stopNames = answer.stopNames;
		
		initPanels();
	}
	
	private void initPanels() {
		this.setLayout(new BorderLayout());
		
		JPanel buttonPanel = new InvisibleJPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
		
		
		// TICKET BUTTON
		ticketButton = new TextButton();
		ticketButton.setText("Tickets");
		buttonPanel.add(ticketButton);
		refreshables.add(ticketButton);
		
		ticketButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CardLayout cardLayout = (CardLayout) switchPanel.getLayout();
				cardLayout.show(switchPanel, TicketType.TICKET.name());
			}
		});
		
		// FARE BUTTON
		fareButton = new TextButton();
		fareButton.setText("Fares");
		buttonPanel.add(fareButton);
		refreshables.add(fareButton);
		
		fareButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CardLayout cardLayout = (CardLayout) switchPanel.getLayout();
				cardLayout.show(switchPanel, TicketType.FARE.name());
			}
		});
		
		switchPanel = new InvisibleJPanel();
		switchPanel.setLayout(new CardLayout());
		
		addTicketPanel();
		addFarePanel();
		
		this.add(buttonPanel, BorderLayout.NORTH);
		this.add(switchPanel, BorderLayout.CENTER);
	}
	
	private void addFarePanel() {
		JPanel farePanel = new InvisibleJPanel();
		farePanel.setLayout(new BoxLayout(farePanel, BoxLayout.Y_AXIS));
		
		// Area panel
		JPanel areaPanel = new InvisibleJPanel();
		areaPanel.setLayout(new BoxLayout(areaPanel, BoxLayout.X_AXIS));
		VisibleJLabel areaLabel = new VisibleJLabel("Area");
		areaBox = new JComboBox(areaStrings);
		areaPanel.add(areaLabel);
		areaPanel.add(areaBox);
		
		
		validTimeField = new TextField(50);
		validTimeField.setPlaceholderText("Start of validity");
		
		JPanel validLengthPanel = new InvisibleJPanel();
		validLengthPanel.setLayout(new BoxLayout(validLengthPanel, BoxLayout.X_AXIS));
		VisibleJLabel validLengthLabel = new VisibleJLabel("Area");
		validLengthBox = new JComboBox(validLengthStrings);
		validLengthPanel.add(validLengthLabel);
		validLengthPanel.add(validLengthBox);
		
		validLengthBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateFarePrice();
			}
		});
		
		JPanel pricePanel = new InvisibleJPanel();
		pricePanel.setLayout(new BoxLayout(pricePanel, BoxLayout.X_AXIS));
		VisibleJLabel priceLabelLabel = new VisibleJLabel("Price");
		priceLabel = new VisibleJLabel("");
		priceLabel.setForeground(Window.getConfig().theme.secondaryColor);
		updateFarePrice();
		pricePanel.add(priceLabelLabel);
		pricePanel.add(priceLabel);
		
		// Purchase button
		JPanel buttonPanel = new InvisibleJPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
		TextButton button = new TextButton();
		button.setText("Purchase");
		wrongLabel = new JLabel();
		wrongLabel.setOpaque(false);
		wrongLabel.setForeground(Window.getConfig().theme.warningColor);
		wrongLabel.setVisible(false);
		buttonPanel.add(button);
		buttonPanel.add(wrongLabel);
		
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					wrongLabel.setVisible(false);
					handleFarePurchase();
				} catch (NoUsernameException err) {
					wrongLabel.setText("Login to purchase fares!");
					wrongLabel.setVisible(true);
				}
			}
		});
		
		farePanel.add(areaPanel);
		farePanel.add(validTimeField);
		farePanel.add(validLengthPanel);
		farePanel.add(pricePanel);
		farePanel.add(buttonPanel);
		
		switchPanel.add(farePanel, TicketType.FARE.name());
		
		refreshables.add(areaLabel);
		//refreshables.add(areaBox);
		refreshables.add(validTimeField);
		refreshables.add(validLengthLabel);
		//refreshables.add(validLengthBox);
		refreshables.add(button);
	}
	
	private void handleFarePurchase() throws NoUsernameException {
		String area = (String) areaBox.getSelectedItem();
		LocalDate validityStart = LocalDate.parse(this.validTimeField.getText());
		String validityLength = (String) validLengthBox.getSelectedItem();
		int price = FareCalculator.priceCalculator(validityLength);
		
		TicketAddFareAnswer answer = (TicketAddFareAnswer) RequestSender.sendRequest(new TicketAddFareRequest(RequestSender.username, new FareData(area, validityStart, validityLength, price)));
		
		if (!answer.successful) {
			throw new NoUsernameException();
		}
	}
	
	private class NoUsernameException extends Exception {}
	
	private void updateFarePrice() {
		String validityLength = (String) validLengthBox.getSelectedItem();
		String priceText = Integer.toString(FareCalculator.priceCalculator(validityLength));
		priceLabel.setText(priceText);
	}
	
	private void addTicketPanel() {
		JPanel ticketPanel = new InvisibleJPanel();
		ticketPanel.setLayout(new BoxLayout(ticketPanel, BoxLayout.Y_AXIS));
		
		startStopField = new TextField(50);
		startStopField.setPlaceholderText("Start stop name");
		startStopField.getDocument().addDocumentListener(new StopSearchDocumentListener(startStopField));
		
		destStopField = new TextField(50);
		destStopField.setPlaceholderText("Destination stop name");
		destStopField.getDocument().addDocumentListener(new StopSearchDocumentListener(destStopField));
		
		timeField = new TextField(50);
		timeField.setPlaceholderText("Time of start");
		timeField.setText(LocalDateTime.now().toString().substring(0,19));
		peopleField = new TextField(50);
		peopleField.setPlaceholderText("Number of people");
		
		// Search button
		TextButton searchButton = new TextButton();
		searchButton.setText("Search");
		
		searchButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				searchData.startStopName = startStopField.getText();
				searchData.destStopName = destStopField.getText();
				try {
					searchData.startTime = LocalDateTime.parse(timeField.getText());
				} catch (Exception ignored) {}
				try {
					searchData.peopleNum = Integer.parseInt(peopleField.getText());
				} catch (Exception ignored) {}
				onSearchRunnable.run();
			}
		});
		
		ticketPanel.add(startStopField);
		ticketPanel.add(destStopField);
		ticketPanel.add(timeField);
		ticketPanel.add(peopleField);
		ticketPanel.add(searchButton);
		
		switchPanel.add(ticketPanel, TicketType.TICKET.name());
		
		refreshables.add(startStopField);
		refreshables.add(destStopField);
		refreshables.add(timeField);
		refreshables.add(peopleField);
		refreshables.add(searchButton);
	}
	
	@Override
	public void refreshColors() {
		super.refreshColors();
		for (Refreshable refreshable : refreshables) {
			refreshable.refreshColors();
		}
		Theme theme = Window.getConfig().theme;
		priceLabel.setForeground(theme.secondaryColor);
		wrongLabel.setForeground(theme.warningColor);
	}

	public SearchData getSearchData() {
		return searchData;
	}
}
