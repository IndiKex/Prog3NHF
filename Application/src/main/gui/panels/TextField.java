package main.gui.panels;

import java.awt.Font;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.BorderFactory;
import javax.swing.JTextField;
import javax.swing.border.Border;

import main.gui.Window;
import main.gui.config.theme.Theme;

public class TextField extends JTextField implements Refreshable {
	private String placeholder = "";
	private boolean usesPlaceholder = true;
	
	public TextField(int n) {
		super(n);
		
		Font font = new Font("Arial", Font.PLAIN, 20);
		this.setFont(font);

		refreshColors();
		
		addListener();
	}
	
	public String getText() {
		if (usesPlaceholder || super.getText().isEmpty()) {
			return "";
		}
		return super.getText();
	}
	
	public void setText(String s) {
		super.setText(s);
		usesPlaceholder = s.isEmpty() || s.equals(placeholder);
		updateForeground();
	}
	
	public void setPlaceholderText(String placeholder) {
		this.placeholder = placeholder;
		updateForeground();
	}
	
	public void refreshColors() {
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
			super.setText(placeholder);
		} else {
			this.setForeground(theme.textColor);
			if (super.getText().equals(placeholder))
				super.setText("");
			else
				super.setText(getText());
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
