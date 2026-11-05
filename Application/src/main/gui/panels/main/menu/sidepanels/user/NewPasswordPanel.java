package main.gui.panels.main.menu.sidepanels.user;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;

import main.gui.Window;
import main.gui.config.lang.Language;
import main.gui.panels.InvisibleJPanel;
import main.gui.panels.TextButton;
import main.gui.panels.TextField;

public class NewPasswordPanel extends InvisibleJPanel {
	private JButton passwordButton;
	private TextField passwordTextfield;
	private boolean pressed = false;
	
	public NewPasswordPanel(Dimension buttonSize) {
		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		this.setAlignmentX(Component.LEFT_ALIGNMENT);

		Language lang = Window.getConfig().language;
		
		String newPasswordButtonLabel = lang.get("menuSideUserButtonNewPassword");
		passwordButton = new TextButton();
		passwordButton.setText(newPasswordButtonLabel);
		passwordButton.setMinimumSize(buttonSize);
		passwordButton.setPreferredSize(buttonSize);
		passwordButton.setMaximumSize(buttonSize);
		passwordButton.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		addListener();
		
		Dimension textfieldSize = new Dimension(200, 40);
		
		String passwordTextfieldLabel = lang.get("menuSideUserTextfieldNewPassword");
		passwordTextfield = new TextField(50);
		passwordTextfield.setPlaceholderText(passwordTextfieldLabel);
		passwordTextfield.setMaximumSize(textfieldSize);
		passwordTextfield.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		this.add(passwordButton);
		this.add(Box.createRigidArea(new Dimension(10, 0)));
		this.add(passwordTextfield);
		
		updatePasswordTextfield();
	}
	
	private void updatePasswordTextfield() {
		if (pressed) {
			passwordTextfield.setVisible(true);
		} else {
			passwordTextfield.setVisible(false);
		}
		
		revalidate();
		repaint();
	}
	
	private void addListener() {
		passwordButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (pressed) {
					pressed = false;
				} else {
					pressed = true;
				}
				updatePasswordTextfield();
			}
		});
	}
}
