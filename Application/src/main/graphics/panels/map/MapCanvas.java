package main.graphics.panels.map;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class MapCanvas extends JPanel {
	BufferedImage img;
	boolean isDrawn = false;
	public float scale = 1.0f;
	float prevScale = scale;
	float minScale;
	public float translateX = 0.0f;
	public float translateY = 0.0f;
	Point prevMousePos;
	
	StopCanvas stopCanvas;
	
	public MapCanvas() {
		try {
			img = ImageIO.read(new File("assets/magyarorszag-terkep.png"));
			invertMapColors();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		setLayout(new BorderLayout());
		
		stopCanvas = new StopCanvas();
		add(stopCanvas);
		
		bindScroll();
		bindClick();
		bindDrag();
		bindMove();
		
		updateStopsTransform();
		repaint();
	}
	
	private void bindScroll() {
		addMouseWheelListener(new MouseWheelListener() {
			public void mouseWheelMoved(MouseWheelEvent e) {
				if (e.getWheelRotation() < 0) {
					scale *= 1.1f;
				} else {
					scale /= 1.1f;
				}
				scale = Float.max(scale, minScale);
				
				translateX = e.getX() - (e.getX() - translateX) * (scale / prevScale);
				translateY = e.getY() - (e.getY() - translateY) * (scale / prevScale);
				translateBound();
				
				
				prevScale = scale;
				updateStopsTransform();
				repaint();
			}
		});
	}
	
	private void bindClick() {
		addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				prevMousePos = e.getPoint();
				
				stopCanvas.onMouseClicked(e);
			}
		});
	}
	
	private void bindDrag() {
		addMouseMotionListener(new MouseMotionAdapter() {
			public void mouseDragged(MouseEvent e) {
				double dx = e.getX() - prevMousePos.getX();
				double dy = e.getY() - prevMousePos.getY();
				
				translateX += dx;
				translateY += dy;
				
				translateBound();
				
				prevMousePos = e.getPoint();
				updateStopsTransform();
				repaint();
			}
		});
	}
	
	private void bindMove() {
		addMouseMotionListener(new MouseMotionAdapter() {
			public void mouseMoved(MouseEvent e) {
				stopCanvas.onMouseMoved(e);
				repaint();
			}
		});
	}
	
	private void calculateFirstDraw() {
		float minScaleX = (float) getWidth() / img.getWidth();
		float minScaleY = (float) getHeight() / img.getHeight();
		minScale = Float.max(minScaleX, minScaleY);
		scale = Float.max(minScale, scale);
		prevScale = scale;
		updateStopsTransform();
	}
	
	private void translateBound() {
		translateX = Float.min(translateX, 0);
		translateY = Float.min(translateY, 0);
		translateX = Float.max(translateX, getWidth() - img.getWidth() * scale);
		translateY = Float.max(translateY, getHeight() - img.getHeight() * scale);
	}
	
	private void updateStopsTransform() {
		if (stopCanvas != null) {
			stopCanvas.updateTransform(scale, translateX, translateY);
		}
	}
	
	private void invertMapColors() {
		int width = img.getWidth();
		int height = img.getHeight();
		
		BufferedImage inverted = new BufferedImage(width, height, img.getType());
				
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				int rgb = img.getRGB(x,y); // alpha - red - green - blue -> 8 bit for each
				
				int alpha = (rgb >> 24) & 0xff;
				int red = (rgb >> 16) & 0xff;
				int green = (rgb >> 8) & 0xff;
				int blue = rgb & 0xff;
				
				int invertedRGB = (alpha << 24) + ((255 - red) << 16) + ((255 - green) << 8) + (255 - blue);
						
				inverted.setRGB(x,y,invertedRGB);
			}
		}
		
		img = inverted;
	}
	
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		if (!isDrawn) {
			calculateFirstDraw();
			isDrawn = true;
		}
		
		Graphics2D g2d = (Graphics2D) g;
		
		AffineTransform at = new AffineTransform();
		at.translate(translateX, translateY);
		at.scale(scale, scale);
		
		g2d.drawImage(img, at, this);
		
		//stopCanvas.draw(g, scale, translateX, translateY);
		
	}
}
