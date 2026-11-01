package main.gui.panels;

import java.awt.Font;
import java.awt.GridBagLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class TempMainPanel extends JPanel {
	public String text;
	
	public TempMainPanel(String text) {
		this.text = text;
		
		this.setLayout(new GridBagLayout());
		
		JLabel label = new JLabel(text);
		
		label.setFont(new Font("Arial", Font.BOLD, 72));
		
		this.add(label);
	}
	
	public void showPanel() {
		this.setVisible(true);
	}
	
	public void hidePanel() {
		this.setVisible(false);
	}
	
}
