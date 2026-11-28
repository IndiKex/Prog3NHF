package main.gui.config;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;

import main.gui.config.lang.Language;
import main.gui.config.theme.Theme;

public class Config implements Serializable {
	public static final File dataFolder = new File("data");
	public static final File configFolder = new File(dataFolder, "configs");
	public static final File configFile = new File(configFolder, "config.dat");
	public Theme theme;
	public Language language;
	
	public Config() {
		try {
			if (!dataFolder.exists()) {
				dataFolder.mkdir();
			}
			if (!configFolder.exists()) {
				configFolder.mkdir();
			}
			
			theme = Theme.load(new File("assets/configs/themes/dark.thm"));
			language = Language.load(new File("assets/configs/languages/hu.lang"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// TODO
}
