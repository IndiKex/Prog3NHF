package main.gui.config;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import main.gui.config.lang.Language;
import main.gui.config.orient.Orientation;
import main.gui.config.theme.Theme;

public class Config implements Serializable {
	public static final File configFile = new File("assets/configs/config.dat");
	public Theme theme;
	public Language language;
	public Orientation orientation;
	
	public Config() {
		try {
			theme = Theme.load(new File("assets/configs/themes/dark.thm"));
			language = Language.load(new File("assets/configs/languages/en.lang"));
			orientation = new Orientation();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// TODO
}
