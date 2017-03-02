package de.uni_kiel.progOOproject17.model;

import de.uni_kiel.progOOproject17.model.abs.TickedBaseModel;
import de.uni_kiel.progOOproject17.view.abs.Viewable;
import java.awt.Point;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.function.Consumer;

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
		// level generator
		levelGenerator.tick(timestamp);
		// tick all components
		gameObjects.forEach(c -> c.tick(timestamp));
		particles.forEach(c -> c.tick(timestamp));

		// remove dead elements
		gameObjects.removeAll(destroyedElements);
		particles.removeAll(destroyedElements);

		destroyedElements.clear();
	}

	// /**
	// * safe
	// */
	// @Override
	// public boolean collides(GameObject obj) {
	// if (obj == this)
	// return false;
	//
	// return getBoundingRect().intersects(obj.getBoundingRect());
	// }
	//
	// @Override
	// public boolean contacts(GameObject obj) {
	//
	// if (obj == this)
	// return false;
	// Rectangle rect = getBoundingRect();
	// rect.grow(1, 1);
	//
	// return rect.intersects(obj.getBoundingRect());
	// }
	//
	// /**
	// *
	// * safe ...i guess will no longer make changes dist
	// */
	// @Override
	// public boolean willCollide(GameObject obj, Distance dist) {
	// if (obj == this)
	// return false;
	//
	// // added this sort of clone
	// Rectangle rect = getBoundingRect();
	// rect.translate(dist.x, dist.y);
	// return rect.intersects(obj.getBoundingRect());
	// }
	//
	// /**
	// * safe and better will no longer make changes dist
	// */
	// @Override
	// public boolean willCollide(List<GameObject> gObjts, Distance dist) {
	// Rectangle rect = getBoundingRect();
	// rect.translate(dist.x, dist.y);
	// synchronized (gObjts) {
	// for (GameObject obj : gObjts) {
	// if (obj == this)
	// return false;
	//
	// if (rect.intersects(obj.getBoundingRect()))
	// return true;
	// }
	// }
	//
	// return false;
	// }
	//
	// /**
	// * fixed ... i hope so
	// */
	// @Override
	// public Distance getCollisionDistance(GameObject obj, Distance maxDist) {
	//
	// if (maxDist.x == 0 && maxDist.y == 0)
	// return maxDist;
	//
	// if (collides(obj))
	// return new Distance();
	//
	// // deckt den fall, dass (obj == this) gilt ab
	// if (!willCollide(obj, maxDist))
	// return new Distance(maxDist);
	//
	// // sonst:
	//
	// Distance signD = maxDist.getSignDistance();
	// Distance absDist = maxDist.getAbsDistance();
	// Distance currBestDist = new Distance(0, 0);
	//
	// for (int dx = absDist.x; dx >= 0; dx--)
	// for (int dy = absDist.y; dy >= 0; dy--) {
	//
	// // für jede Distance im max bereich:
	//
	// Distance dist = new Distance(dx, dy);
	// dist.multiply(signD);
	//
	// if (!willCollide(obj, dist) && dist.getSqLenghth() >
	// currBestDist.getSqLenghth())
	// // möglich und besser? dann:
	// currBestDist = dist;
	//
	// }
	//
	// return currBestDist;
	// }
	//
	// /**
	// * safe not tested tho
	// *
	// */
	// @Override
	// public Distance getCollisionDistance(List<GameObject> gObjts, Distance
	// maxDist) {
	//
	// if (maxDist.x == 0 && maxDist.y == 0)
	// return maxDist;
	//
	// ArrayList<GameObject> collObjts = new ArrayList<>();
	//
	// synchronized (gObjts) {
	// for (GameObject obj : gObjts) {
	//
	// if (collides(obj))
	// return new Distance(); // (0 ,0)
	//
	// if (willCollide(obj, maxDist))
	// collObjts.add(obj);
	// }
	// }
	//
	// if (collObjts.isEmpty())
	// return new Distance(maxDist);
	//
	// // sonst:
	//
	// Distance signD = maxDist.getSignDistance();
	// Distance absDist = maxDist.getAbsDistance();
	// Distance currBestDist = new Distance(0, 0);
	//
	// for (int dx = absDist.x; dx >= 0; dx--)
	// nextPos: for (int dy = absDist.y; dy >= 0; dy--) {
	//
	// // für jede mögliche position:
	// Distance dist = new Distance(dx, dy);
	// dist.multiply(signD);
	//
	// for (GameObject obj : collObjts)
	// // wenn collision mit nur einem anderen object -> nächtse
	// // pos
	// if (willCollide(obj, dist))
	// continue nextPos;
	//
	// // sonst: eine mögliche Distance gefunden!
	//
	// if (dist.getSqLenghth() > currBestDist.getSqLenghth())
	// // und sie ist besser!
	// currBestDist = dist;
	//
	// }
	//
	// return currBestDist;
	//
	// }
	//
	// @Override
	// public ArrayList<GameObject> getCollObjects(List<GameObject> gObjs,
	// Distance dist) {
	//
	// ArrayList<GameObject> collObjts = new ArrayList<>();
	//
	// synchronized (gObjs) {
	// for (GameObject obj : gObjs) {
	//
	// if (collides(obj))
	// collObjts.add(obj);
	//
	// if (willCollide(obj, dist))
	// collObjts.add(obj);
	// }
	// }
	//
	// return collObjts;
	//
	// }
	//
	// @Override
	// public void forEachCollision(List<GameObject> gObjs, Distance dist,
	// Consumer<GameObject> cons) {
	// synchronized (gObjs) {
	// for (GameObject obj : gObjs)
	// if (collides(obj) || willCollide(obj, dist))
	// cons.accept(obj);
	// }
	// }
	//
	// @Override
	// public void forEachContact(List<GameObject> gObjs, Consumer<GameObject>
	// cons) {
	// synchronized (gObjs) {
	// for (GameObject obj : gObjs)
	// if (contacts(obj))
	// cons.accept(obj);
	// }
	// }

	@Override
	public boolean willCollide(GameObject obj, Distance dist) {
		return false;
	}

	@Override
	public Distance getCollisionDistance(GameObject obj, Distance maxDist) {
		return null;
	}

	@Override
	public ArrayList<GameObject> getCollObjects(GameObject g, Distance dist) {
		return null;
	}

	@Override
	public boolean contacts(GameObject obj) {
		return false;
	}

	@Override
	public boolean isOnGround(GameObject obj) {
		return false;
	}

	@Override
	public void forEachCollision(GameObject obj, Distance dist, Consumer<GameObject> consumer) {
	}

	@Override
	public void forEachContact(GameObject obj, Consumer<GameObject> consumer) {
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
