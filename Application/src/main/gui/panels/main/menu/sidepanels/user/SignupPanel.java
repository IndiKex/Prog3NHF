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

import main.gui.Window;
import main.gui.config.lang.Language;
import main.gui.config.theme.Theme;
import main.gui.panels.TextButton;
import main.gui.panels.TextField;

public class SignupPanel extends JPanel {
	private JButton loginButton;
	private TextField usernameField;
	private TextField passwordField;
	private TextField dateField;
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
		
		dateField = new TextField(50);
		setupTextField(dateField, textFieldSize, lang.get("menuSideUserSignupTextfieldDate"));

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
		this.add(dateField);
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
		loginButton.setText(lang.get("menuSideUserSignupButtonSignup"));
		Dimension loginButtonSize = new Dimension(100, 40);
		loginButton.setMaximumSize(loginButtonSize);
		
		addListener();
		
		wrongLabel = new JLabel();
		wrongLabel.setText(lang.get("menuSideUserSignupWarningUsernameExists"));
		wrongLabel.setForeground(theme.warningColor);
		
		buttonPanel.add(loginButton);
		buttonPanel.add(Box.createRigidArea(new Dimension(10, 10)));
		buttonPanel.add(wrongLabel);
	}
	
	private void addListener() {
		loginButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				onSignupFunction.run();
			}
		});
	}
}
