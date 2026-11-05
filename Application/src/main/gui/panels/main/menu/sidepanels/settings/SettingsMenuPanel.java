package main.gui.panels.main.menu.sidepanels.settings;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import main.gui.Window;
import main.gui.panels.VisibleJPanel;

public class SettingsMenuPanel extends VisibleJPanel {
	JPanel buttonPanel;
	JMenuBar menuBar;
	List<JMenu> menus;
	
	public SettingsMenuPanel() {
		this.setBorder(new EmptyBorder(220, 35, 0, 0)); // top, left, bottom, right
		this.setLayout(new BorderLayout());
		
		initializeButtonPanel();
	}
	
	private void initializeButtonPanel() {
		buttonPanel = new JPanel();
		buttonPanel.setOpaque(false);
		
		initializeMenuBar();
		
		this.add(buttonPanel, BorderLayout.WEST);
	}
	
	private void initializeMenuBar() {
		menuBar = new JMenuBar();
		menuBar.setLayout(new BoxLayout(menuBar, BoxLayout.Y_AXIS));
		menuBar.setOpaque(false);
		menuBar.setBorder(null);
		
		List<String> menuLabels = List.of(
				"Theme",
				"Language",
				"Orientation"
		);
		
		List<List<String>> menusItemLabels = List.of(
				List.of("Light", "Dark", "MAV"),
				List.of("English", "Hungarian"),
				List.of("Top", "Bottom", "Left", "Right")
		);
		
		
		addMenus(menuLabels, menusItemLabels);
		
		buttonPanel.add(menuBar);
	}
	
	private void addMenus(List<String> menuLabels, List<List<String>> menusItemLabels) {
		menus = new ArrayList<>();

		
		for (int i = 0; i < menuLabels.size(); i++) {
			JMenu menu = new SideMenu(menuLabels.get(i));
			menus.add(menu);
			
			addMenuItems(menu, menusItemLabels.get(i));

			menuBar.add(menu);
			menuBar.add(Box.createRigidArea(new Dimension(1, 20)));
		}
	}
	
	private void addMenuItems(JMenu menu, List<String> menuItemLabels) {
		Dimension menuItemSize = new Dimension(120, 40);
		Font font = new Font("Arial", Font.PLAIN, 20);
		
		for (int i = 0; i < menuItemLabels.size(); i++) {
			String menuItemLabel = menuItemLabels.get(i);
			
			JMenuItem menuItem = new JMenuItem(menuItemLabel);
			menuItem.setBackground(Window.getConfig().theme.baseColor);
			menuItem.setForeground(Window.getConfig().theme.textColor);
			menuItem.setBorderPainted(false);
			menuItem.setPreferredSize(menuItemSize);
			menuItem.setFont(font);
			
			menuItem.addActionListener(new AbstractAction() {
				public void actionPerformed(ActionEvent e) {
					System.out.println(menuItemLabel + " was pressed");
				}
			});
			
			menu.add(menuItem);
		}
	}
}
