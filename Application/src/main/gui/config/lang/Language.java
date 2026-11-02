package main.gui.config.lang;

import java.util.Map;

public class Language {
	public Map<String, String> data = Map.of(
		"menuConfigButtonUser", "User",
		"menuConfigButtonSettings", "Settings",
		"menuConfigButtonAbout", "About"
	);
	
	public String get(String dataID) {
		return data.get(dataID);
	}
	
	// TODO
}
