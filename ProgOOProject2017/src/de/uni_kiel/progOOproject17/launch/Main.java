package de.uni_kiel.progOOproject17.launch;

import de.uni_kiel.progOOproject17.controller.PLController;
import de.uni_kiel.progOOproject17.model.PLGameModel;
import de.uni_kiel.progOOproject17.resources.ResourceManager;
import de.uni_kiel.progOOproject17.view.PLDektopView;

/**
 * TODO description of class hierarchy
 * <p>
 * <img src="model_class_hierarchy.png" title="Class hierarchy of the model">
 *
 */
public class Main {

	// TODO COMMENTS

	public static void main(String[] args) {

		ResourceManager.getInstance().init();

		new PLController(new PLDektopView("Project Lighthouze - Yannik Eikmeier und Steffen Trog"), new PLGameModel())
				.start(0);

	}

}
