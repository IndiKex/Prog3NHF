package main.gui.panels.main.menu.sidepanels.settings;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import main.gui.Window;
import main.gui.config.theme.Theme;

public class SideMenu extends JMenu {
	private Dimension menuSize = new Dimension(150, 50);
	
	public SideMenu(String label) {
		super("");
		
		this.setLayout(new BorderLayout());

		Theme theme = Window.getConfig().theme;

		Border border = BorderFactory.createLineBorder(theme.secondaryColor, 3);
		this.setBorder(border);
		
		this.setBackground(theme.baseColor);
		
		Font font = new Font("Arial", Font.PLAIN, 20);
		
		JLabel textLabel = new JLabel(label);
		textLabel.setForeground(theme.textColor);
		textLabel.setFont(font);
		textLabel.setBorder(new EmptyBorder(0, 10, 0, 0)); // top, left, bottom, right
		
		JLabel arrowLabel = new JLabel(">");
		arrowLabel.setForeground(theme.textColor);
		arrowLabel.setFont(font);
		arrowLabel.setBorder(new EmptyBorder(0, 0, 0, 10)); // top, left, bottom, right
		
		this.add(textLabel, BorderLayout.WEST);
		this.add(arrowLabel, BorderLayout.EAST);
		
		
		this.setAlignmentX(Component.LEFT_ALIGNMENT);
		this.setPreferredSize(menuSize);
		this.setMinimumSize(menuSize);
		this.setBackground(theme.baseColor);
		
		this.getPopupMenu().setBackground(theme.baseColor);
		
		Border popupBorder = BorderFactory.createLineBorder(theme.secondaryColor, 3);
		this.getPopupMenu().setBorder(popupBorder);
		
	}
	
	@Override
	protected Point getPopupMenuOrigin() {
		return new Point(this.getWidth() + 3, 0);
	}
}
