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

	private ResourceManager() {
		imagesFolder = "/de/uni_kiel/progOOproject17/resources/images/";
		soundsFolder = "/de/uni_kiel/progOOproject17/resources/sounds/";
		imageResources = new HashMap<>();
		soundResources = new HashMap<>();
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
		System.out.println(
				"Loading " + resList.length + " image(s):\n" + Arrays.toString(resList).replaceAll(", ", ",\n\t"));
		for (File res : resList) {
			String resKey = generateKeyFromFile(res);
			String resPath = imagesFolder + res.getName();
			InputStream resStream = thisClass.getResourceAsStream(resPath);
			Image resource = null;
			try {
				resource = ImageIO.read(resStream);
			} catch (IOException e) {
				e.printStackTrace();
			}
			imageResources.put(resKey, resource);
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

	public Image getImage(String identifier) {
		return imageResources.get(identifier);
	}

	public Sound getSound(String identifier) {
		return soundResources.get(identifier);
	}

}
