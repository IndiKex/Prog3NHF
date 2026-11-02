package main.gui.config;

import java.io.File;

import main.gui.config.lang.Language;
import main.gui.config.orient.Orientation;
import main.gui.config.theme.Theme;

public class Config {
	public Theme theme = new Theme();
	public Language language = new Language(new File("assets/configs/languages/en.lang"));
	public Orientation orientation = new Orientation();
	
	// TODO
}
