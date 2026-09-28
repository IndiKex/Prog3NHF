package main.graphics.panels.map.stop;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

public class Stop {
	public int baseX, baseY;
	float scale, translateX, translateY;
	public int SIZE = 8;
	
	boolean hovered = false;
	boolean selected = false;
	
	public String name;
	
	public Stop(Point baseCoords, String name) {
		this.baseX = (int)baseCoords.getX();
		this.baseY = (int)baseCoords.getY();
		this.name = name;
	}
	
	public void updateTransform(float scale, float translateX, float translateY) {
		this.scale = scale;
		this.translateX = translateX;
		this.translateY = translateY;
	}
	
	public Point getScreenPos() {
		float screenX = baseX * scale + translateX;
		float screenY = baseY * scale + translateY;
		return new Point((int)screenX, (int)screenY);
	}
	
	public void setHovered(boolean hovered) {
		this.hovered = hovered;
		onHovered();
	}
	
	public void setSelected(boolean selected) {
		this.selected = selected;
		onSelected();
	}
	
	private void onHovered() {
		
	}
	
	private void onSelected() {
		
	}
	
	public void draw(Graphics g) {
		Point screenPos = getScreenPos();
		
		Color lastColor = g.getColor();
		
		if (selected)
			g.setColor(Color.RED);
		else
			g.setColor(Color.ORANGE);
		
		int size = SIZE;
		if (hovered)
			size = (int)(SIZE * 1.5);
		g.fillOval((int)(screenPos.getX() - size/2), (int)(screenPos.getY() - size/2), size, size);
		
		g.setColor(lastColor);
	}
}
