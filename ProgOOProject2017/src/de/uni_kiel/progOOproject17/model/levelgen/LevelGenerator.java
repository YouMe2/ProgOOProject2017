package de.uni_kiel.progOOproject17.model.levelgen;

import static de.uni_kiel.progOOproject17.model.PLGameModel.LH_HEIGHT;
import static de.uni_kiel.progOOproject17.model.PLGameModel.LH_WIDTH;
import static de.uni_kiel.progOOproject17.model.PLGameModel.lhToGame;

import de.uni_kiel.progOOproject17.model.CreationHelper;
import de.uni_kiel.progOOproject17.model.Enemy;
import de.uni_kiel.progOOproject17.model.abs.Distance;
import de.uni_kiel.progOOproject17.model.abs.Environment;
import de.uni_kiel.progOOproject17.model.abs.GameElement;
import de.uni_kiel.progOOproject17.model.abs.Ticked;
import java.util.Collection;

public class LevelGenerator implements Ticked {

	private final Distance standardVelocity = new Distance(-8, 0);
	private final Environment environment;
	private final CreationHelper creatHelp;

	private long nextSequenceTime = 0;
	private boolean running = false;

	private int currentStage;
	private Stage[] stages;

	public LevelGenerator(Environment environment, CreationHelper creatHelp) {
		this.creatHelp = creatHelp;
		this.environment = environment;
		currentStage = 0;
		stages = Stage.values();
	}

	public void setRunning(boolean running) {
		this.running = running;
	}

	@Override
	public void tick(long timestamp) {
		if (!running) {
			nextSequenceTime = 0;
			return;
		}
		if (nextSequenceTime <= timestamp)
			nextSequenceTime += spawnRandomSequence();
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
		Collection<GameElement> c = stage.create(stageStart);
		int nextStage = currentStage + 1;
		if (nextStage < stages.length)
			currentStage = nextStage;

		// overall list of obstacle collections
		// stage (random sequence of links to obstacle collections)
		// collection count within stage
		// speed depending on total collection count
		// use last sequence repeatedly

		Enemy e = new Enemy("enemy", lhToGame(LH_WIDTH, LH_HEIGHT - 2),
				environment, creatHelp);
		e.setGravityActive(false);
		e.setVelocity(standardVelocity);
		creatHelp.create(e);

		Enemy e2 = new Enemy("enemy", lhToGame(LH_WIDTH + 12, LH_HEIGHT - 3.1f),
				environment, creatHelp);
		e2.setGravityActive(false);
		e2.setVelocity(standardVelocity);
		creatHelp.create(e2);

		return 3500;
	}

}
