package main.gui.panels.sidebar;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import main.gui.Window;
import main.gui.config.theme.Theme;


public class SideBarButton extends JButton {
	public SideBarButton(File iconFile, Runnable onClickFunction, int buttonSize) {
		this.setMaximumSize(new Dimension(buttonSize, buttonSize));
		this.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		//this.setBackground(Window.getConfig().theme.secondaryColor);
		
		Theme theme = Window.getConfig().theme;
		ImageIcon baseIcon = Theme.recolorIcon(new ImageIcon(iconFile.getPath()), theme.buttonColor);
		ImageIcon hoverIcon = Theme.recolorIcon(new ImageIcon(iconFile.getPath()), theme.buttonHoverColor);
		ImageIcon clickIcon = Theme.recolorIcon(new ImageIcon(iconFile.getPath()), theme.buttonClickColor);
		
		this.setIcon(resizeIcon(baseIcon, buttonSize, buttonSize));
		this.setRolloverIcon(resizeIcon(hoverIcon, buttonSize, buttonSize));
		this.setPressedIcon(resizeIcon(clickIcon, buttonSize, buttonSize));
		
		this.setBorderPainted(false);
		this.setContentAreaFilled(false);
		this.setFocusPainted(false);
		
		addClickFunction(onClickFunction);
	}
	
	private void addClickFunction(Runnable onClickFunction) {
		this.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				onClickFunction.run();
			}
		});
	}
	
	private ImageIcon resizeIcon(ImageIcon originalIcon, int x, int y) {
		Image iconImage = originalIcon.getImage();
		
		Image resized = iconImage.getScaledInstance(x, y, Image.SCALE_SMOOTH);
		
		return new ImageIcon(resized);
	}
}
