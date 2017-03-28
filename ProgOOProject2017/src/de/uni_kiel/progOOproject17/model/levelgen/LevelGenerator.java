package de.uni_kiel.progOOproject17.model.levelgen;

import de.uni_kiel.progOOproject17.model.Background;
import de.uni_kiel.progOOproject17.model.CreationHelper;
import de.uni_kiel.progOOproject17.model.Floor;
import de.uni_kiel.progOOproject17.model.PLBaseModel;
import de.uni_kiel.progOOproject17.model.abs.Environment;
import de.uni_kiel.progOOproject17.model.abs.GameElement;
import de.uni_kiel.progOOproject17.model.abs.Ticked;
import de.uni_kiel.progOOproject17.resources.GameProperties;
import de.uni_kiel.progOOproject17.resources.ResourceManager;
import java.awt.Rectangle;
import java.util.Collection;

/**
 * The <code>LevelGenerator</code> class is responsible for generating two
 * major game elements:
 * <ol>
 * <li>Background images</li>
 * <li>{@link Stage}s</li>
 * </ol>
 * For that purpose, it depends on an {@link Environment} instance (to monitor
 * the screen rectangle as in {@link Environment#getScreenRect()}) and a
 * {@link CreationHelper} instance to create the <code>GameElements</code>.
 * <p>
 * In addition, the constructor takes a <code>Runnable</code> that can be used
 * as a listener to be notified whenever a new <code>Stage</code> is being
 * spawned.
 * <p>
 * Note that the term level is used synonymous to stage.
 *
 */
public class LevelGenerator implements Ticked {

	/**
	 * The height of the generated floor, supposed to be equivalent to the
	 * height of one window on the skyscraper.
	 */
	public static final int FLOOR_HEIGHT = PLBaseModel.lhToGame(0, 1).y;
	/**
	 * The position of the floor.
	 */
	public static final int FLOOR_POS = PLBaseModel.GAME_HEIGHT - LevelGenerator.FLOOR_HEIGHT;

	/**
	 * The standard reskey for the enemys.
	 */
	public static final String ENEMYRESKEY = GameProperties.getInstance().getProperty("enemyResKey");
	
	/**
	 * The environment to figure out where the screen rect is positioned.
	 */
	private final Environment environment;
	/**
	 * The <code>CreationHelper</code> used to create all new
	 * <code>GameElement</code>s.
	 */
	private final CreationHelper createHelper;
	/**
	 * The listener to be notified whenever a new stage is being spawned.
	 */
	private Runnable stageSpawnListener;

	/**
	 * Indicates whether the level generator is making sure that new terrain is
	 * being generated or not.
	 */
	private boolean running = false;

	/**
	 * Stores how much terrain was generated before the last time this was
	 * performed.
	 */
	private int lastGeneratedTerrain;
	/**
	 * Stores how much terrain was generated up to now.
	 */
	private int generatedTerrain;
	/**
	 * Stores how much background was generated up to now.
	 */
	private int generatedBackground;

	/**
	 * The current stage as an index in the list of stages.
	 */
	private int currentStage;
	/**
	 * List of all stages that will ever be used in the course of the game.
	 */
	private Stage[] stages;

	private final Rectangle screenRectangle;
	
	/**
	 * Creates a new inactive level generator based on the given environment,
	 * creation helper and stage spawn listener.
	 *
	 * @param environment
	 *            the environment
	 * @param createHelper
	 *            the creation helper
	 * @param stageSpawnListener
	 *            the stage spawn listener that is being notified every time a
	 *            new stage is being generated
	 */
	public LevelGenerator(Environment environment, CreationHelper createHelper, Runnable stageSpawnListener, Rectangle screenRect) {
		this.createHelper = createHelper;
		this.environment = environment;
		this.stageSpawnListener = stageSpawnListener;
		this.screenRectangle = screenRect;
		generatedTerrain = generatedBackground = 0;
		currentStage = 0;
		stages = Stage.values();
	}

	/**
	 * Sets whether the level generator is running or not. If it is, it is going
	 * to make sure that there's always enough background visible inside the
	 * screen rect and that there's always enemies being spawned in increasingly
	 * difficult constellations.
	 *
	 * @param running
	 *            whether the level generator is running or not
	 */
	public void setRunning(boolean running) {
		this.running = running;
	}

	@Override
	public void tick(long timestamp) {
		if (running) {
			
			int rightScreenBorder = screenRectangle.x + screenRectangle.width;
			if (generatedTerrain <= rightScreenBorder) {
				lastGeneratedTerrain = generatedTerrain;
				generatedTerrain = spawnStage(generatedTerrain);
			}
			if (generatedBackground <= rightScreenBorder)
				generatedBackground = spawnBackground(generatedBackground);
		}
	}

	/**
	 * Spawns in a new randomly selected sequence of obstacles for the player to
	 * master, and returns the position up to which the terrain is being
	 * generated.
	 *
	 * @param stageStart
	 *            the position the stage is supposed to start at
	 * @return the position up to which the terrain is being generated
	 */
	public int spawnStage(int stageStart) {
		Stage stage = stages[currentStage];
		// Create first floor
		if (currentStage == 0) {
			int floorLength = PLBaseModel.GAME_WIDTH + 800;
			Floor startFloor = new Floor("floor", 0, FLOOR_POS, floorLength, FLOOR_HEIGHT);
			stageStart = floorLength;
			createHelper.create(startFloor);
		} else if (stageSpawnListener != null)
				stageSpawnListener.run();
		
		// Create the stage
		Collection<GameElement> c;
		int stageEnd;
		synchronized (stage) {
			c = stage.create(stageStart);
			stageEnd = stage.getLastStageEnd();
		}
		for (GameElement element : c)
			createHelper.create(element);
		int nextStage = currentStage + 1;
		if (nextStage < stages.length)
			currentStage = nextStage;
		
//		System.out.println("Spawned stage " + stage + " from " + stageStart + " to " + stageEnd);
		return stageEnd;
	}

	/**
	 * Spawns one new {@link Background} at the given position and returns its
	 * width.
	 *
	 * @param backgroundStart
	 *            the position at which the background will be generated
	 * @return the width of the generated background
	 */
	private int spawnBackground(int backgroundStart) {
		Background b = new Background("bg", backgroundStart, 0, 1024, PLBaseModel.GAME_HEIGHT);
		int backgroundEnd = backgroundStart + b.getView().getWidth();
		createHelper.create(b);
//		System.out.println("Spawned background from " + backgroundStart + " to " + backgroundEnd);
		return backgroundEnd;
	}

	/**
	 * Computes the ratio of the given x to the length of the current stage.
	 * This method can be used to get the progress of an object inside the
	 * current stage.
	 *
	 * @param x
	 *            the x position of the object
	 * @return the progress as a double value between 0, inclusive, and 1,
	 *         exclusive
	 */
	public double getProgressOf(int x) {
		return (double) (x - lastGeneratedTerrain) / (generatedTerrain - lastGeneratedTerrain);
	}

	/**
	 * Return the index of the current stage
	 *
	 * @return the index of the current stage
	 */
	public int getCurrentStage() {
		return currentStage;
	}
}