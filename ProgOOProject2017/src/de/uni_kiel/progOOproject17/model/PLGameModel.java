package de.uni_kiel.progOOproject17.model;

import de.uni_kiel.progOOproject17.model.abs.Destroyable;
import de.uni_kiel.progOOproject17.model.abs.Distance;
import de.uni_kiel.progOOproject17.model.abs.Environment;
import de.uni_kiel.progOOproject17.model.abs.GameObject;
import de.uni_kiel.progOOproject17.model.abs.TickedBaseModel;
import de.uni_kiel.progOOproject17.view.abs.Viewable;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.function.Consumer;

public class PLGameModel extends TickedBaseModel implements Environment, CreationHelper {

	public static final int LH_WIDTH = 28;
	public static final int LH_HEIGHT = 14;

	public static final int LHPIXEL_WIDTH = 15;
	public static final int LHPIXEL_HEIGHT = 35;

	public static final int GAME_WIDTH = LH_WIDTH * LHPIXEL_WIDTH; // = 420
	public static final int GAME_HEIGHT = LH_HEIGHT * LHPIXEL_HEIGHT; // = 490

	public static final String ACTIONKEY_PLAYER_STARTCROUCH = "start crouching";
	public static final String ACTIONKEY_PLAYER_STOPCROUCH = "stop crouching";
	public static final String ACTIONKEY_PLAYER_JUMP = "jump";

	private final LinkedList<GameObject> gameObjects;
	private final LinkedList<Particle> particles;

	private final LinkedList<Destroyable> destroyedElements;

	private LevelGeneratorDEMO levelGenerator;

	// scoreboard
	private final Scoreboard scoreboard;
	// bg
	private Background gBG = new Background("background", 0, 0, GAME_WIDTH, GAME_HEIGHT);
	// keep reference to player
	private Player player;

	private boolean running;

	public PLGameModel() {
		gameObjects = new LinkedList<>();
		particles = new LinkedList<>();

		destroyedElements = new LinkedList<>();

		levelGenerator = new LevelGeneratorDEMO(this, this);

		Floor floor = new Floor("floor", lhToGam(0, LH_HEIGHT - 1, LH_WIDTH, 1), this, this);
		Floor barier = new Floor(null, lhToGam(-20, 0, 1, LH_HEIGHT), this, this);
		barier.setDeadly(true);

		player = new Player("player", lhToGame(3, LH_HEIGHT - 3), this, this);

		scoreboard = new Scoreboard(player);

		levelGenerator.setRunning(true);

		create(floor);
		create(barier);
		create(player);

		running = true;
		// PARTICLE TEST:
		// Particle particle = new Particle("partTest", 120, 120, 60, 60, 1000,
		// 3, this);
		// create(particle);
	}

	public static Point lhToGame(float x, float y) {
		return new Point(Math.round(x * LHPIXEL_WIDTH), Math.round(y * LHPIXEL_HEIGHT));
	}

	public static Rectangle lhToGam(float x, float y, float w, float h) {
		return new Rectangle(Math.round(x * LHPIXEL_WIDTH), Math.round(y * LHPIXEL_HEIGHT),
				Math.round(w * LHPIXEL_WIDTH), Math.round(h * LHPIXEL_HEIGHT));
	}

	@Override
	public Viewable[] getViewables() {

		ArrayList<Viewable> views = new ArrayList<>();

		views.add(gBG);
		views.addAll(Arrays.asList(scoreboard.getViewables()));
		views.addAll(gameObjects);
		views.addAll(particles);

		return views.toArray(new Viewable[views.size()]);
	}

	@Override
	public void tick(long timestamp) {
		// TODO so wird nicht alles geticked!

		if (running) {
			scoreboard.tick(timestamp);
			// level generator
			levelGenerator.tick(timestamp);
			// tick all components
			gameObjects.forEach(c -> c.tick(timestamp));
			particles.forEach(c -> c.tick(timestamp));

			// remove dead elements
			gameObjects.removeAll(destroyedElements);
			particles.removeAll(destroyedElements);

			System.out.println(
					"Dest (" + destroyedElements.size() + "): " + Arrays.toString(destroyedElements.toArray()));
			System.out.println("GObj (" + gameObjects.size() + "): " + Arrays.toString(gameObjects.toArray()));
			System.out.println("Part (" + particles.size() + "): " + Arrays.toString(particles.toArray()));
			System.out.println("---");

			destroyedElements.clear();
		} else {
			// TODO was sonst? Menüs?
		}
	}

	@Override
	public boolean willCollide(GameObject obj, Distance dist) {
		Rectangle rect = obj.getBoundingRect();
		rect.translate(dist.x, dist.y);
		synchronized (gameObjects) {
			for (GameObject o : gameObjects)
				if (rect.intersects(o.getBoundingRect()) && !o.equals(obj))
					return true;
		}

		return false;
	}

	private boolean willCollide(GameObject o1, Distance d1, GameObject o2) {
		if (o1 == o2)
			return false;

		Rectangle rect = o1.getBoundingRect();
		rect.translate(d1.x, d1.y);

		return rect.intersects(o2.getBoundingRect());
	}

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
					if (willCollide(obj, dist, o))
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
			for (GameObject o : gameObjects)
				if (willCollide(obj, dist, o))
					collObjts.add(o);
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
			for (GameObject o : gameObjects)
				if (rect.intersects(o.getBoundingRect()) && !o.equals(obj))
					return true;
		}

		return false;
	}

	@Override
	public void forEachCollision(GameObject obj, Distance dist, Consumer<GameObject> consumer) {
		synchronized (gameObjects) {
			for (GameObject o : gameObjects)
				if (willCollide(obj, dist, o))
					consumer.accept(o);
		}
	}

	@Override
	public void forEachContact(GameObject obj, Consumer<GameObject> consumer) {

		synchronized (gameObjects) {
			for (GameObject o : gameObjects)
				if (contacts(obj, o))
					consumer.accept(o);
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
		if (d == player)
			stopGame();
		else
			destroyedElements.add(d);
	}

	private void stopGame() {
		running = false;
		showEndScreen();
	}

	private void showEndScreen() {
		// TODO end screen
	}

}
