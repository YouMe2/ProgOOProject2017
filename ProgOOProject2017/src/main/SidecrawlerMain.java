/**
 * 
 */
package main;

import de.uni_kiel.progOOproject17.controller.SCController;
import de.uni_kiel.progOOproject17.model.GameModel;
import de.uni_kiel.progOOproject17.view.SCDektopView;

/**
 * @author Yannik Eikmeier
 * @since 23.02.2017
 *
 */
public class SidecrawlerMain {

    /**
     * @param args
     */
    public static void main(String[] args) {
	
	new SCController(new SCDektopView("Sidecrawler Test Title", 400, 400), new GameModel()).start();
	
	

    }

}
