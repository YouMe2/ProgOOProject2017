package de.uni_kiel.progOOproject17.model.levelgen;

import de.uni_kiel.progOOproject17.model.CreationHelper;
import de.uni_kiel.progOOproject17.model.abs.Environment;
import de.uni_kiel.progOOproject17.model.abs.GameElement;
import de.uni_kiel.progOOproject17.model.abs.Ticked;
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
			// Rectangle screenRectangle = environment.getScreenRectangle();
			int rightScreenBorder = environment.getScreenPosition().x + 200; // screenRectangle.x
																				// +
																				// screenRectangle.width;
			if (generatedTerrain <= rightScreenBorder)
				generatedTerrain += spawnRandomSequence();
		}
	}

	/**
	 * Spawns in a new randomly selected sequence of obstacles for the player to
	 * master, And returns the time in ms that new sequence will take before a
	 * new one should be spawned.
	 *
	 * @return the time the new sequence will take to run through
	 */
	public long spawnRandomSequence() {
		int stageStart = environment.getScreenPosition().x;
		Stage stage = stages[currentStage];
		Collection<GameElement> c = stage.create(stageStart, environment, createHelper);
		for (GameElement element : c)
			createHelper.create(element);
		int nextStage = currentStage + 1;
		if (nextStage < stages.length)
			currentStage = nextStage;

		// overall list of obstacle collections
		// stage (random sequence of links to obstacle collections)
		// collection count within stage
		// speed depending on total collection count
		// use last sequence repeatedly

		return 3500;
	}

}
