package main.gui.config.theme;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;

public class Theme {
	public Color baseColor = new Color(10, 8, 102); 		// Background of the panel
	public Color gradientSecondColor = baseColor.darker();
	
	public Color secondaryColor = new Color(230, 200, 48); // Color of icons and text
	
	public Color buttonColor = new Color(secondaryColor.getRed(), secondaryColor.getGreen(), secondaryColor.getBlue());
	public Color buttonHoverColor = new Color(buttonColor.getRed(), buttonColor.getGreen(), buttonColor.getBlue());
	public Color buttonClickColor = buttonColor.darker();
	
	// TODO
	
	public static ImageIcon recolorIcon(ImageIcon originalIcon, Color newColor) {
		Image img = originalIcon.getImage();
		
		BufferedImage bi = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);
		
		Graphics2D g2d = bi.createGraphics();
		g2d.drawImage(img, 0, 0, null);
		g2d.dispose();
		
		for (int x = 0; x < bi.getWidth(); x++) {
			for (int y = 0; y < bi.getHeight(); y++) {
				int argb = bi.getRGB(x, y);
				
				int red = (argb >> 16) & 0xff;
				int green = (argb >> 8) & 0xff;
				int blue = (argb >> 0) & 0xff;
				
				int value = (red + green + blue) / 3;
				
				if (value == 0) {
					int newRGB = (0xff << 24) | (newColor.getRed() << 16) | (newColor.getGreen() << 8) | newColor.getBlue();
					bi.setRGB(x, y, newRGB);
				} else {
					int newRGB = (0 << 24) | (newColor.getRed() << 16) | (newColor.getGreen() << 8) | newColor.getBlue();
					bi.setRGB(x, y, newRGB);
				}
				
				
			}
		}
		
		return new ImageIcon(bi);
		
	}
	
	public static ImageIcon drawOnFixCanvas(ImageIcon originalIcon, int offsetX, int offsetY, int fixWidth, int fixHeight) {
		Image iconImage = originalIcon.getImage();
		
		BufferedImage bi = new BufferedImage(fixWidth, fixHeight, BufferedImage.TYPE_INT_ARGB);
		
		Graphics2D g2d = bi.createGraphics();
		g2d.drawImage(iconImage, offsetX, offsetY, null);
		g2d.dispose();
		
		return new ImageIcon(bi);
		
	}
	
	public static ImageIcon resizeIcon(ImageIcon originalIcon, int x, int y) {
		Image iconImage = originalIcon.getImage();
		
		Image resized = iconImage.getScaledInstance(x, y, Image.SCALE_SMOOTH);
		
		return new ImageIcon(resized);
	}
}
