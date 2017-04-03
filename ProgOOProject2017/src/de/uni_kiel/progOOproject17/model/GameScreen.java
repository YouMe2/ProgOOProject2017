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
import de.uni_kiel.progOOproject17.model.abs.Hitbox;
import de.uni_kiel.progOOproject17.model.abs.MoveCommand;
import de.uni_kiel.progOOproject17.model.abs.Screen;
import de.uni_kiel.progOOproject17.model.levelgen.LevelGenerator;
import de.uni_kiel.progOOproject17.resources.GameProperties;
import de.uni_kiel.progOOproject17.resources.ResourceManager;
import de.uni_kiel.progOOproject17.view.abs.Viewable;

/**
 * This class represents a {@link Screen} that serves as the environment for the
 * game. It holds all the {@link GameElement}s, the {@link Scoreboard} and the
 * {@link LevelGenerator}, as well as the relevant actions for the player.
 */
public class GameScreen extends Screen implements Stats {

	private final LinkedList<GameElement> gameElements;
	private final LinkedList<GameElement> createdElements;
	private final LinkedList<Destroyable> destroyedElements;

	private final Player player;
	private int screenVelocity = Integer.valueOf(GameProperties.getInstance().getProperty("startVelocity"));

	private Scoreboard scoreboard;
	private LevelGenerator levelGenerator;
	private final Action endAction;

	private final Rectangle inGameScreenBoarder;
	
	private Environment environment = new Environment() {

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * de.uni_kiel.progOOproject17.model.abs.Environment#willCollide(de.
		 * uni_kiel.progOOproject17.model.abs.Collidable,
		 * de.uni_kiel.progOOproject17.model.abs.Distance)
		 */
		@Override
		public boolean willCollide(Collidable coll, Distance dist) {
			Hitbox tHitbox = coll.getHitbox();
			tHitbox.translate(dist);
			synchronized (gameElements) {
				for (GameElement e : gameElements)
					if (e instanceof Collidable)
						if (!e.equals(coll) && tHitbox.intersects(((Collidable) e).getHitbox()))
							return true;
			}
			return false;
		}

