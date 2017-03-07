package de.uni_kiel.progOOproject17.launch;

import de.uni_kiel.progOOproject17.controller.PLController;
import de.uni_kiel.progOOproject17.model.PLBaseModel;
import de.uni_kiel.progOOproject17.resources.GameProperties;
import de.uni_kiel.progOOproject17.resources.ResourceManager;
import de.uni_kiel.progOOproject17.view.PLDektopView;

/**
 * 50 lines comment here, deutsch?
 * 
 * 
 * TODO explain what our interpretation of MVC is:
 * 		Controller intermediates between View an model
 * 		Controller holds all the strings
 * 		Model builds the game it self with all the logik
 * 		View builds up the frame
 * 		View manages the InputActions 
 * TODO description of class hierarchy
 * TODO update type hierarchy
 * <p>
 * <img src="model_class_hierarchy.png" title="Class hierarchy of the model" alt
 * ="Class hierarchy of the model" width="800" height="355">
 *
 */
public class Main {

	public static void main(String[] args) {

		ResourceManager.getInstance().init();

		new PLController(new PLDektopView(GameProperties.getInstance().getProperty("titleText")), new PLBaseModel())
				.start(0);

	}

}
