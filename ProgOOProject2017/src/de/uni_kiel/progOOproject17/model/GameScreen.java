package de.uni_kiel.progOOproject17.model;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.function.Consumer;

import javax.swing.AbstractAction;
import javax.swing.Action;

import de.uni_kiel.progOOproject17.model.abs.Collidable;
import de.uni_kiel.progOOproject17.model.abs.Destroyable;
import de.uni_kiel.progOOproject17.model.abs.Distance;
import de.uni_kiel.progOOproject17.model.abs.Environment;
import de.uni_kiel.progOOproject17.model.abs.GameElement;
import de.uni_kiel.progOOproject17.model.abs.GameObject;
import de.uni_kiel.progOOproject17.model.abs.MoveCommand;
import de.uni_kiel.progOOproject17.model.levelgen.LevelGenerator;
import de.uni_kiel.progOOproject17.resources.GameProperties;
import de.uni_kiel.progOOproject17.view.abs.Viewable;

/**
 * This class represents a {@link Screen} that serves as the environment for the
 * game. It holds all the {@link GameElement}s, the {@link Scoreboard} and the
 * {@link LevelGenerator}, as well as the relevant actions for the player.
 */
public class GameScreen extends Screen implements Environment, CreationHelper, Stats {

	private final LinkedList<GameElement> gameElements;
	private final LinkedList<GameElement> createdElements;
	private final LinkedList<Destroyable> destroyedElements;

	private final Player player;
	private int screenVelocity = Integer.valueOf(GameProperties.getInstance().getProperty("startVelocity"));

	private Scoreboard scoreboard;
	private LevelGenerator levelGenerator;
	private final Action endAction;

	private long deathtime = -1;

	/**
	 * Constructs a new {@link GameScreen} which essentially constructs a fully
	 * new game. Creates a {@link Player}. Starts the {@link LevelGenerator} and
	 * initializes the player actions.
	 * 
	 * @param w
	 *            the width
	 * @param h
	 *            the height
	 * @param pauseAction
	 *            the action for when the game is paused
	 * @param endAction
	 *            the action for when the game ended (Player died)
	 */
	public GameScreen(int w, int h, Action pauseAction, Action endAction) {
		super(w, h);
		this.endAction = endAction;
		gameElements = new LinkedList<>();
		destroyedElements = new LinkedList<>();
		createdElements = new LinkedList<>();

		player = new Player(GameProperties.getInstance().getProperty("playerResKey"),
				PLBaseModel.lhToGame(3, PLBaseModel.LH_HEIGHT - 3));
		player.setPermaXVel(screenVelocity);
		scoreboard = new Scoreboard(getPlayerStats());

		levelGenerator = new LevelGenerator(this, this, () -> {

			player.addPoint();
			// speed up :D
			screenVelocity *= Double.valueOf(GameProperties.getInstance().getProperty("stageSpeedup"));
			player.setPermaXVel(screenVelocity);
		});

		levelGenerator.setRunning(true);

		putAction(InputActionKeys.P_UP, new AbstractAction() {

			/**
			 * 
			 */
			private static final long serialVersionUID = -2653404899012756232L;

			@Override
			public void actionPerformed(ActionEvent e) {
				player.setCurrMoveCommand(MoveCommand.JUMP);

			}
		});

		putAction(InputActionKeys.P_DOWN, new AbstractAction() {

			/**
			 * 
			 */
			private static final long serialVersionUID = -5121077967094929482L;

			@Override
			public void actionPerformed(ActionEvent e) {
				player.setCurrMoveCommand(MoveCommand.START_CROUCH);

			}
		});

		putAction(InputActionKeys.R_DOWN, new AbstractAction() {

			/**
			 * 
			 */
			private static final long serialVersionUID = -8089055634210864468L;

			@Override
			public void actionPerformed(ActionEvent e) {
				player.setCurrMoveCommand(MoveCommand.END_CROUCH);

			}
		});

		putAction(InputActionKeys.P_SELECT, pauseAction);

		create(player);

		// tests below:

		// create(new Floor("floor", PLGameModel.lhToGam(0,
		// PLGameModel.LH_HEIGHT - 1, PLGameModel.LH_WIDTH, 1)));

		// create(new Background("background", 0, 0, w, h));

		// PARTICLE TEST:
		// Particle particle = new Particle("partTest", 800, 0, 300, 300,
		// 1000,4);
		// create(particle);

	}

