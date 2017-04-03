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
	
	private Environment environment;


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
		this.environment = new SimpleEnvironment(gameElements);

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
		//TODO fix HITBOX OPTION
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