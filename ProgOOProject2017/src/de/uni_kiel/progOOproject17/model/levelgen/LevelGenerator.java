package de.uni_kiel.progOOproject17.model.levelgen;

import de.uni_kiel.progOOproject17.model.Background;
import de.uni_kiel.progOOproject17.model.CreationHelper;
import de.uni_kiel.progOOproject17.model.Floor;
import de.uni_kiel.progOOproject17.model.PLGameModel;
import de.uni_kiel.progOOproject17.model.abs.Environment;
import de.uni_kiel.progOOproject17.model.abs.GameElement;
import de.uni_kiel.progOOproject17.model.abs.Ticked;
import java.awt.Rectangle;
import java.util.Collection;

public class LevelGenerator implements Ticked {

	public static final int FLOOR_HEIGHT = PLGameModel.lhToGame(0, 1).y;
	// height for enemies at floor level
	public static final int FLOOR_POS = PLGameModel.GAME_HEIGHT- LevelGenerator.FLOOR_HEIGHT;

	private final Environment environment;
	private final CreationHelper createHelper;

	private boolean running = false;

	private int generatedTerrain;
	private int generatedBackground;

	private int currentStage;
	private Stage[] stages;

	public LevelGenerator(Environment environment, CreationHelper createHelper) {
		this.createHelper = createHelper;
		this.environment = environment;
		generatedTerrain = generatedBackground = 0;
		currentStage = 5;
		stages = Stage.values();
	}

	public void setRunning(boolean running) {
		this.running = running;
	}

	@Override
	public void tick(long timestamp) {
		if (running) {
			Rectangle screenRectangle = environment.getScreenRect();
			int rightScreenBorder = screenRectangle.x + screenRectangle.width;
			if (generatedTerrain <= rightScreenBorder)
				generatedTerrain = spawnStage(generatedTerrain);
			if (generatedBackground <= rightScreenBorder)
				generatedBackground = spawnBackground(generatedBackground);
		}
	}

	/**
	 * Spawns in a new randomly selected sequence of obstacles for the player to
	 * master, and returns the time in ms that new sequence will take before a
	 * new one should be spawned.
	 *
	 * @return the time the new stage will take to run through
	 */
	public int spawnStage(int stageStart) {
		Stage stage = stages[currentStage];
		// Create first floor
		if (currentStage == 0) {
			int floorLength = PLGameModel.GAME_WIDTH + 800;
			Floor startFloor = new Floor("floor", 0, FLOOR_POS, floorLength, FLOOR_HEIGHT);
			stageStart = floorLength;
			createHelper.create(startFloor);
		}
		// Create the stage
		Collection<GameElement> c;
		int stageEnd;
		synchronized (stage) {
			c = stage.create(stageStart, environment, createHelper);
			stageEnd = stage.getLastStageEnd();
		}
		for (GameElement element : c)
			createHelper.create(element);
		int nextStage = currentStage + 1;
		if (nextStage < stages.length)
			currentStage = nextStage;
		System.out.println("Spawned stage " + stage + " from " + stageStart + " to " + stageEnd);
		return stageEnd;
	}

	private int spawnBackground(int backgroundStart) {
		Background b = new Background("bg", backgroundStart, 0, 1024, PLGameModel.GAME_HEIGHT);
		int backgroundEnd = backgroundStart + b.getWidth();
		createHelper.create(b);
		System.out.println("Spawned background from " + backgroundStart + " to " + backgroundEnd);
		return backgroundEnd;
	}

}
