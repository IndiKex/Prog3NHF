package main.gui.panels.sidebar;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import main.gui.Window;
import main.gui.config.theme.Theme;


public class SideBarButton extends JButton {
	public SideBarButton(File iconFile, Runnable onClickFunction, int buttonSize) {
		this.setMaximumSize(new Dimension(buttonSize, buttonSize));
		this.setPreferredSize(new Dimension(buttonSize, buttonSize));
		this.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		//this.setBackground(Window.getConfig().theme.secondaryColor);
		
		Theme theme = Window.getConfig().theme;
		ImageIcon baseIcon = Theme.recolorIcon(new ImageIcon(iconFile.getPath()), theme.buttonColor);
		ImageIcon hoverIcon = Theme.recolorIcon(new ImageIcon(iconFile.getPath()), theme.buttonHoverColor);
		ImageIcon clickIcon = Theme.recolorIcon(new ImageIcon(iconFile.getPath()), theme.buttonClickColor);
		
		ImageIcon baseResized = resizeIcon(baseIcon, (int)(buttonSize * 0.8), (int)(buttonSize * 0.8));
		ImageIcon hoverResized = resizeIcon(hoverIcon, buttonSize, buttonSize);
		ImageIcon clickResized = resizeIcon(clickIcon, buttonSize, buttonSize);
		
		//this.setIcon(drawOnFixCanvas(baseIcon, (int)(buttonSize * 0.1), (int)(buttonSize * 0.1), buttonSize, buttonSize));
		this.setIcon(drawOnFixCanvas(baseResized, (int)(buttonSize * 0.1), (int)(buttonSize * 0.1), buttonSize, buttonSize));
		this.setRolloverIcon(hoverResized);
		this.setPressedIcon(clickResized);
		
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
	
	private ImageIcon drawOnFixCanvas(ImageIcon originalIcon, int offsetX, int offsetY, int fixWidth, int fixHeight) {
		Image iconImage = originalIcon.getImage();
		
		BufferedImage bi = new BufferedImage(fixWidth, fixHeight, BufferedImage.TYPE_INT_ARGB);
		
		Graphics2D g2d = bi.createGraphics();
		g2d.drawImage(iconImage, offsetX, offsetY, null);
		g2d.dispose();
		
		return new ImageIcon(bi);
		
	}
	
	private ImageIcon resizeIcon(ImageIcon originalIcon, int x, int y) {
		Image iconImage = originalIcon.getImage();
		
		Image resized = iconImage.getScaledInstance(x, y, Image.SCALE_SMOOTH);
		
		return new ImageIcon(resized);
	}
}
