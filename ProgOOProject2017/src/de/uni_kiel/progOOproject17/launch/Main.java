package de.uni_kiel.progOOproject17.launch;

import de.uni_kiel.progOOproject17.controller.PLController;
import de.uni_kiel.progOOproject17.model.PLGameModel;
import de.uni_kiel.progOOproject17.resources.ResourceManager;
import de.uni_kiel.progOOproject17.view.PLDektopView;

public class Main {

	// TODO Actions im Controller aufbauen und in den Views setzen.
	// TODO PLGameModel aufbauen
	// TODO LevelGenerator
	// TODO Scoreboard

	// TODO Lighthouse util
	// FIXME CurrentModificationError

	public static void main(String[] args) {

		ResourceManager.getInstance().init();

		new PLController(new PLDektopView("Project Lighthouze - Yannik Eikemeier und Steffen Trog"), new PLGameModel())
				.start();

	}

}
