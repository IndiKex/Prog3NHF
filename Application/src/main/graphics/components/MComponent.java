package main.graphics.components;

import java.awt.Component;

public interface MComponent {
	public void setAlignment(int vertical, int horizontal);
	public void resize(int width, int height);
	public Component getComponent();
}
