package main.gui.panels.main.menu.sidepanels.user;

import java.awt.BorderLayout;
import java.awt.Component;
import java.util.Arrays;

import main.gui.panels.InvisibleJPanel;

public class UserMenuPanel extends InvisibleJPanel {
	private boolean loggedIn = true;
	
	private LoggedInUserMenuPanel loggedInPanel;
	private LoggedOutUserMenuPanel loggedOutPanel; 
	
	public UserMenuPanel() {
		this.setLayout(new BorderLayout());
		
		reInitPanel();
	}
	
	public void switchPanel(boolean newLoggedIn) {
		if (loggedIn == newLoggedIn) {
			return;
		}
		loggedIn = newLoggedIn;
		
		reInitPanel();
		
	}
	
	public void reInitPanel() {
		
		if (this.contains(loggedInPanel)) this.remove(loggedInPanel);
		if (this.contains(loggedOutPanel)) this.remove(loggedOutPanel);
		
		if (loggedIn) {
			initLoggedIn();
			this.add(loggedInPanel);
		} else {
			initLoggedOut();
			this.add(loggedOutPanel);
		}
		
		this.revalidate();
		this.repaint();
	} 	
	
	public void initLoggedIn() {
		loggedInPanel = new LoggedInUserMenuPanel(() -> {switchPanel(false);});
	}
	
	public void initLoggedOut() {
		loggedOutPanel = new LoggedOutUserMenuPanel(() -> {switchPanel(true);});
	}
	
	private boolean contains(Component component) {
		for (int i = 0; i < this.getComponents().length; i++) {
			if (component == this.getComponent(i)) {
				return true;
			}
		}
		return false;
	}
}
