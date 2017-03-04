package de.uni_kiel.progOOproject17.resources;

import java.awt.Image;
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

	public Image getImage(String identifier) {
		Image img = imageResources.get(identifier);
		if (img == null) {
			String resPath = imagesFolder + identifier + ".png";
			InputStream resStream = thisClass.getResourceAsStream(resPath);
			try {
				img = ImageIO.read(resStream);
			} catch (IOException e) {
				e.printStackTrace();
			}
			imageResources.put(identifier, img);
		}
		return img;
	}

	public Sound getSound(String identifier) {
		Sound sound = soundResources.get(identifier);
		if (sound == null) {
			String resPath = soundsFolder + identifier + ".wav";
			try {
				URL url = new URL(resPath);
				AudioInputStream inputStream = AudioSystem
						.getAudioInputStream(url);
				Clip clip = AudioSystem.getClip();
				clip.open(inputStream);
				sound = new Sound(clip);
				soundResources.put(identifier, sound);
			} catch (UnsupportedAudioFileException | IOException
					| LineUnavailableException e) {
				e.printStackTrace();
			}
		}
		return sound;
	}

}
