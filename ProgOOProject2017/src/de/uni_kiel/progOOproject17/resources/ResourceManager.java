package de.uni_kiel.progOOproject17.resources;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class ResourceManager {

	private static ResourceManager instance;

	private final String imagesFolder;
	private final String soundsFolder;
	private final HashMap<String, Image> imageResources;
	private final HashMap<String, Sound> soundResources;

	private Class<? extends ResourceManager> thisClass;

	private ResourceManager() {
		imagesFolder = "/de/uni_kiel/progOOproject17/resources/images/";
		soundsFolder = "/de/uni_kiel/progOOproject17/resources/sounds/";
		imageResources = new HashMap<>();
		soundResources = new HashMap<>();
		thisClass = getClass();
	}

	public static ResourceManager getInstance() {
		if (instance == null)
			instance = new ResourceManager();
		return instance;
	}

	public void init() {
		Class<? extends ResourceManager> thisClass = getClass();
		// init images
		URL url = thisClass.getResource(imagesFolder);
		String path = url.getFile();
		File parent = new File(path);
		File[] resList = parent.listFiles();
		// might be inside JAR, not inside IDE
		// -> no list of files available
		// -> lazy loading required
		// abort method in that case
		if (resList == null) {
			System.out.println("Couldn't preload resources. Expect slight delays during the game.");
			return;
		}
		System.out.println(
				"Loading " + resList.length + " image(s):\n" + Arrays.toString(resList).replaceAll(", ", ",\n\t"));
		for (File file : resList) {
			String resKey = generateKeyFromFile(file);
			Image img = loadImage(resKey);
			imageResources.put(resKey, img);
		}
		// init sounds
		url = thisClass.getResource(soundsFolder);
		path = url.getFile();
		parent = new File(path);
		resList = parent.listFiles();
		System.out.println(
				"Loading " + resList.length + " sound(s):\n" + Arrays.toString(resList).replaceAll(", ", ",\n\t"));
		for (File res : resList) {
			String resKey = generateKeyFromFile(res);
			try {
				AudioInputStream inputStream = AudioSystem.getAudioInputStream(res);
				Clip clip = AudioSystem.getClip();
				clip.open(inputStream);
				Sound resource = new Sound(clip);
				soundResources.put(resKey, resource);
			} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
				e.printStackTrace();
			}
		}
	}

	private String generateKeyFromFile(File file) {
		String fileName = file.getName();
		int fileExtPos = fileName.lastIndexOf(".");
		if (fileExtPos != -1)
			fileName = fileName.substring(0, fileExtPos);
		return fileName;
	}

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

	public Image getImage(String identifier) {
		Image img = imageResources.get(identifier);
		if (img == null) {
			img = loadImage(identifier);
			imageResources.put(identifier, img);
		}
		return img;
	}

	public Sound getSound(String identifier) {
		Sound sound = soundResources.get(identifier);
		if (sound == null) {
			sound = loadSound(identifier);
			soundResources.put(identifier, sound);
		}
		return sound;
	}

}
