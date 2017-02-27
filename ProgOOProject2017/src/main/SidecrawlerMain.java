/**
 *
 */
package main;

import de.uni_kiel.progOOproject17.controller.PLController;
import de.uni_kiel.progOOproject17.model.PLGameModel;
import de.uni_kiel.progOOproject17.view.PLDektopView;

/**
 * @author Yannik Eikmeier
 * @since 23.02.2017
 */
public class SidecrawlerMain {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		// TODO set Title
		new PLController(new PLDektopView("Sidecrawler Test Title"),
				new PLGameModel()).start();

	}

}
