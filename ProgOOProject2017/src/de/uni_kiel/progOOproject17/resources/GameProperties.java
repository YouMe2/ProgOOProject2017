package de.uni_kiel.progOOproject17.resources;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class GameProperties {
	private static final String BUNDLE_NAME = "de.uni_kiel.progOOproject17.resources.properties.game_properties";

	private static GameProperties instance;

	private final ResourceBundle resourceBundle;

	private GameProperties() {
		resourceBundle = ResourceBundle.getBundle(BUNDLE_NAME);
	}

	public static GameProperties getInstance() {
		if (instance == null)
			instance = new GameProperties();
		return instance;
	}

	public String getProperty(String key) {
		try {
			return resourceBundle.getString(key);
		} catch (MissingResourceException e) {
			return '!' + key + '!';
		}
	}
}
