package de.uni_kiel.progOOproject17.view;

import java.awt.Image;
import java.util.HashMap;

public class Resources {

	private static Resources instance;

	private HashMap<String, Image> resources = null;

	private Resources() {}

	public static Resources getInstance() {
		if (instance == null)
			instance = new Resources();
		return instance;
	}

	public void init() {
		resources = new HashMap<>();
		// TODO alle hinzufügen
	}

	public Image get(String identifier) {
		return resources.get(identifier);
	}

}
