/**
 *
 */
package de.uni_kiel.progOOproject17.model;

import de.uni_kiel.progOOproject17.model.abs.TickedBaseModel;
import de.uni_kiel.progOOproject17.view.abs.Viewable;
import java.awt.Point;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 * @author Yannik Eikmeier
 * @since 23.02.2017
 */
public class PLGameModel extends TickedBaseModel
		implements GameObjectCreator, ParticleCreator {

	private final LinkedList<GameObject> gameObjects;
	private final LinkedList<GameObject> destroyedGameObjects;

	private final LinkedList<Particle> particles;
	private final LinkedList<GameObject> destroyedParticles;

	private LevelGenerator levelGenerator;

	// scoreboard
	Scoreboard gSB = new Scoreboard(0, 0, GAME_WIDTH, LHPIXEL_HEIGHT * 2);
	// bg
	Background gBG = new Background("gray", 0, 0, GAME_WIDTH, GAME_HEIGHT);

	public static final int LH_WIDTH = 28;
	public static final int LH_HEIGHT = 14;

	public static final int LHPIXEL_WIDTH = 15;
	public static final int LHPIXEL_HEIGHT = 35;

	public static final int GAME_WIDTH = LH_WIDTH * LHPIXEL_WIDTH; // = 420
	public static final int GAME_HEIGHT = LH_HEIGHT * LHPIXEL_HEIGHT; // = 490

	public static final String ACTIONKEY_PLAYER_STARTCROUCH = "start crouching";
	public static final String ACTIONKEY_PLAYER_STOPCROUCH = "stop crouching";
<<<<<<< HEAD
	public static final String ACTIONKEY_PLAYER_JUMP = "jump";

=======
	public static final String ACTIONKEY_PLAYER_JUMP = "jump";
>>>>>>> refs/heads/master
	public PLGameModel() {
		gameObjects = new LinkedList<>();
		destroyedGameObjects = new LinkedList<>();
		particles = new LinkedList<>();
		destroyedParticles = new LinkedList<>();

		Floor floor = new Floor("cyan", 0, GAME_HEIGHT - LHPIXEL_HEIGHT,
				GAME_WIDTH, LHPIXEL_HEIGHT);
		Player player = new Player("player", GAME_WIDTH / 2, GAME_HEIGHT / 2);
		Enemy enemy = new Enemy("enemy", GAME_WIDTH - 2 * LHPIXEL_WIDTH,
				GAME_HEIGHT - 2 * LHPIXEL_HEIGHT);
		Block block = new Block("yellow", 50, 50, 50, 50);

<<<<<<< HEAD
		create(floor);
		create(player);
		create(enemy);
		create(block);
=======
		new Block("yellow", 50, 50, 50, 50);
		

>>>>>>> refs/heads/master
	}

	public static Point lhToGameCoord(int x, int y) {
		return new Point(x * LHPIXEL_WIDTH, y * LHPIXEL_HEIGHT);
	}

	@Override
	public Viewable[] getViewables() {

		ArrayList<Viewable> views = new ArrayList<>();

		views.add(gBG);
		// views.addAll(Arrays.asList(gSB.getViewables()));
		views.addAll(gameObjects);
		views.addAll(particles);

		return views.toArray(new Viewable[views.size()]);
	}

	@Override
	public void tick(long timestamp) {
		// level generator
		levelGenerator.tick(timestamp);
		// tick all components
		gameObjects.forEach(c -> c.tick(timestamp));
		particles.forEach(c -> c.tick(timestamp));

		// remove dead game objects
		gameObjects.removeAll(destroyedGameObjects);
		destroyedGameObjects.clear();
		// remove dead particles
		particles.removeAll(destroyedParticles);
		particles.clear();
	}

	@Override
	public void create(GameObject g) {
		gameObjects.add(g);
		g.activate();
	}

	@Override
	public void create(Particle p) {
		particles.add(p);
		p.activate();
	}

}
