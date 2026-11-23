package main.gui.panels.main.menu.sidepanels.user;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import main.comm.asnwer.UserRegistAnswer;
import main.comm.request.UserRegistRequest;
import main.gui.RequestSender;
import main.gui.Window;
import main.gui.config.lang.Language;
import main.gui.config.theme.Theme;
import main.gui.panels.util.DateChoiceButton;
import main.gui.panels.util.InvisibleJPanel;
import main.gui.panels.util.TextButton;
import main.gui.panels.util.TextField;
import main.gui.panels.util.VisibleJLabel;
import main.gui.panels.util.WrongLabel;

public class SignupPanel extends JPanel {
	private JButton loginButton;
	private TextField usernameField;
	private TextField passwordField;
	private VisibleJLabel dateLabel;
	private DateChoiceButton dateButton;
	private TextField IDField;
	private TextField addressField;
	private JLabel wrongLabel;
	private JPanel buttonPanel;
	
	private Runnable onSignupFunction;
	
	public SignupPanel(Runnable onSignupFunction) {
		this.onSignupFunction = onSignupFunction;
		
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setOpaque(false);
		
		Dimension textFieldSize = new Dimension(200, 40);
		
		Language lang = Window.getConfig().language;
		
		usernameField = new TextField(50);
		setupTextField(usernameField, textFieldSize, lang.get("menuSideUserSignupTextfieldUsername"));
		
		passwordField = new TextField(50);
		setupTextField(passwordField, textFieldSize, lang.get("menuSideUserSignupTextfieldPassword"));
		
		
		// Date panel
		JPanel datePanel = new InvisibleJPanel();
		datePanel.setLayout(new BoxLayout(datePanel, BoxLayout.X_AXIS));
		datePanel.setMaximumSize(textFieldSize);
		datePanel.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		dateLabel = new VisibleJLabel(lang.get("menuSideUserSignupTextfieldDate"));
		
		dateButton = new DateChoiceButton();
		dateButton.setMaximumSize(textFieldSize);
		
		datePanel.add(dateLabel);
		datePanel.add(dateButton);

		
		IDField = new TextField(50);
		setupTextField(IDField, textFieldSize, lang.get("menuSideUserSignupTextfieldID"));

		addressField = new TextField(50);
		setupTextField(addressField, textFieldSize, lang.get("menuSideUserSignupTextfieldAddress"));
		
		initButtonPanel();
		
		Dimension rigidAreaSize = new Dimension(this.getWidth(), 20);
		
		this.add(Box.createRigidArea(rigidAreaSize));
		this.add(usernameField);
		this.add(Box.createRigidArea(rigidAreaSize));
		this.add(passwordField);
		this.add(Box.createRigidArea(rigidAreaSize));
		this.add(datePanel);
		this.add(Box.createRigidArea(rigidAreaSize));
		this.add(IDField);
		this.add(Box.createRigidArea(rigidAreaSize));
		this.add(addressField);
		this.add(Box.createRigidArea(rigidAreaSize));
		this.add(buttonPanel);
	}
	
	private void setupTextField(TextField textField, Dimension textFieldSize, String placeholderText) {
		textField.setPlaceholderText(placeholderText);
		textField.setMaximumSize(textFieldSize);
		textField.setAlignmentX(Component.LEFT_ALIGNMENT);
	}
	
	private void initButtonPanel() {
		Theme theme = Window.getConfig().theme;
		Language lang = Window.getConfig().language;
		
		buttonPanel = new JPanel();
		buttonPanel.setOpaque(false);
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
		buttonPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
		buttonPanel.setOpaque(false);
		
		loginButton = new TextButton();
		String text = lang.get("menuSideUserSignupButtonSignup");
		loginButton.setText(text);
		Dimension loginButtonSize = new Dimension(text.length() * 15, 40);
		loginButton.setMaximumSize(loginButtonSize);
		
		addListener();
		
		wrongLabel = new WrongLabel("");
		wrongLabel.setForeground(theme.warningColor);
		wrongLabel.setVisible(false);
		
		buttonPanel.add(loginButton);
		buttonPanel.add(Box.createRigidArea(new Dimension(10, 10)));
		buttonPanel.add(wrongLabel);
	}
	
	private void addListener() {
		loginButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				wrongLabel.setVisible(false);
				try {
					handleSignup();
				} catch (UserExistsException e) {
					handleError(e);
				} catch (EmptyFieldException e) {
					handleError(e);
				}
			}
		});
	}
	
	private void handleError(UserExistsException e) {
		Language lang = Window.getConfig().language;
		wrongLabel.setText(lang.get("menuSideUserSignupWarningUsernameExists"));
		wrongLabel.setVisible(true);
	}
	
	private void handleError(EmptyFieldException e) {
		Language lang = Window.getConfig().language;
		wrongLabel.setText(lang.get("menuSideUserSignupWarningEmptyFields"));
		wrongLabel.setVisible(true);
	}
	
	private void handleSignup() throws UserExistsException, EmptyFieldException {
		if (usernameField.getText().isEmpty() ||
			passwordField.getText().isEmpty() ||
			IDField.getText().isEmpty() ||
			addressField.getText().isEmpty()) {
			throw new EmptyFieldException();
		}
		
		UserRegistAnswer answer = sendRequest();
		if (!answer.successful) {
			throw new UserExistsException();
		}
		
		RequestSender.username = answer.username;
		onSignupFunction.run();
	}
	
	private UserRegistAnswer sendRequest() {
		String username = usernameField.getText();
		String password = passwordField.getText();
		String date = dateButton.getDate().toString();
		String idnum = IDField.getText();
		String address = addressField.getText();
		
		return (UserRegistAnswer) RequestSender.sendRequest(new UserRegistRequest(username, password, date, idnum, address));
	}
	
	private class UserExistsException extends Exception {}
	private class EmptyFieldException extends Exception {}
}
