package main.gui.panels.main.menu.sidepanels.user;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import main.gui.Window;
import main.gui.config.lang.Language;
import main.gui.panels.TextButton;
import main.gui.panels.VisibleJPanel;

public class LoggedOutUserMenuPanel extends VisibleJPanel {
	private JPanel buttonPanel;
	private JPanel dataPanel;
	
	private JPanel loginPanel;
	private JPanel signupPanel;
	
	private JButton loginButton;
	private JButton signupButton;
	
	private boolean loginPanelChosen = true;
	
	private Runnable onLoginFunction;

	public LoggedOutUserMenuPanel(Runnable onLoginFunction) {
		this.onLoginFunction = onLoginFunction;
		
		this.setLayout(new BorderLayout());
		this.setBorder(new EmptyBorder(120, 50, 0, 0));
		
		addPanels();
	}
	
	private void addPanels() {
		addButtonPanel();
		addDataPanel();
	}
	
	private void addButtonPanel() {
		buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
		buttonPanel.setOpaque(false);
		
		Dimension buttonSize = new Dimension(100, 40);
		
		initLoginButton(buttonSize);
		initSignupButton(buttonSize);
		
		buttonPanel.add(loginButton);
		buttonPanel.add(Box.createRigidArea(new Dimension(20, buttonSize.height)));
		buttonPanel.add(signupButton);
		
		this.add(buttonPanel, BorderLayout.NORTH);
	}
	
	private void initLoginButton(Dimension buttonSize) {
		Language lang = Window.getConfig().language;
		
		loginButton = new TextButton();
		String loginButtonLabel = lang.get("menuSideUserButtonLogin");
		loginButton.setText(loginButtonLabel);
		loginButton.setAlignmentX(Component.LEFT_ALIGNMENT);
		loginButton.setMaximumSize(buttonSize);
		
		loginButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (!loginPanelChosen) {
					CardLayout cl = (CardLayout) dataPanel.getLayout();
					cl.show(dataPanel, "login");
					loginPanelChosen = true;
				}
			}
		});
	}
	
	private void initSignupButton(Dimension buttonSize) {
		Language lang = Window.getConfig().language;
		
		signupButton = new TextButton();
		String signupButtonLabel = lang.get("menuSideUserButtonSignup");
		signupButton.setText(signupButtonLabel);
		signupButton.setAlignmentX(Component.LEFT_ALIGNMENT);
		signupButton.setMaximumSize(buttonSize);
		
		signupButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (loginPanelChosen) {
					CardLayout cl = (CardLayout) dataPanel.getLayout();
					cl.show(dataPanel, "signup");
					loginPanelChosen = false;
				}
			}
		});
	}
	
	private void addDataPanel() {
		dataPanel = new JPanel();
		dataPanel.setLayout(new CardLayout());
		dataPanel.setOpaque(false);
		
		initLoginPanel();
		initSignupPanel();
		
		dataPanel.add(loginPanel, "login");
		dataPanel.add(signupPanel, "signup");
		
		this.add(dataPanel);
	}
	
	private void initLoginPanel() {
		loginPanel = new LoginPanel(onLoginFunction);
	}
	
	private void initSignupPanel() {
		signupPanel = new SignupPanel(onLoginFunction);
	}
	
}
