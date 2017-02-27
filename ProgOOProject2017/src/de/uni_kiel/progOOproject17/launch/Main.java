/**
 *
 */
package de.uni_kiel.progOOproject17.launch;

import de.uni_kiel.progOOproject17.controller.PLController;
import de.uni_kiel.progOOproject17.model.PLGameModel;
import de.uni_kiel.progOOproject17.view.PLDektopView;
import de.uni_kiel.progOOproject17.view.abs.Resources;

/**
 * @author Yannik Eikmeier
 * @since 23.02.2017
 */
public class Main {

	//TODO class Velocity statt Dimension
	//TODO PLGameModel aufbauen
	//TODO LevelGenerator
	//TODO Scoreboard
	//TODO Lighthouse util
	//TODO Resources
	

	
	public static void main(String[] args) {

		Resources.getInstance().init();

		new PLController(new PLDektopView("Project Lighthouze - Yannik Eikemeier und Steffen Trog"),
				new PLGameModel()).start();

	}

}
