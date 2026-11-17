package main.gui.panels.main.menu.sidepanels.user;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;

import main.comm.RequestType;
import main.comm.asnwer.UserChangePasswordAnswer;
import main.comm.request.UserChangePasswordRequest;
import main.gui.RequestSender;
import main.gui.Window;
import main.gui.config.lang.Language;
import main.gui.config.theme.Theme;
import main.gui.panels.InvisibleJPanel;
import main.gui.panels.TextButton;
import main.gui.panels.TextField;

public class NewPasswordPanel extends InvisibleJPanel {
	private JButton passwordButton;
	private TextField passwordTextfield;
	private JLabel wrongLabel;
	private boolean pressed = false;
	
	public NewPasswordPanel(Dimension buttonSize) {
		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		this.setAlignmentX(Component.LEFT_ALIGNMENT);

		Language lang = Window.getConfig().language;
		Theme theme = Window.getConfig().theme;
		
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
		
		wrongLabel = new JLabel();
		wrongLabel.setText("Leszophat Karsa Zoltán");
		wrongLabel.setForeground(theme.warningColor);
		wrongLabel.setOpaque(false);
		wrongLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		this.add(passwordButton);
		this.add(Box.createRigidArea(new Dimension(10, 0)));
		this.add(passwordTextfield);
		this.add(Box.createRigidArea(new Dimension(10, 0)));
		this.add(wrongLabel);
		
		updatePasswordTextfield();
	}
	
	private void updatePasswordTextfield() {
		if (pressed) {
			passwordTextfield.setVisible(true);
		} else {
			passwordTextfield.setVisible(false);
			wrongLabel.setVisible(false);
		}
		
		revalidate();
		repaint();
	}
	
	private void addListener() {
		passwordButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				if (pressed) {
					try {
						wrongLabel.setVisible(false);
						handleChangePassword();
						pressed = false;
					} catch (EmptyFieldException e) {
						handleError(e);
					} catch (SamePasswordException e) {
						handleError(e);
					}
				} else {
					pressed = true;
				}
				updatePasswordTextfield();
			}
		});
	}
	
	private void handleError(EmptyFieldException e) {
		wrongLabel.setText("Some fields are empty");
		wrongLabel.setVisible(true);
	}
	
	private void handleError(SamePasswordException e) {
		wrongLabel.setText("New password cannot be the same as the last password");
		wrongLabel.setVisible(true);
	}
	
	private void handleChangePassword() throws SamePasswordException, EmptyFieldException {
		if (passwordTextfield.getText().isEmpty()) {
			throw new EmptyFieldException();
		}
		
		UserChangePasswordAnswer answer = sendRequest();
		if (!answer.successful) {
			throw new SamePasswordException();
		}
	}
	
	private UserChangePasswordAnswer sendRequest() {
		String newPassword = passwordTextfield.getText();
		
		return (UserChangePasswordAnswer) RequestSender.sendRequest(new UserChangePasswordRequest(RequestSender.username, newPassword), RequestType.USERCHANGEPASSWORD);
	}
	
	private class SamePasswordException extends Exception {}
	private class EmptyFieldException extends Exception {}
}
