/**
 *
 */
package de.uni_kiel.progOOproject17.model;

import de.uni_kiel.progOOproject17.model.abs.TickedBaseModel;
import de.uni_kiel.progOOproject17.view.abs.Viewable;
import java.awt.Point;
import java.util.ArrayList;

/**
 * @author Yannik Eikmeier
 * @since 23.02.2017
 */
public class PLGameModel extends TickedBaseModel {

	// scoreboard
	Scoreboard gSB = new Scoreboard(0, 0, GAME_WIDTH, LHPIXEL_HEIGHT * 2);
	// bg
	Background gBG = new Background("gray", 0, 0, GAME_WIDTH, GAME_HEIGHT);

	// TODO LevelGenerator

	public static final int LH_WIDTH = 28;
	public static final int LH_HEIGHT = 14;

	public static final int LHPIXEL_WIDTH = 15;
	public static final int LHPIXEL_HEIGHT = 35;

	public static final int GAME_WIDTH = LH_WIDTH * LHPIXEL_WIDTH; // = 420
	public static final int GAME_HEIGHT = LH_HEIGHT * LHPIXEL_HEIGHT; // = 490

	public static final String ACTIONKEY_PLAYER_STARTCROUCH = "start crouching";
	public static final String ACTIONKEY_PLAYER_STOPCROUCH = "stop crouching";
	public static final String ACTIONKEY_PLAYER_JUMP = "jump";
	public PLGameModel() {

		new Floor("cyan", 0, GAME_HEIGHT - LHPIXEL_HEIGHT, GAME_WIDTH,
				LHPIXEL_HEIGHT);

		new Player("player", GAME_WIDTH / 2, GAME_HEIGHT / 2);

		new Enemy("enemy", GAME_WIDTH - 2 * LHPIXEL_WIDTH,
				GAME_HEIGHT - 2 * LHPIXEL_HEIGHT);

		new Block("yellow", 50, 50, 50, 50);
		

	}

	public static Point lhToGameCoord(int x, int y) {
		return new Point(x * LHPIXEL_WIDTH, y * LHPIXEL_HEIGHT);
	}

	@Override
	public Viewable[] getViewables() {

		ArrayList<Viewable> views = new ArrayList<>();

		views.add(gBG);
		// views.addAll(Arrays.asList(gSB.getViewables()));
		views.addAll(GameObject.OBJECTS);
		views.addAll(Particle.PARTICLES);

		return views.toArray(new Viewable[views.size()]);
	}

	@Override
	public void tick(long timestamp) {
		// tick all components
		GameComponent.COMPONENTS.forEach(c -> c.tick(timestamp));

		// remove dead game objects
		GameComponent.COMPONENTS.removeAll(GameObject.DESTROYED_OBJECTS);
		GameObject.OBJECTS.removeAll(GameObject.DESTROYED_OBJECTS);
		// remove dead particles
		GameComponent.COMPONENTS.removeAll(Particle.DESTROYED_PARTICLES);
		Particle.PARTICLES.removeAll(Particle.DESTROYED_PARTICLES);
	}

}
