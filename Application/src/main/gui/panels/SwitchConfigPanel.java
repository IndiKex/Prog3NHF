package main.gui.panels;

import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

public class SwitchConfigPanel<E extends Enum<E>> extends VisibleJPanel {
	private E enumValue;
	
	private Dimension panelSize = new Dimension(275, 720);
	
	private JPanel buttonPanel;
	private List<SwitchConfigButton> buttons;
	
	private Runnable onChooseFunction;
	private List<String> buttonTexts;
	private List<File> iconFiles;
	private List<E> buttonEnumValues;
	
	public SwitchConfigPanel(E initEnumValue, Runnable onChooseFunction, List<String> buttonTexts, List<File> iconFiles, List<E> buttonEnumValues) {
		this.onChooseFunction = onChooseFunction;
		this.buttonTexts = buttonTexts;
		this.iconFiles = iconFiles;
		this.buttonEnumValues = buttonEnumValues;
		this.enumValue = initEnumValue;
		
		this.setPreferredSize(panelSize);
		this.setMaximumSize(panelSize);
		this.setMinimumSize(panelSize);
		
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
		
		ArrayList<Runnable> onClickFunctions = new ArrayList<>();
		onClickFunctions.ensureCapacity(buttonEnumValues.size());
		
		for (E buttonEnumValue : buttonEnumValues) {
			onClickFunctions.add(() -> {
				setEnumValue(buttonEnumValue);
				updateButtons();
				onChooseFunction.run();
			});
		}
		
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
			SwitchConfigButton button = new SwitchConfigButton(buttonTexts.get(i), iconFiles.get(i), onClickFunctions.get(i), buttonSize);
			buttonPanel.add(button);
			buttons.add(button);
			
			if (i < iconFiles.size() - 1) {
				buttonPanel.add(Box.createRigidArea(new Dimension((int)buttonPanelSize.getWidth(), padding)));
			}
		}
	}
	
	private void updateButtons() {
		for (SwitchConfigButton button : buttons) {
			if (buttons.get(enumValue.ordinal()) == button) {
				button.setChosen(true);
			} else {
				button.setChosen(false);
			}
			
			button.updateChosen();
		}
	}
	
	public void setEnumValue(E sideMenu) {
		this.enumValue = sideMenu;
		updateButtons();
	}
	
	public E getMenuSideMenu() {
		return enumValue;
	}
	
}
