package de.uni_kiel.progOOproject17.model;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.function.Consumer;

import de.uni_kiel.progOOproject17.model.abs.TickedBaseModel;
import de.uni_kiel.progOOproject17.view.abs.Viewable;

public class PLGameModel extends TickedBaseModel
		implements Environment, GameObjectCreator, ParticleCreator, DestroyListener {

	private final LinkedList<GameObject> gameObjects;
	private final LinkedList<Particle> particles;

	private final LinkedList<Destroyable> destroyedElements;

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
	public static final String ACTIONKEY_PLAYER_JUMP = "jump";

	public PLGameModel() {
		gameObjects = new LinkedList<>();
		particles = new LinkedList<>();

		destroyedElements = new LinkedList<>();

		levelGenerator = new LevelGenerator();

		Floor floor = new Floor("cyan", 0, GAME_HEIGHT - LHPIXEL_HEIGHT, GAME_WIDTH, LHPIXEL_HEIGHT, this);
		Player player = new Player("player", GAME_WIDTH / 2, GAME_HEIGHT / 2, this);
		Enemy enemy = new Enemy("enemy", GAME_WIDTH - 2 * LHPIXEL_WIDTH, GAME_HEIGHT - 2 * LHPIXEL_HEIGHT, this);
		Block block = new Block("yellow", 50, 50, 50, 50, this);

		create(floor);
		create(player);
		create(enemy);
		create(block);
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
		
		System.out.println(gameObjects);
		
		
		//TODO so wird nicht alles geticked!
		// level generator
		levelGenerator.tick(timestamp);
		// tick all components
		gameObjects.forEach(c -> c.tick(timestamp));
		particles.forEach(c -> c.tick(timestamp));

		// remove dead elements
		gameObjects.removeAll(destroyedElements); //FIXME DOES NOt WORK!
		particles.removeAll(destroyedElements);

		destroyedElements.clear();
	}

	@Override
	public boolean willCollide(GameObject obj, Distance dist) {
		Rectangle rect = obj.getBoundingRect();
		rect.translate(dist.x, dist.y);
		synchronized (gameObjects) {
			for (GameObject o : gameObjects) {
				if (o == obj)
					return false;

				if (rect.intersects(obj.getBoundingRect()))
					return true;
			}
		}

		return false;
	}

	private boolean collides2(GameObject o1, GameObject o2, Distance d1) {
		if (o1 == o2)
			return false;

		// added this sort of clone
		Rectangle rect = o1.getBoundingRect();
		rect.translate(d1.x, d1.y);
		
		return rect.intersects(o2.getBoundingRect());
	}

	/**
	 * TODO!! FIXME
	 */
	@Override
	public Distance getCollisionDistance(GameObject obj, Distance maxDist) {

		if (maxDist.x == 0 && maxDist.y == 0)
			return maxDist;

		ArrayList<GameObject> collObjts = getCollObjects(obj, maxDist);

		if (collObjts.isEmpty())
			return new Distance(maxDist);

		// sonst:

		Distance signD = maxDist.getSignDistance();
		Distance absDist = maxDist.getAbsDistance();
		Distance currBestDist = new Distance(0, 0);

		for (int dx = absDist.x; dx >= 0; dx--)
			nextPos: for (int dy = absDist.y; dy >= 0; dy--) {

				// für jede mögliche position:
				Distance dist = new Distance(dx, dy);
				dist.multiply(signD);

				for (GameObject o : collObjts)
					// wenn collision mit nur einem anderen object -> nächtse
					if (collides2(obj, o, dist))
						continue nextPos;

				// sonst: eine mögliche Distance gefunden!

				if (dist.getSqLenghth() > currBestDist.getSqLenghth())
					// und sie ist besser!
					currBestDist = dist;

			}

		return currBestDist;

	}

	@Override
	public ArrayList<GameObject> getCollObjects(GameObject obj, Distance dist) {

		ArrayList<GameObject> collObjts = new ArrayList<>();

		synchronized (gameObjects) {
			for (GameObject o : gameObjects) {

				if (collides2(obj, o, dist))
					collObjts.add(obj);
			}
		}
		return collObjts;
	}

	@Override
	public boolean contacts(GameObject o1, GameObject o2) {

		if (o1 == o2)
			return false;
		Rectangle rect = o1.getBoundingRect();
		rect.grow(1, 1);

		return rect.intersects(o2.getBoundingRect());
	}

	@Override
	public boolean isOnGround(GameObject obj) {
		Rectangle rect = obj.getBoundingRect();
		rect.translate(0, 1);
		synchronized (gameObjects) {
			for (GameObject o : gameObjects) {
				if (o == obj)
					return false;

				if (rect.intersects(obj.getBoundingRect()))
					return true;
			}
		}

		return false;
	}

	@Override
	public void forEachCollision(GameObject obj, Distance dist, Consumer<GameObject> consumer) {
		synchronized (gameObjects) {
			for (GameObject o : gameObjects)
				if (collides2(obj, o, dist))
					consumer.accept(obj);
		}
	}

	@Override
	public void forEachContact(GameObject obj, Consumer<GameObject> consumer) {

		synchronized (gameObjects) {
			for (GameObject o : gameObjects)
				if (contacts(obj, o))
					consumer.accept(obj);
		}
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

	@Override
	public void onDestruction(Destroyable d) {
		destroyedElements.add(d);
	}

}
