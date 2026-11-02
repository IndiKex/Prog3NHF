package main.gui.panels.main.menu.sidepanels.about;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import main.gui.Window;
import main.gui.config.lang.Language;
import main.gui.config.theme.Theme;
import main.gui.panels.VisibleJPanel;

public class AboutSidePanel extends VisibleJPanel {
	private JPanel labelPanel;
	private JPanel infoPanel;
	
	public AboutSidePanel() {
		this.setLayout(new BorderLayout());
		
		initializePanels();
	}
	
	public void initializePanels() {
		labelPanel = new JPanel();
		labelPanel.setLayout(new BoxLayout(labelPanel, BoxLayout.Y_AXIS));
		labelPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
		labelPanel.setAlignmentY(Component.CENTER_ALIGNMENT);
		labelPanel.setOpaque(false);
		
		infoPanel = new JPanel();
		infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
		infoPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
		infoPanel.setAlignmentY(Component.CENTER_ALIGNMENT);
		infoPanel.setOpaque(false);
		
		this.setBorder(new EmptyBorder(200, 50, 0, 0)); // top, left, bottom, right
		
		addLabels();
		
		this.add(labelPanel, BorderLayout.WEST);
		this.add(infoPanel, BorderLayout.CENTER);
	}
	
	public void addLabels() {
		List<String> infoNames = List.of("Creator", "Version", "Release", "GitHub");
		
		Theme theme = Window.getConfig().theme;
		Font font = new Font("Arial", Font.PLAIN, 20);
		
		Language lang = Window.getConfig().language;
		for (String infoName : infoNames) {
			String labelData = lang.get("menuSideAboutLabel" + infoName);
			String infoData = lang.get("menuSideAboutInfo" + infoName);
			
			JLabel labelObj = new JLabel(labelData + "     ");
			labelObj.setForeground(Color.WHITE);
			labelObj.setFont(font);
			labelPanel.add(labelObj);
			
			JLabel infoObj = new JLabel(infoData);
			infoObj.setForeground(theme.secondaryColor);
			infoObj.setFont(font);
			infoPanel.add(infoObj);
			
			labelPanel.add(Box.createRigidArea(new Dimension(labelPanel.getWidth(), 20)));
			infoPanel.add(Box.createRigidArea(new Dimension(infoPanel.getWidth(), 20)));
		}
	}
}
