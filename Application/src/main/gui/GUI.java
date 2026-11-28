package main.gui;

import main.backend.Backend;

public interface GUI {
	public void initialize(Backend backend, Runnable onExitFunction);
	public void start();
	public void close();
}
