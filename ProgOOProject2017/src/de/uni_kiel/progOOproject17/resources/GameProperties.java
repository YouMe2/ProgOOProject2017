package de.uni_kiel.progOOproject17.resources;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * A class that provides reading the game properties as defined in the
 * .properties-file. Reading is key-value based. Only <code>String</code>s can
 * be read.
 *
 */
public class GameProperties {
	/**
	 * The properties file without file ending
	 */
	private static final String BUNDLE_NAME = "de.uni_kiel.progOOproject17.resources.properties.game_properties";

	/**
	 * The static instance of the class ensures that there's only one instance
	 * created (via factory method). Might be useful later if problems with the
	 * concurrency have to be solved.
	 */
	private static GameProperties instance;

	/**
	 * The instance of the <code>ResourceBundle</code> used to read the values.
	 */
	private final ResourceBundle resourceBundle;

	/**
	 * Private constructor, only the factory method {@link #getInstance()} can
	 * call it!
	 */
	private GameProperties() {
		resourceBundle = ResourceBundle.getBundle(BUNDLE_NAME);
	}

	/**
	 * Factory method to get or create the instance of this class that allows
	 * reading game properties.
	 *
	 * @return the only instance of <code>GameProperties</code> there is
	 */
	public static GameProperties getInstance() {
		if (instance == null)
			instance = new GameProperties();
		return instance;
	}

	/**
	 * Reads a game property from the file. Behaves just like
	 * {@link ResourceBundle#getString(String)} and only catches the
	 * <code>MissingResourceException</code>. In that case,
	 * <code>'!' + key + '!'</code> is returned.
	 *
	 * @param key
	 *            the key for the game property to be read
	 * @return the property, or <code>'!' + key + '!'</code> if the key cannot
	 *         be mapped
	 */
	public String getProperty(String key) {
		try {
			return resourceBundle.getString(key);
		} catch (MissingResourceException e) {
			return '!' + key + '!';
		}
	}
}
