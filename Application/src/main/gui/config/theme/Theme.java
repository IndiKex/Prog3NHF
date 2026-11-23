package main.gui.config.theme;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;

import javax.swing.ImageIcon;

public class Theme implements Serializable {
	//public Color baseColor = new Color(10, 8, 102); 		// Background of the panel
	public Color baseColor = new Color(32, 32, 32);
	public Color gradientSecondColor = baseColor.darker();
	
	public Color secondaryColor = new Color(230, 200, 48);
	//public Color secondaryColor = new Color(200, 50, 200);
	
	public Color buttonColor = secondaryColor;
	public Color buttonHoverColor = buttonColor;
	public Color buttonClickColor = buttonColor.darker();
	
	public Color textColor = Color.WHITE;
	public Color placeholderTextColor = Color.LIGHT_GRAY;
	public Color warningColor = Color.RED;
	
	public Color calendarDateColor = textColor;
	public Color calendarGreyedColor = textColor.darker();
	public Color calendarNowColor = secondaryColor;
	
	private void updateDerivedColors() {
		gradientSecondColor = baseColor.darker();
		
		buttonColor = secondaryColor;
		buttonHoverColor = buttonColor;
		buttonClickColor = buttonColor.darker();
		
		calendarDateColor = textColor;
		if (isDark(calendarDateColor)) {
			calendarGreyedColor = blend(calendarDateColor, Color.WHITE, 0.5f);
		} else {
			calendarGreyedColor = blend(calendarDateColor, Color.BLACK, 0.5f);
		}
		calendarNowColor = warningColor;
	}
	
	public static Color blend(Color c1, Color c2, float ratio) {
	    float inv = 1.0f - ratio;
	    int r = (int)(c1.getRed() * inv + c2.getRed() * ratio);
	    int g = (int)(c1.getGreen() * inv + c2.getGreen() * ratio);
	    int b = (int)(c1.getBlue() * inv + c2.getBlue() * ratio);
	    return new Color(r, g, b);
	}
	
	private boolean isDark(Color color) {
		return color.getRed() < 20 && color.getBlue() < 20 && color.getGreen() < 20;
	}
	
	public static Theme load(File themeFile) throws IOException {
		Theme theme = new Theme();
		BufferedReader br = new BufferedReader(new FileReader(themeFile));
		
		int i = 0;
		String line = br.readLine();
		while (line != null) {
			String preprocessedLine = preprocessLine(line);
			if (!preprocessedLine.isEmpty()) {
				Color parsedColor = parseLine(preprocessedLine);
				setThemeColors(theme, i, parsedColor);
				i++;
			}
			line = br.readLine();
		}

		br.close();
		
		if (i != 5) {
			throw new IOException("Not enough colors provided.");
		}
		
		theme.updateDerivedColors();
		return theme;
	}
	
	private static void setThemeColors(Theme theme, int i, Color color) {
		switch(i) {
		case 0 -> theme.baseColor = color;
		case 1 -> theme.secondaryColor = color;
		case 2 -> theme.textColor = color;
		case 3 -> theme.placeholderTextColor = color;
		case 4 -> theme.warningColor = color;
		}
	}
	
	private static String preprocessLine(String line) {
		String res = "";
		
		char[] lineArray = line.toCharArray();
		for (int i = 0; i < line.length(); i++) {
			char c = lineArray[i];
			if (c == '#') {
				res = line.substring(0, i);
				return res;
			}
		}
		
		return line;
	}
	
	private static Color parseLine(String line) throws IOException {
		int red = -1;
		int blue = -1;
		int green = -1;
		
		String[] splitLine = line.split(",");
		if (splitLine.length != 3) {
			throw new IOException("Not 3 color values in THM file.");
		}
		
		red = Integer.parseInt(splitLine[0].trim());
		blue = Integer.parseInt(splitLine[1].trim());
		green = Integer.parseInt(splitLine[2].trim());
		
		return new Color(red, blue, green);
	}
	
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
