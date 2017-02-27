package de.uni_kiel.progOOproject17.view;

import java.awt.Image;
import java.util.HashMap;

public class Resources {

	public final HashMap<String, Image> IMAGES = new HashMap<>();

	/*
	 * test.png 		-> test
	 * test_0-low.png	-> test_0-low
	 * 
	 * 
	 * 
	 * 
	*/
	
	
	public Resources() {
		
		//TODO
		
	}

	public static Resources getInstance() {
		//TODO
		return null;
	}
	
	public Image get(String key){
		return IMAGES.get(key);
	}
	
}
