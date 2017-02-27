/**
 *
 */
package de.uni_kiel.progOOproject17.model;

import de.uni_kiel.progOOproject17.model.abs.TickedBaseModel;
import de.uni_kiel.progOOproject17.view.abs.Viewable;
import de.uni_kiel.progOOproject17.view.abs.ViewableData;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author Yannik Eikmeier
 * @since 23.02.2017
 */
public class PLGameModel extends TickedBaseModel {

	// scoreboard
	
	// bg
	Background gBG = new Background("background", 0, 0, GAME_WIDTH, GAME_HEIGHT);
	// particles?
	
	// sounds???

	// LevelGenerator


	public static final int LH_WIDTH = 28;
	public static final int LH_HEIGHT = 14;

	public static final int LHPIXEL_WIDTH = 15;
	public static final int LHPIXEL_HEIGHT = 35;

	public static final int GAME_WIDTH = LH_WIDTH * LHPIXEL_WIDTH; // = 420
	public static final int GAME_HEIGHT = LH_HEIGHT * LHPIXEL_HEIGHT; // = 490

	public PLGameModel() {
		

		new Floor("floor", 0, GAME_HEIGHT-LHPIXEL_HEIGHT, GAME_WIDTH, LHPIXEL_HEIGHT);
		
		new Player("player", GAME_WIDTH/2, GAME_HEIGHT/2);
		
		new Enemy("enemy", GAME_WIDTH-2*LHPIXEL_WIDTH, GAME_HEIGHT-2*LHPIXEL_HEIGHT);

		new Block("block", 50, 50, 50, 50);
		
	}

	

	public static Point lhToGameCoord(int x, int y) {
		return new Point(x * LHPIXEL_WIDTH, y * LHPIXEL_HEIGHT);
	}



	@Override
	public Viewable[] getViewables() {
		
		ArrayList<Viewable> views = new ArrayList<>();
		
		views.add(gBG);
		views.addAll(GameObject.OBJECTS);

		return views.toArray(new Viewable[views.size()]);
	}


	@Override
	public void tick(long timestamp) {
		// TODO tick all....
		
	}

}
