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

public class LoginPanel extends JPanel {
	private JButton loginButton;
	private TextField usernameField;
	private TextField passwordField;
	private JLabel wrongLabel;
	private JPanel buttonPanel;
	
	private Runnable onLoginFunction;
	
	public LoginPanel(Runnable onLoginFunction) {
		this.onLoginFunction = onLoginFunction;
		
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setOpaque(false);
		
		Dimension textFieldSize = new Dimension(200, 40);
		
		Language lang = Window.getConfig().language;
		
		usernameField = new TextField(50);
		usernameField.setPlaceholderText(lang.get("menuSideUserLoginTextfieldUsername"));
		usernameField.setMaximumSize(textFieldSize);
		usernameField.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		passwordField = new TextField(50);
		passwordField.setPlaceholderText(lang.get("menuSideUserLoginTextfieldPassword"));
		passwordField.setMaximumSize(textFieldSize);
		passwordField.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		initButtonPanel();
		
		Dimension rigidAreaSize = new Dimension(this.getWidth(), 20);
		
		this.add(Box.createRigidArea(rigidAreaSize));
		this.add(usernameField);
		this.add(Box.createRigidArea(rigidAreaSize));
		this.add(passwordField);
		this.add(Box.createRigidArea(rigidAreaSize));
		this.add(buttonPanel);
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
		loginButton.setText(lang.get("menuSideUserLoginButtonLogin"));
		Dimension loginButtonSize = new Dimension(100, 40);
		loginButton.setMaximumSize(loginButtonSize);
		
		addListener();
		
		wrongLabel = new JLabel();
		wrongLabel.setText(lang.get("menuSideUserLoginWarningWrongAuthData"));
		wrongLabel.setForeground(theme.warningColor);
		
		buttonPanel.add(loginButton);
		buttonPanel.add(Box.createRigidArea(new Dimension(10, 10)));
		buttonPanel.add(wrongLabel);
	}
	
	private void addListener() {
		loginButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				onLoginFunction.run();
			}
		});
	}
}
