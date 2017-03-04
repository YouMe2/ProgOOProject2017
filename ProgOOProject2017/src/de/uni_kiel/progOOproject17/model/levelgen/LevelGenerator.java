package de.uni_kiel.progOOproject17.model.levelgen;

import de.uni_kiel.progOOproject17.model.CreationHelper;
import de.uni_kiel.progOOproject17.model.abs.Environment;
import de.uni_kiel.progOOproject17.model.abs.GameElement;
import de.uni_kiel.progOOproject17.model.abs.Ticked;
import java.awt.Rectangle;
import java.util.Collection;

public class LevelGenerator implements Ticked {

	private final Environment environment;
	private final CreationHelper createHelper;

	private boolean running = false;

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

	public void setRunning(boolean running) {
		this.running = running;
	}

	@Override
	public void tick(long timestamp) {
		if (running) {
			Rectangle screenRectangle = environment.getScreenRect();
			int rightScreenBorder = screenRectangle.x + screenRectangle.width;
			if (generatedTerrain <= rightScreenBorder)
				generatedTerrain += spawnStage(rightScreenBorder);
		}
	}

	/**
	 * Spawns in a new randomly selected sequence of obstacles for the player to
	 * master, and returns the time in ms that new sequence will take before a
	 * new one should be spawned.
	 *
	 * @return the time the new stage will take to run through
	 */
	public long spawnStage(int stageStart) {
		Stage stage = stages[currentStage];
		Collection<GameElement> c = stage.create(stageStart, environment, createHelper);
		for (GameElement element : c)
			createHelper.create(element);
		if (currentStage < stages.length - 1)
			currentStage++;
		return stage.getLastWidth();
	}

}
