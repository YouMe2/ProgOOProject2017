package de.uni_kiel.progOOproject17.launch;

import de.uni_kiel.progOOproject17.controller.PLController;
import de.uni_kiel.progOOproject17.model.PLGameModel;
import de.uni_kiel.progOOproject17.resources.ResourceManager;
import de.uni_kiel.progOOproject17.view.PLDektopView;

public class Main {

	// TODO COMMENTS

	public static void main(String[] args) {

		ResourceManager.getInstance().init();

		new PLController(new PLDektopView("Project Lighthouze - Yannik Eikmeier und Steffen Trog"), new PLGameModel())
				.start(0);

	}

}
