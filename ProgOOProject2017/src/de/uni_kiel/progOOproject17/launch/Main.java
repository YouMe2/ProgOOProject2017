/**
 *
 */
package de.uni_kiel.progOOproject17.launch;

import de.uni_kiel.progOOproject17.controller.PLController;
import de.uni_kiel.progOOproject17.model.PLGameModel;
import de.uni_kiel.progOOproject17.resources.ResourceManager;
import de.uni_kiel.progOOproject17.view.PLDektopView;

/**
 * @author Yannik Eikmeier
 * @since 23.02.2017
 */
public class Main {

    // TODO Viewsaufbauen
	// TODO PLGameModel aufbauen
	// TODO LevelGenerator
	// TODO Scoreboard
	// TODO Lighthouse util
	// FIXME CurrentModificationError

	// TODO bei applyGravity einen check auf Kollision einbeziehen.
	// Dazu: Die GameEntities werden zwar von der Schwerkraft beeinflusst,
	// allerdings prüft nur der Player auf Kollision (und auch der bekommt nur
	// Damage, wird aber nicht angehalten). Außerdem muss noch mal die Grenze
	// zwischen Dimensions und Velocities besser gezogen werden, siehe anderes
	// todo.

	public static void main(String[] args) {

		ResourceManager.getInstance().init();

		new PLController(
				new PLDektopView(
						"Project Lighthouze - Yannik Eikemeier und Steffen Trog"),
				new PLGameModel()).start();

	}

}
