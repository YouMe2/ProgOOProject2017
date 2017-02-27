/**
 *
 */
package de.uni_kiel.progOOproject17.model;

import de.uni_kiel.progOOproject17.model.abs.TickedDataModelContainer;
import de.uni_kiel.progOOproject17.view.abs.Viewable;
import de.uni_kiel.progOOproject17.view.abs.ViewableData;
import java.awt.Point;

/**
 * @author Yannik Eikmeier
 * @since 23.02.2017
 */
public class PLGameModel extends TickedDataModelContainer {

	// scoreboard
	// bg?
	// floors?
	// entities?
	// particles?
	// sounds???

	// LevelGenerator

	// a Destroyer for the Particles
	// a Destroyer for the Objects

	public static final int LH_WIDTH = 28;
	public static final int LH_HEIGHT = 14;

	public static final int LHPIXEL_WIDTH = 15;
	public static final int LHPIXEL_HEIGHT = 35;

	public static final int GAME_WIDTH = LH_WIDTH * LHPIXEL_WIDTH; // = 420
	public static final int GAME_HEIGHT = LH_HEIGHT * LHPIXEL_HEIGHT; // = 490

	public PLGameModel(/* params ? */) {
		// TODO

	}

	@Override
	public Viewable getViewable() {

		ViewableData vD = new ViewableData();

		// TODO

		// layer0

		// layer1

		// layer2

		// usw

		return vD;
	}

	public static Point lhToGameCoord(int x, int y) {
		return new Point(x * LHPIXEL_WIDTH, y * LHPIXEL_HEIGHT);
	}

}
