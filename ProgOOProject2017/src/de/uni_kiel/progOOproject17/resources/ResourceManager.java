package de.uni_kiel.progOOproject17.resources;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class ResourceManager {

	/**
	 * The static instance of the class ensures that there's only one instance
	 * created (via factory method). Might be useful later if problems with the
	 * concurrency have to be solved.
	 */
	private static ResourceManager instance;

	/**
	 * The folder from which to load the images.
	 */
	private final String imagesFolder = "/de/uni_kiel/progOOproject17/resources/images/";
	/**
	 * The folder from which to load the sounds.
	 */
	private final String soundsFolder = "/de/uni_kiel/progOOproject17/resources/sounds/";
	/**
	 * A map to store loaded images by their identifier in order to avoid
	 * loading the same resource multiple times.
	 */
	private final HashMap<String, Image> imageResources = new HashMap<>();
	/**
	 * A map to store loaded sounds by their identifier in order to avoid
	 * loading the same resource multiple times.
	 */
	private final HashMap<String, Sound> soundResources = new HashMap<>();

	/**
	 * A <code>Class</code> instance of this class. Used repeatedly throughout
	 * the loading of the resources.
	 */
	private Class<? extends ResourceManager> thisClass = getClass();

	/**
	 * Private constructor, only the factory method {@link #getInstance()} can
	 * call it!
	 */
	private ResourceManager() {
	}

	/**
	 * Factory method to get or create the instance of this class that allows
	 * loading resources.
	 *
	 * @return the only instance of <code>ResourceManager</code> there is
	 */
	public static ResourceManager getInstance() {
		if (instance == null)
			instance = new ResourceManager();
		return instance;
	}

	/**
	 * Pre-loads all available resources if this if possible.
	 * <p>
	 * Usually, resources can be preloaded when running the game inside an IDE.
	 * This allows the resources folders to list their subfiles so that all
	 * images and sounds can be loaded on startup.
	 * <p>
	 * On the other hand, when running the game from a JAR file, querying the
	 * children of a folder is not supported. As a result, the resources will be
	 * loaded lazily. This results in slight delay during the game.
	 *
	 * @return <code>true</code> if pre-loading the resources was successful,
	 *         <code>false</code> otherwise
	 */
	public boolean init() {
		boolean res = true;
		Class<? extends ResourceManager> thisClass = getClass();
		// init images
		URL url = thisClass.getResource(imagesFolder);
		String path = url.getFile();
		File parent = new File(path);
		File[] resList = parent.listFiles();
		// might be inside JAR, not inside IDE
		// -> no list of files available
		// -> lazy loading required
		if (resList == null) {
			System.out.println("Couldn't preload images. Expect slight delays during the game.");
			res = false;
		} else {
			System.out.println("Loading " + resList.length + " image(s):");
			for (File file : resList) {
				String resKey = generateKeyFromFile(file);
				System.out.println(resKey + ":\t" + file.getAbsolutePath());
				Image img = loadImage(resKey);
				imageResources.put(resKey, img);
			}
		}
		// init sounds
		url = thisClass.getResource(soundsFolder);
		path = url.getFile();
		parent = new File(path);
		resList = parent.listFiles();
		// might be inside JAR, not inside IDE
		// -> no list of files available
		// -> lazy loading required
		if (resList == null) {
			System.out.println("Couldn't preload sounds. Expect slight delays during the game.");
			res = false;
		} else {
			System.out.println("Loading " + resList.length + " sound(s):");
			for (File file : resList) {
				String resKey = generateKeyFromFile(file);
				System.out.println(resKey + ":\t" + file.getAbsolutePath());
				Sound sound = loadSound(resKey);
				soundResources.put(resKey, sound);
			}
		}
		return res;
	}

	/**
	 * Generates the resource identifier associated with a given file.
	 *
	 * @param file
	 *            the file
	 * @return the resource identifier associated with the file
	 */
	private String generateKeyFromFile(File file) {
		String fileName = file.getName();
		// remove file ending
		int fileExtPos = fileName.lastIndexOf(".");
		if (fileExtPos != -1)
			fileName = fileName.substring(0, fileExtPos);
		return fileName;
	}

	/**
	 * Loads a PNG image from the disk. The resource identifier is the filename
	 * without ending. If no resource if found or an IOException occurs,
	 * <code>null</code> is returned.
	 *
	 * @param identifier
	 *            the resource identifier
	 * @return the image loaded from disk
	 */
	private Image loadImage(String identifier) {
		String path = imagesFolder + identifier + ".png";
		InputStream inputStream = thisClass.getResourceAsStream(path);
		if (inputStream == null) {
			System.err.println("Resource " + identifier + " (" + path + ") not found!");
			return null;
		}
		Image resource = null;
		try {
			resource = ImageIO.read(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return resource;
	}

	/**
	 * Loads a wavesound file from the disk. The resource identifier is the
	 * filename without ending. If no resource if found or an IOException
	 * occurs, <code>null</code> is returned.
	 *
	 * @param identifier
	 *            the resource identifier
	 * @return the image loaded from disk
	 */
	private Sound loadSound(String identifier) {
		String path = soundsFolder + identifier + ".wav";
		URL res = thisClass.getResource(path);
		Sound sound = null;
		try {
			AudioInputStream inputStream = AudioSystem.getAudioInputStream(res);
			Clip clip = AudioSystem.getClip();
			clip.open(inputStream);
			sound = new Sound(clip);
		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
			e.printStackTrace();
		}
		return sound;
	}

	/**
	 * Gets an image by its resource identifier. The resource identifier is the
	 * filename without ending. If this image is cached, the cached instance is
	 * being used. Otherwise, the image will be loaded from disk before.
	 *
	 * @param identifier
	 *            the resource identifier
	 * @return the image, or <code>null</code>, if no such resource exists
	 */
	public Image getImage(String identifier) {
		Image img = imageResources.get(identifier);
		if (img == null) {
			img = loadImage(identifier);
			// don't store null references
			if (img != null)
				imageResources.put(identifier, img);
		}
		return img;
	}

	/**
	 * Gets a sound by its resource identifier. The resource identifier is the
	 * filename without ending. If this sound is cached, the cached instance is
	 * being used. Otherwise, the sound will be loaded from disk before.
	 *
	 * @param identifier
	 *            the resource identifier
	 * @return the sound, or <code>null</code>, if no such resource exists
	 */
	public Sound getSound(String identifier) {
		Sound sound = soundResources.get(identifier);
		if (sound == null) {
			sound = loadSound(identifier);
			// don't store null references
			if (sound != null)
				soundResources.put(identifier, sound);
		}
		return sound;
	}
}