		/**
		 * Returns true of false whether the two {@link Collidable}s will
		 * collide after o1 moves for d1.
		 * 
		 * @param o1
		 *            the 1st {@link Collidable}
		 * @param d1
		 *            the {@link Distance} o1 will move
		 * @param o2
		 *            the 2nd {@link Collidable}
		 * @return whether o1 will collide with o2 after moving d1
		 */
		private boolean willCollide(Collidable c1, Distance d1, Collidable c2) {
			if (c1 == c2)
				return false;

			Hitbox tHitbox = c1.getHitbox();
			tHitbox.translate(d1);
			return c2.getHitbox().intersects(tHitbox);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see de.uni_kiel.progOOproject17.model.abs.Environment#
		 * getCollisionDistance(de.uni_kiel.progOOproject17.model.abs.
		 * Collidable, de.uni_kiel.progOOproject17.model.abs.Distance)
		 */
		@Override
		public Distance getCollisionDistance(Collidable coll, Distance maxDist) {

			if (maxDist.x == 0 && maxDist.y == 0)
				return maxDist;

			ArrayList<Collidable> collObjts = getCollObjects(coll, maxDist);

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
					dist.scale(signD);

					for (Collidable c : collObjts)
						// wenn collision mit nur einem anderen object ->
						// nächtse
						if (willCollide(coll, dist, c))
							continue nextPos;

					// sonst: eine mögliche Distance gefunden!

					if (dist.getSqLength() > currBestDist.getSqLength())
						// und sie ist besser!
						currBestDist = dist;

				}

			return currBestDist;

		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * de.uni_kiel.progOOproject17.model.abs.Environment#getCollObjects(de.
		 * uni_kiel.progOOproject17.model.abs.Collidable,
		 * de.uni_kiel.progOOproject17.model.abs.Distance)
		 */
		@Override
		public ArrayList<Collidable> getCollObjects(Collidable coll, Distance dist) {

			ArrayList<Collidable> collObjts = new ArrayList<>();

			synchronized (gameElements) {
				for (GameElement e : gameElements)
					if (e instanceof Collidable) {
						Collidable c = (Collidable) e;
						if (willCollide(coll, dist, c))
							collObjts.add(c);
					}
			}
			return collObjts;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see de.uni_kiel.progOOproject17.model.abs.Environment#contacts(de.
		 * uni_kiel.progOOproject17.model.abs.Collidable,
		 * de.uni_kiel.progOOproject17.model.abs.Collidable)
		 */
		@Override
		public boolean contacts(Collidable c1, Collidable c2) {
			if (c1 == c2)
				return false;
			return c1.getHitbox().contacts(c2.getHitbox());
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see de.uni_kiel.progOOproject17.model.abs.Environment#isOnGround(de.
		 * uni_kiel.progOOproject17.model.abs.Collidable)
		 */
		@Override
		public boolean isOnGround(Collidable coll) {
			Hitbox tHitbox = coll.getHitbox();
			tHitbox.translate(new Distance(0, 1));
			synchronized (gameElements) {
				for (GameElement e : gameElements)
					if (e instanceof Collidable) {
						Collidable c = (Collidable) e;
						if (!c.equals(coll) && c.getHitbox().intersects(tHitbox))
							return true;

					}
			}

			return false;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * de.uni_kiel.progOOproject17.model.abs.Environment#forEachCollision(de
		 * .uni_kiel.progOOproject17.model.abs.Collidable,
		 * de.uni_kiel.progOOproject17.model.abs.Distance,
		 * java.util.function.Consumer)
		 */
		@Override
		public void forEachCollision(Collidable coll, Distance dist, Consumer<GameObject> consumer) {
			synchronized (gameElements) {
				for (GameElement e : gameElements)

					if (e instanceof GameObject) {
						GameObject o = (GameObject) e;
						if (willCollide(coll, dist, o))
							consumer.accept(o);

					}
			}
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * de.uni_kiel.progOOproject17.model.abs.Environment#forEachContact(de.
		 * uni_kiel.progOOproject17.model.abs.Collidable,
		 * java.util.function.Consumer)
		 */
		@Override
		public void forEachContact(Collidable coll, Consumer<GameObject> consumer) {

			synchronized (gameElements) {

				for (GameElement e : gameElements)

					if (e instanceof GameObject) {
						GameObject o = (GameObject) e;
						if (contacts(coll, o))
							consumer.accept(o);
					}
			}
		}


	};

	private CreationHelper creatHelp = new CreationHelper() {
		/*
		 * (non-Javadoc)
		 * 
		 * @see de.uni_kiel.progOOproject17.model.abs.ElementCreator#create(de.
		 * uni_kiel.progOOproject17.model.abs.GameElement)
		 */
		@Override
		public void create(GameElement g) {

			// System.out.println("Created: " + g.getResourceKey());

			createdElements.add(g);
			g.getView().setRelativeAnchor(inGameScreenBoarder);
			g.activate(environment, this);
		}

		@Override
		public void onDestruction(Destroyable d) {
			// System.out.println("Destroyed: " + ((GameElement)
			// d).getResourceKey());

			destroyedElements.add(d);
		}

	};

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
		this.inGameScreenBoarder = new Rectangle(0, 0, w, h);
		this.endAction = endAction;
		gameElements = new LinkedList<>();
		destroyedElements = new LinkedList<>();
		createdElements = new LinkedList<>();

		player = new Player(GameProperties.getInstance().getProperty("playerResKey"),
				PLBaseModel.lhToGame(3, PLBaseModel.LH_HEIGHT - 3));
		player.setPermaXVel(screenVelocity);
		scoreboard = new Scoreboard(getPlayerStats());

		levelGenerator = new LevelGenerator(environment, creatHelp, () -> {
			player.addPoint();
			player.addLife();

			// speed up :D
			screenVelocity *= Double.valueOf(GameProperties.getInstance().getProperty("stageSpeedup"));
			player.setPermaXVel(screenVelocity);
			ResourceManager.getInstance().getSound("speedup").play();
		}, inGameScreenBoarder);

		levelGenerator.setRunning(true);

		putAction(InputActionKey.UP_P, new AbstractAction() {

			/**
			 * 
			 */
			private static final long serialVersionUID = -2653404899012756232L;

			@Override
			public void actionPerformed(ActionEvent e) {
				player.setCurrMoveCommand(MoveCommand.JUMP);

			}
		});

		putAction(InputActionKey.DOWN_P, new AbstractAction() {

			/**
			 * 
			 */
			private static final long serialVersionUID = -5121077967094929482L;

			@Override
			public void actionPerformed(ActionEvent e) {
				player.setCurrMoveCommand(MoveCommand.CROUCH_START);

			}
		});

		putAction(InputActionKey.DOWN_R, new AbstractAction() {

			/**
			 * 
			 */
			private static final long serialVersionUID = -8089055634210864468L;

			@Override
			public void actionPerformed(ActionEvent e) {
				player.setCurrMoveCommand(MoveCommand.CROUCH_END);

			}
		});

		putAction(InputActionKey.SELECT_P, pauseAction);

		creatHelp.create(player);

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

		inGameScreenBoarder.setLocation((int) (player.getHitbox().getX() - PLBaseModel.LHPIXEL_WIDTH * 2.5), 0);

		levelGenerator.tick(timestamp);
		gameElements.forEach(new Consumer<GameElement>() {

			@Override
			public void accept(GameElement e) {

				e.tick(timestamp);
				if (e.getView().getViewRect().getMaxX() < GameScreen.this.getX())
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
		//TODO DEBUG HITBOX OPTION
//		for (GameElement e : gameElements) {
//			if (e instanceof Collidable)
//				views.addAll(Arrays.asList(((Collidable) e).getHitbox().getDebugViewables()));
//		}
		
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.uni_kiel.progOOproject17.model.Stats#getProgress()
	 */
	@Override
	public double getProgress() {
		return levelGenerator.getProgressOf(player.getHitbox().getX());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.uni_kiel.progOOproject17.model.Stats#getPoints()
	 */
	@Override
	public int getPoints() {
		return player.getPoints();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.uni_kiel.progOOproject17.model.Stats#getLifes()
	 */
	@Override
	public int getLifes() {
		return player.getLifes();
	}
}