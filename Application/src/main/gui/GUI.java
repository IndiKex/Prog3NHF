package main.gui;

public interface GUI {
	public void initialize(Runnable onExitFunction);
	public void start();
	public void close();
}
