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

	private final Environment environment;
	private final CreationHelper createHelper;


	private int generatedTerrain;

	private int currentStage;
	private Stage[] stages;

	public LevelGenerator(Environment environment, CreationHelper createHelper) {
		this.createHelper = createHelper;
		this.environment = environment;
		generatedTerrain = 0;
		currentStage = 0;
		stages = Stage.values();
	}

	@Override
	public void tick(long timestamp) {
		
			Rectangle screenRectangle = environment.getScreenRect();
			int rightScreenBorder = (int) screenRectangle.getMaxX();
			if (generatedTerrain <= rightScreenBorder)
				generatedTerrain = spawnStage(rightScreenBorder);
		
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
			int space = PLGameModel.GAME_WIDTH * 2;
			Floor startFloor = new Floor("floor", 0, PLGameModel.GAME_HEIGHT - FLOOR_HEIGHT, stageStart + space,
					FLOOR_HEIGHT);
			createHelper.create(startFloor);
			
			int covered = 0;
			while (covered < space) {
				Background b = new Background("bg", 0, 0, 1024, PLGameModel.GAME_HEIGHT);
				covered += b.getWidth();
				createHelper.create(b);;
			}
			
			stageStart += space;
			
			
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
		if (currentStage < stages.length - 1)
			currentStage++;
		System.out.println("Spawned stage " + stage + " from " + stageStart + " to " + stageEnd);
		return stageEnd;
	}

}
