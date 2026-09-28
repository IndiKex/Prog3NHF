package main.graphics.components;

import java.awt.Component;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class MImage implements MComponent {
	private Image img;
	private int horizontal = SwingConstants.CENTER;
	private int vertical = SwingConstants.CENTER;
	
	public MImage(String imagePath) {
		try {
			img = ImageIO.read(new File(imagePath));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Image getImage() {
		return img;
	}
	
	public void setAlignment(int vertical, int horizontal) {
		this.horizontal = horizontal;
		this.vertical = vertical;
	}
	
	public void resize(int width, int height) {
		img = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
	}
	
	public Component getComponent() {
		ImageIcon icon = new ImageIcon(img);
		JLabel label = new JLabel(icon);
		label.setHorizontalAlignment(horizontal);
		label.setVerticalAlignment(vertical);
		return label;
	}
}