	/*
	 * (non-Javadoc)
	 *
	 * @see de.uni_kiel.progOOproject17.model.abs.Ticked#tick(long)
	 */
	@Override
	public void tick(long timestamp) {

		if (!player.isAlive())

			if (!player.isAlive()) {
				if (deathtime == -1)
					deathtime = timestamp;
				if (deathtime + 1600 < timestamp)
					endAction.actionPerformed(null);

			}

		this.setLocation((int) (player.getX() - PLBaseModel.LHPIXEL_WIDTH * 2.5), 0);

		levelGenerator.tick(timestamp);
		gameElements.forEach(new Consumer<GameElement>() {

			@Override
			public void accept(GameElement e) {

				e.tick(timestamp);
				if (e.getBoundingRect().getMaxX() < GameScreen.this.getX())
					e.destroy();

			};

		});

		scoreboard.tick(timestamp);

		gameElements.removeAll(destroyedElements);
		destroyedElements.clear();

		gameElements.addAll(createdElements);
		createdElements.clear();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see de.uni_kiel.progOOproject17.model.abs.GameCompound#getViewables()
	 */
	@Override
	public Viewable[] getViewables() {
		ArrayList<Viewable> views = new ArrayList<>();
		views.addAll(Arrays.asList(scoreboard.getViewables()));
		views.addAll(gameElements);
		return views.toArray(new Viewable[views.size()]);
	}

	/**
	 * Returns the {@link Stats} of the player.
	 * 
	 * @return the {@link Stats} of the player
	 */
	public Stats getPlayerStats() {
		return this;
	}

	/* (non-Javadoc)
	 * @see de.uni_kiel.progOOproject17.model.abs.Environment#willCollide(de.uni_kiel.progOOproject17.model.abs.Collidable, de.uni_kiel.progOOproject17.model.abs.Distance)
	 */
	@Override
	public boolean willCollide(Collidable obj, Distance dist) {
		Rectangle rect = obj.getBoundingRect();
		rect.translate(dist.x, dist.y);
		synchronized (gameElements) {
			for (GameElement o : gameElements)
				if (o instanceof Collidable)
					if (rect.intersects(o.getBoundingRect()) && !o.equals(obj))
						return true;
		}
		return false;
	}

	/**
	 * Returns true of false whether the two {@link Collidable}s will collide after o1 moves for d1.
	 * 
	 * @param o1 the 1st {@link Collidable}
	 * @param d1 the {@link Distance} o1 will move
	 * @param o2 the 2nd {@link Collidable}
	 * @return whether o1 will collide with o2 after moving d1
	 */
	private boolean willCollide(Collidable o1, Distance d1, Collidable o2) {
		if (o1 == o2)
			return false;

		Rectangle rect = o1.getBoundingRect();
		rect.translate(d1.x, d1.y);

		return rect.intersects(o2.getBoundingRect());
	}

	/* (non-Javadoc)
	 * @see de.uni_kiel.progOOproject17.model.abs.Environment#getCollisionDistance(de.uni_kiel.progOOproject17.model.abs.Collidable, de.uni_kiel.progOOproject17.model.abs.Distance)
	 */
	@Override
	public Distance getCollisionDistance(Collidable obj, Distance maxDist) {

		if (maxDist.x == 0 && maxDist.y == 0)
			return maxDist;

		ArrayList<Collidable> collObjts = getCollObjects(obj, maxDist);

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

				for (Collidable o : collObjts)
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

	/* (non-Javadoc)
	 * @see de.uni_kiel.progOOproject17.model.abs.Environment#getCollObjects(de.uni_kiel.progOOproject17.model.abs.Collidable, de.uni_kiel.progOOproject17.model.abs.Distance)
	 */
	@Override
	public ArrayList<Collidable> getCollObjects(Collidable obj, Distance dist) {

		ArrayList<Collidable> collObjts = new ArrayList<>();

		synchronized (gameElements) {
			for (GameElement o : gameElements)
				if (o instanceof Collidable) {
					Collidable c = (Collidable) o;
					if (willCollide(obj, dist, c))
						collObjts.add(c);
				}
		}
		return collObjts;
	}

	/* (non-Javadoc)
	 * @see de.uni_kiel.progOOproject17.model.abs.Environment#contacts(de.uni_kiel.progOOproject17.model.abs.Collidable, de.uni_kiel.progOOproject17.model.abs.Collidable)
	 */
	@Override
	public boolean contacts(Collidable o1, Collidable o2) {

		if (o1 == o2)
			return false;
		Rectangle rect = o1.getBoundingRect();
		rect.grow(1, 1);

		return rect.intersects(o2.getBoundingRect());
	}

	/* (non-Javadoc)
	 * @see de.uni_kiel.progOOproject17.model.abs.Environment#isOnGround(de.uni_kiel.progOOproject17.model.abs.Collidable)
	 */
	@Override
	public boolean isOnGround(Collidable obj) {
		Rectangle rect = obj.getBoundingRect();
		rect.translate(0, 1);
		synchronized (gameElements) {
			for (GameElement e : gameElements)
				if (e instanceof Collidable) {
					Collidable c = (Collidable) e;
					if (rect.intersects(c.getBoundingRect()) && !c.equals(obj))
						return true;

				}
		}

		return false;
	}

	/* (non-Javadoc)
	 * @see de.uni_kiel.progOOproject17.model.abs.Environment#forEachCollision(de.uni_kiel.progOOproject17.model.abs.Collidable, de.uni_kiel.progOOproject17.model.abs.Distance, java.util.function.Consumer)
	 */
	@Override
	public void forEachCollision(Collidable obj, Distance dist, Consumer<GameObject> consumer) {
		synchronized (gameElements) {
			for (GameElement o : gameElements)

				if (o instanceof GameObject) {
					GameObject c = (GameObject) o;
					if (willCollide(obj, dist, c))
						consumer.accept(c);

				}
		}
	}

	/* (non-Javadoc)
	 * @see de.uni_kiel.progOOproject17.model.abs.Environment#forEachContact(de.uni_kiel.progOOproject17.model.abs.Collidable, java.util.function.Consumer)
	 */
	@Override
	public void forEachContact(Collidable obj, Consumer<GameObject> consumer) {

		synchronized (gameElements) {

			for (GameElement o : gameElements)

				if (o instanceof Collidable) {

					Collidable c = (Collidable) o;
					if (contacts(obj, c))
						consumer.accept((GameObject) c);
				}
		}
	}

	/* (non-Javadoc)
	 * @see de.uni_kiel.progOOproject17.model.abs.ElementCreator#create(de.uni_kiel.progOOproject17.model.abs.GameElement)
	 */
	@Override
	public void create(GameElement g) {

		// System.out.println("Created: " + g.getResourceKey());

		createdElements.add(g);

		Environment environment = this;
		CreationHelper creationHelper = this;
		g.activate(environment, creationHelper);
	}

	@Override
	public void onDestruction(Destroyable d) {
		// System.out.println("Destroyed: " + ((GameElement)
		// d).getResourceKey());

		destroyedElements.add(d);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * de.uni_kiel.progOOproject17.model.abs.Environment#getScreenShiftDistance(
	 * )
	 */
	@Override
	public Rectangle getScreenRect() {
		return getBoundingRect();
	}

	/* (non-Javadoc)
	 * @see de.uni_kiel.progOOproject17.model.Stats#getProgress()
	 */
	@Override
	public double getProgress() {
		return levelGenerator.getProgressOf(player.getX());
	}

	/* (non-Javadoc)
	 * @see de.uni_kiel.progOOproject17.model.Stats#getPoints()
	 */
	@Override
	public int getPoints() {
		return player.getPoints();
	}

	/* (non-Javadoc)
	 * @see de.uni_kiel.progOOproject17.model.Stats#getLifes()
	 */
	@Override
	public int getLifes() {
		return player.getLifes();
	}
}