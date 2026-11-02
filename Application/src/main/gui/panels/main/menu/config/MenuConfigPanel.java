package main.gui.panels.main.menu.config;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagLayout;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

import main.gui.Window;
import main.gui.config.lang.Language;
import main.gui.config.theme.Theme;
import main.gui.panels.main.menu.MenuSideMenus;

public class MenuConfigPanel extends JPanel {
	private MenuSideMenus sideMenu = MenuSideMenus.USER;
	
	private Dimension panelSize = new Dimension(275, 720);
	
	private JPanel buttonPanel;
	private List<MenuConfigButton> buttons;
	
	private Runnable onChooseFunction;
	
	public MenuConfigPanel(Runnable onChooseFunction) {
		this.onChooseFunction = onChooseFunction;
		
		this.setPreferredSize(panelSize);
		this.setMaximumSize(panelSize);
		this.setBackground(Color.BLACK);
		
		this.setLayout(new GridBagLayout());
		
		initializeButtons();
	}
	
	private void initializeButtons() {
		buttonPanel = new JPanel();
		
		Dimension buttonPanelSize = new Dimension((int)(panelSize.getWidth() * 0.8), 250);
		
		buttonPanel.setPreferredSize(buttonPanelSize);
		buttonPanel.setMaximumSize(buttonPanelSize);
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
		buttonPanel.setOpaque(false);
		
		Dimension buttonSize = new Dimension((int)(buttonPanelSize.getWidth()), 70);
		
		Language lang = Window.getConfig().language;
		List<String> buttonTexts = List.of(
				lang.get("menuConfigButtonUser"),
				lang.get("menuConfigButtonSettings"),
				lang.get("menuConfigButtonAbout")
		);
		
		List<File> iconFiles = List.of(
				new File("assets/graphics/menu/user_button_icon.png"),
				new File("assets/graphics/menu/settings_button_icon.png"),
				new File("assets/graphics/menu/about_button_icon.png")
		);
		
		List<Runnable> onClickFunctions = List.of(
				() -> {setMenuSideMenu(MenuSideMenus.USER); updateButtons(); onChooseFunction.run();},
				() -> {setMenuSideMenu(MenuSideMenus.SETTINGS); updateButtons(); onChooseFunction.run();},
				() -> {setMenuSideMenu(MenuSideMenus.ABOUT); updateButtons(); onChooseFunction.run();}
		);
		
		addButtons(buttonPanelSize, buttonSize, buttonTexts, iconFiles, onClickFunctions);
		updateButtons();
		
		this.add(buttonPanel);
	}
	
	private void addButtons(Dimension buttonPanelSize, Dimension buttonSize, List<String> buttonTexts, List<File> iconFiles, List<Runnable> onClickFunctions) {
		buttons = new ArrayList<>();
		
		int padding = 0;
		if (iconFiles.size() > 1) {
			padding = (int)(buttonPanelSize.getHeight() - buttonSize.getHeight() * iconFiles.size()) / (iconFiles.size() - 1);
		}
		
		for (int i = 0; i < iconFiles.size(); i++) {
			MenuConfigButton button = new MenuConfigButton(buttonTexts.get(i), iconFiles.get(i), onClickFunctions.get(i), buttonSize);
			buttonPanel.add(button);
			buttons.add(button);
			
			if (i < iconFiles.size() - 1) {
				buttonPanel.add(Box.createRigidArea(new Dimension((int)buttonPanelSize.getWidth(), padding)));
			}
		}
	}
	
	private void updateButtons() {
		for (MenuConfigButton button : buttons) {
			if (buttons.get(sideMenu.ordinal()) == button) {
				button.setChosen(true);
			} else {
				button.setChosen(false);
			}
			
			button.updateChosen();
		}
	}
	
	public void setMenuSideMenu(MenuSideMenus sideMenu) {
		this.sideMenu = sideMenu;
	}
	
	public MenuSideMenus getMenuSideMenu() {
		return sideMenu;
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		Theme theme = Window.getConfig().theme;
		Color darkGradientColor = theme.gradientSecondColor;
		Color lightGradientColor = theme.baseColor;
		
		Graphics2D g2d = (Graphics2D) g;
		GradientPaint gp = new GradientPaint(0, getHeight() / 2, darkGradientColor, 60, getHeight() / 2, lightGradientColor);
		
		g2d.setPaint(gp);
		g2d.fillRect(0, 0, getWidth(), getHeight());
	}
}
