package de.uni_kiel.progOOproject17.model;

import static de.uni_kiel.progOOproject17.model.PLGameModel.LH_HEIGHT;
import static de.uni_kiel.progOOproject17.model.PLGameModel.LH_WIDTH;
import static de.uni_kiel.progOOproject17.model.PLGameModel.lhToGame;

import de.uni_kiel.progOOproject17.model.abs.Distance;
import de.uni_kiel.progOOproject17.model.abs.Environment;
import de.uni_kiel.progOOproject17.model.abs.GameEntity;
import de.uni_kiel.progOOproject17.model.abs.Ticked;
import java.util.Arrays;

public class LevelGenerator implements Ticked {

	private final Distance standardVelocity = new Distance(-8, 0);
	private final Environment environment;
	private final CreationHelper creatHelp;

	private long nextSequenceTime = 0;
	private boolean running = false;

	public LevelGenerator(Environment environment, CreationHelper creatHelp) {
		this.creatHelp = creatHelp;
		this.environment = environment;
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
	 * @return the time the new sequence will take to run thru
	 */
	public long spawnRandomSequence() {

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

	private enum Obstacle {

		SPACE();
		// SINGLE(createEnemies(false)),
		// DOUBLE(createEnemies(false)),
		// TRIPLE(createEnemies(false)),
		// HOVERING(createEnemies(false)),
		// DOUBLE_HOVERING(createEnemies(false)),
		// TRIPLE_HOVERING(createEnemies(false)),
		// TOP_DOWN_TOP(createEnemies(false)),
		// DOWN_TOP_DOWN(createEnemies(false));
		// etc

		private final GameEntity[] entities;

		private Obstacle(GameEntity... entities) {
			this.entities = Arrays.copyOf(entities, entities.length);
		}

	}

}
