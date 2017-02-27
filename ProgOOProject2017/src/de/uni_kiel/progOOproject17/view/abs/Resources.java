package de.uni_kiel.progOOproject17.view.abs;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import javax.imageio.ImageIO;

public class Resources {

	private static Resources instance;

	private final String resourcesFolder;
	private final HashMap<String, Image> resources;

	private Resources() {
		resourcesFolder = "/resources/";
		resources = new HashMap<>();
	}

	public static Resources getInstance() {
		if (instance == null)
			instance = new Resources();
		return instance;
	}

	public void init() {
		Class<? extends Resources> thisClass = getClass();
		URL url = thisClass.getResource(resourcesFolder);
		String path = url.getFile();
		File parent = new File(path);
		File[] resList = parent.listFiles();
		System.out.println(Arrays.toString(resList));
		for (File res : resList) {
			String resKey = generateKeyFromFile(res);
			String resPath = resourcesFolder + res.getName();
			InputStream resStream = thisClass.getResourceAsStream(resPath);
			Image resource = null;
			try {
				resource = ImageIO.read(resStream);
			} catch (IOException e) {
				e.printStackTrace();
			}
			resources.put(resKey, resource);
		}
	}

	private String generateKeyFromFile(File file) {
		String fileName = file.getName();
		int fileExtPos = fileName.lastIndexOf(".");
		if (fileExtPos != -1)
			fileName = fileName.substring(0, fileExtPos);
		return fileName;
	}

	public Image get(String identifier) {
		return resources.get(identifier);
	}

}
