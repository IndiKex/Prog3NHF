package main.gui.panels.main.tickets.sidepanels.ticket_config;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import main.comm.asnwer.StopNameAnswer;
import main.comm.asnwer.TicketAddFareAnswer;
import main.comm.products.FareData;
import main.comm.request.StopNameRequest;
import main.comm.request.TicketAddFareRequest;
import main.gui.RequestSender;
import main.gui.Window;
import main.gui.config.lang.Language;
import main.gui.config.theme.Theme;
import main.gui.panels.util.DateChoiceButton;
import main.gui.panels.util.InvisibleJPanel;
import main.gui.panels.util.Refreshable;
import main.gui.panels.util.SearchPopup;
import main.gui.panels.util.TextButton;
import main.gui.panels.util.TextField;
import main.gui.panels.util.TimeField;
import main.gui.panels.util.VisibleJComboBox;
import main.gui.panels.util.VisibleJLabel;
import main.gui.panels.util.VisibleJPanel;
import main.gui.panels.util.WrongLabel;
import main.util.FareCalculator;
import main.util.FareCalculator.FareValidityLength;
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
		}	
		
		public void insertUpdate(DocumentEvent e) {
			if (!ignoreNextUpdate) {
				onTextChanged();
			}
			ignoreNextUpdate = false;
		}

		public void removeUpdate(DocumentEvent e) {
			if (!ignoreNextUpdate) {
				onTextChanged();
			}
			ignoreNextUpdate = false;
		}

		public void changedUpdate(DocumentEvent e) {
			if (!ignoreNextUpdate) {
				onTextChanged();
			}
			ignoreNextUpdate = false;
		}
		
		private void onTextChanged() {
			String currentText = textField.getText();
			List<String> res = StopNameSearch.search(currentText, stopNames);
			res = res.subList(0, Integer.min(res.size(), 10));

			popup = new SearchPopup();
			popup.setFocusable(false);
			
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
	private TimeField timeField;
	private DateChoiceButton timeButton;
	private TextField peopleField;
	private JLabel ticketWrongLabel;
	
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
	private String[] validLengthStrings;
	private VisibleJComboBox areaBox;
	private DateChoiceButton validTimeButton;
	private VisibleJComboBox validLengthBox;
	private VisibleJLabel priceLabel;
	private JLabel fareWrongLabel;
	
	private JPanel switchPanel;
	
	private TextButton ticketButton;
	private TextButton fareButton;
	
	private SearchData searchData = new SearchData(null, null, null, -1);
	private Runnable onSearchRunnable;
	
	public TicketSearchPanel(Runnable onSearchRunnable) {
		this.onSearchRunnable = onSearchRunnable;
		
		StopNameAnswer answer = (StopNameAnswer) RequestSender.sendRequest(new StopNameRequest()); 
		stopNames = answer.stopNames;

		this.setBorder(new EmptyBorder(120, 50, 0, 0));
		
		initPanels();
	}
	
	private void initPanels() {
		this.setLayout(new BorderLayout());
		
		JPanel buttonPanel = new InvisibleJPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));

		Dimension buttonSize = new Dimension(100, 40);
		Language lang = Window.getConfig().language;
		String ticketButtonText = lang.get("ticketsSideSearchButtonTicket");
		String fareButtonText = lang.get("ticketsSideSearchButtonFare");
		
		int maxLength = Integer.max(ticketButtonText.length(), fareButtonText.length());
		buttonSize.width = Integer.max(buttonSize.width, maxLength * 15);
		
		// TICKET BUTTON
		ticketButton = new TextButton();
		ticketButton.setText(ticketButtonText);
		ticketButton.setMaximumSize(buttonSize);
		buttonPanel.add(ticketButton);
		refreshables.add(ticketButton);
		
		ticketButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CardLayout cardLayout = (CardLayout) switchPanel.getLayout();
				cardLayout.show(switchPanel, TicketType.TICKET.name());
			}
		});
		
		buttonPanel.add(Box.createRigidArea(new Dimension(20, buttonSize.height)));
		
		// FARE BUTTON
		fareButton = new TextButton();
		fareButton.setText(fareButtonText);
		fareButton.setMaximumSize(buttonSize);
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
		Theme theme = Window.getConfig().theme;
		
		JPanel farePanel = new InvisibleJPanel();
		farePanel.setLayout(new BoxLayout(farePanel, BoxLayout.Y_AXIS));
		
		Dimension componentSize = new Dimension(350, 40);
		
		Language lang = Window.getConfig().language;
		
		// Area panel
		JPanel areaPanel = new InvisibleJPanel();
		areaPanel.setLayout(new BoxLayout(areaPanel, BoxLayout.X_AXIS));
		areaPanel.setMaximumSize(componentSize);
		areaPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		VisibleJLabel areaLabel = new VisibleJLabel(lang.get("ticketsSideSearchFareAreaLabel"));
		areaBox = new VisibleJComboBox(areaStrings);
		
		areaPanel.add(areaLabel);
		areaPanel.add(Box.createRigidArea(new Dimension(10, 0)));
		areaPanel.add(areaBox);
		
		// Valid time panel
		JPanel validTimePanel = new InvisibleJPanel();
		validTimePanel.setLayout(new BoxLayout(validTimePanel, BoxLayout.X_AXIS));
		validTimePanel.setMaximumSize(componentSize);
		validTimePanel.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		VisibleJLabel validTimeLabel = new VisibleJLabel(lang.get("ticketsSideSearchFareValidityTimeLabel"));
		validTimeButton = new DateChoiceButton();
		validTimeButton.setAlignmentX(Component.LEFT_ALIGNMENT);
		validTimeButton.setMaximumSize(componentSize);
		
		validTimePanel.add(validTimeLabel);
		validTimePanel.add(Box.createRigidArea(new Dimension(10, 0)));
		validTimePanel.add(validTimeButton);
		
		// Valid length panel
		Map<String, FareValidityLength> fareValidityLengthMap = getFareValidityLengthMap();
		
		JPanel validLengthPanel = new InvisibleJPanel();
		validLengthPanel.setLayout(new BoxLayout(validLengthPanel, BoxLayout.X_AXIS));
		validLengthPanel.setMaximumSize(componentSize);
		validLengthPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		VisibleJLabel validLengthLabel = new VisibleJLabel(lang.get("ticketsSideSearchFareValidityLengthLabel"));
		validLengthBox = new VisibleJComboBox(fareValidityLengthMap.keySet().toArray());
		validLengthPanel.add(validLengthLabel);
		validLengthPanel.add(Box.createRigidArea(new Dimension(10, 0)));
		validLengthPanel.add(validLengthBox);
		
		validLengthBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateFarePrice();
			}
		});
		
		// Price panel
		JPanel pricePanel = new InvisibleJPanel();
		pricePanel.setLayout(new BoxLayout(pricePanel, BoxLayout.X_AXIS));
		pricePanel.setMaximumSize(componentSize);
		pricePanel.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		VisibleJLabel priceLabelLabel = new VisibleJLabel(lang.get("ticketsSideSearchFarePriceLabel"));
		priceLabel = new VisibleJLabel("");
		priceLabel.setForeground(Window.getConfig().theme.secondaryColor);
		updateFarePrice();
		
		pricePanel.add(priceLabelLabel);
		pricePanel.add(Box.createRigidArea(new Dimension(10, 0)));
		pricePanel.add(priceLabel);
		
		// Purchase button
		JPanel buttonPanel = new InvisibleJPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
		buttonPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		TextButton button = new TextButton();
		button.setText(lang.get("ticketsSideSearchFarePurchaseButton"));
		button.setMaximumSize(new Dimension(100, 40));
		
		fareWrongLabel = new WrongLabel("");
		fareWrongLabel.setOpaque(false);
		fareWrongLabel.setForeground(theme.warningColor);
		fareWrongLabel.setVisible(false);
		buttonPanel.add(button);
		buttonPanel.add(Box.createRigidArea(new Dimension(10, 0)));
		buttonPanel.add(fareWrongLabel);
		
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					fareWrongLabel.setVisible(false);
					handleFarePurchase();
					fareWrongLabel.setForeground(theme.textColor);
					fareWrongLabel.setText(lang.get("ticketsSidePurchaseSuccessful"));
					fareWrongLabel.setVisible(true);
				} catch (NoUsernameException err) {
					fareWrongLabel.setForeground(theme.warningColor);
					fareWrongLabel.setText(lang.get("ticketsSideSearchFareWrong"));
					fareWrongLabel.setVisible(true);
				}
			}
		});
		
		Dimension rigidAreaSize = new Dimension(this.getWidth(), 20);
		
		farePanel.add(Box.createRigidArea(rigidAreaSize));
		farePanel.add(areaPanel);
		farePanel.add(Box.createRigidArea(rigidAreaSize));
		farePanel.add(validTimePanel);
		farePanel.add(Box.createRigidArea(rigidAreaSize));
		farePanel.add(validLengthPanel);
		farePanel.add(Box.createRigidArea(rigidAreaSize));
		farePanel.add(pricePanel);
		farePanel.add(Box.createRigidArea(rigidAreaSize));
		farePanel.add(buttonPanel);
		
		switchPanel.add(farePanel, TicketType.FARE.name());
		
		refreshables.add(areaLabel);
		refreshables.add(areaBox);
		refreshables.add(validTimeLabel);
		refreshables.add(validTimeButton);
		refreshables.add(validLengthLabel);
		refreshables.add(validLengthBox);
		refreshables.add(priceLabelLabel);
		refreshables.add(priceLabel);
		refreshables.add(button);
	}
	
	private Map<String, FareValidityLength> getFareValidityLengthMap() {
		Map<String, FareValidityLength> res = new HashMap<>();
		
		Language lang = Window.getConfig().language;
		for (FareValidityLength val : FareValidityLength.values()) {
			res.put(lang.get(val.name()), val);
		}
		return res;
	}
	
	private void handleFarePurchase() throws NoUsernameException {
		String area = (String) areaBox.getSelectedItem();
		LocalDate validityStart = validTimeButton.getDate();
		String validityLengthString = (String) validLengthBox.getSelectedItem();
		Map<String, FareValidityLength> fareValidityLengthMap = getFareValidityLengthMap();
		FareValidityLength validityLength = fareValidityLengthMap.get(validityLengthString);
		int price = FareCalculator.priceCalculator(validityLength);
		
		TicketAddFareAnswer answer = (TicketAddFareAnswer) RequestSender.sendRequest(new TicketAddFareRequest(RequestSender.username, new FareData(area, validityStart, validityLength.name(), price)));
		
		if (!answer.successful) {
			throw new NoUsernameException();
		}
	}
	
	private class NoUsernameException extends Exception {}
	
	private void updateFarePrice() {
		String validityLength = (String) validLengthBox.getSelectedItem();
		Map<String, FareValidityLength> fareValidityLengthMap = getFareValidityLengthMap();
		String priceText = Integer.toString(FareCalculator.priceCalculator(fareValidityLengthMap.get(validityLength)));
		priceLabel.setText(priceText);
	}
	
	
	
	private void addTicketPanel() {
		JPanel ticketPanel = new InvisibleJPanel();
		ticketPanel.setLayout(new BoxLayout(ticketPanel, BoxLayout.Y_AXIS));

		Dimension textFieldSize = new Dimension(200, 40);
		Language lang = Window.getConfig().language;
		
		startStopField = new TextField(50);
		startStopField.setPlaceholderText(lang.get("ticketsSideSearchTicketStartStopName"));
		startStopField.getDocument().addDocumentListener(new StopSearchDocumentListener(startStopField));
		startStopField.setMaximumSize(textFieldSize);
		startStopField.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		destStopField = new TextField(50);
		destStopField.setPlaceholderText(lang.get("ticketsSideSearchTicketDestStopName"));
		destStopField.getDocument().addDocumentListener(new StopSearchDocumentListener(destStopField));
		destStopField.setMaximumSize(textFieldSize);
		destStopField.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		
		JPanel timePanel = new InvisibleJPanel();
		timePanel.setLayout(new BoxLayout(timePanel, BoxLayout.X_AXIS));
		timePanel.setAlignmentX(Component.LEFT_ALIGNMENT);
		timePanel.setMaximumSize(textFieldSize);
		timeField = new TimeField();
		timeField.setHorizontalAlignment(JTextField.CENTER);
		timeField.setMaximumSize(new Dimension(60, textFieldSize.height));
		timeField.setPreferredSize(new Dimension(60, textFieldSize.height));
		timeButton = new DateChoiceButton();
		timeButton.setMaximumSize(new Dimension(130, textFieldSize.height));
		timeButton.setPreferredSize(new Dimension(130, textFieldSize.height));
		timePanel.add(timeButton);
		timePanel.add(Box.createRigidArea(new Dimension(10, textFieldSize.height)));
		timePanel.add(timeField);
		
		peopleField = new TextField(50);
		peopleField.setPlaceholderText(lang.get("ticketsSideSearchTicketNumPeople"));
		peopleField.setMaximumSize(textFieldSize);
		peopleField.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		// Search button
		JPanel buttonPanel = new InvisibleJPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
		buttonPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		TextButton searchButton = new TextButton();
		searchButton.setText(lang.get("ticketsSideSearchTicketSearchButton"));
		searchButton.setMaximumSize(new Dimension(100, 40));
		
		ticketWrongLabel = new WrongLabel("");
		ticketWrongLabel.setForeground(Window.getConfig().theme.warningColor);
		ticketWrongLabel.setVisible(false);
		
		buttonPanel.add(searchButton);
		buttonPanel.add(Box.createRigidArea(new Dimension(10, 0)));
		buttonPanel.add(ticketWrongLabel);
		
		searchButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					ticketWrongLabel.setVisible(false);
					handleSearch();
					onSearchRunnable.run();
				} catch (EmptyFieldException err) {
					ticketWrongLabel.setVisible(true);
					ticketWrongLabel.setText(lang.get("ticketsSideSearchTicketWrongEmptyField"));
				} catch (PeopleFieldNotIntegerException err) {
					ticketWrongLabel.setVisible(true);
					ticketWrongLabel.setText(lang.get("ticketsSideSearchTicketWrongPeopleNum"));
				} catch (StopNameInvalidException err) {
					ticketWrongLabel.setText(lang.get("ticketsSideSearchTicketWrongStopName"));
					ticketWrongLabel.setVisible(true);
				}
			}
		});
		
		Dimension rigidAreaSize = new Dimension(this.getWidth(), 20);
		
		ticketPanel.add(Box.createRigidArea(rigidAreaSize));
		ticketPanel.add(startStopField);
		ticketPanel.add(Box.createRigidArea(rigidAreaSize));
		ticketPanel.add(destStopField);
		ticketPanel.add(Box.createRigidArea(rigidAreaSize));
		ticketPanel.add(timePanel);
		ticketPanel.add(Box.createRigidArea(rigidAreaSize));
		ticketPanel.add(peopleField);
		ticketPanel.add(Box.createRigidArea(rigidAreaSize));
		ticketPanel.add(buttonPanel);
		
		switchPanel.add(ticketPanel, TicketType.TICKET.name());
		
		refreshables.add(startStopField);
		refreshables.add(destStopField);
		refreshables.add(timeField);
		refreshables.add(timeButton);
		refreshables.add(peopleField);
		refreshables.add(searchButton);
	}
	
	private void handleSearch() throws EmptyFieldException, PeopleFieldNotIntegerException, StopNameInvalidException {
		if (startStopField.getText().isEmpty() || destStopField.getText().isEmpty() || peopleField.getText().isEmpty()) {
			throw new EmptyFieldException();
		}
		
		if (!stopNames.contains(startStopField.getText()) || !stopNames.contains(destStopField.getText())) {
			throw new StopNameInvalidException();
		}
		
		searchData.startStopName = startStopField.getText();
		searchData.destStopName = destStopField.getText();
		
		LocalDate date = timeButton.getDate();
		LocalTime time = timeField.getTime();
		searchData.startTime = LocalDateTime.of(date, time);
		
		try {
			searchData.peopleNum = Integer.parseInt(peopleField.getText());
		} catch (Exception e) {
			throw new PeopleFieldNotIntegerException();
		}
	}
	
	private class EmptyFieldException extends Exception {}
	private class StopNameInvalidException extends Exception {}
	private class PeopleFieldNotIntegerException extends Exception {}
	
	@Override
	public void refreshColors() {
		super.refreshColors();
		for (Refreshable refreshable : refreshables) {
			refreshable.refreshColors();
		}
		Theme theme = Window.getConfig().theme;
		priceLabel.setForeground(theme.secondaryColor);
		fareWrongLabel.setForeground(theme.warningColor);
		ticketWrongLabel.setForeground(theme.warningColor);
	}

	public SearchData getSearchData() {
		return searchData;
	}
}
