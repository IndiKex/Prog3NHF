package main.gui.panels;

import java.awt.Font;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.BorderFactory;
import javax.swing.JTextField;
import javax.swing.border.Border;

import main.gui.Window;
import main.gui.config.theme.Theme;

public class TextField extends JTextField {
	private String placeholder = "";
	private boolean usesPlaceholder = true;
	
	public TextField(int n) {
		super(n);
		
		Font font = new Font("Arial", Font.PLAIN, 20);
		this.setFont(font);

		refreshColors();
		
		addListener();
	}
	
	public void setPlaceholderText(String placeholder) {
		this.placeholder = placeholder;
		updateForeground();
	}
	
	private void refreshColors() {
		Theme theme = Window.getConfig().theme;
		
		this.setBackground(theme.baseColor);
		updateForeground();
		
		Border border = BorderFactory.createLineBorder(theme.secondaryColor, 3);
		this.setBorder(border);
	}
	
	private void updateForeground() {
		Theme theme = Window.getConfig().theme;
		
		if (usesPlaceholder) {
			this.setForeground(theme.placeholderTextColor);
			this.setText(placeholder);
		} else {
			this.setForeground(theme.textColor);
			this.setText("");
		}
	}
	
	private void addListener() {
		this.addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent e) {
				if (usesPlaceholder) {
					usesPlaceholder = false;
					updateForeground();
				}
			}
			
			@Override
			public void focusLost(FocusEvent e) {
				if (!usesPlaceholder && getText().isEmpty()) {
					usesPlaceholder = true;
					updateForeground();
				}
			}
		});
	}
}
