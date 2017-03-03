package de.uni_kiel.progOOproject17.model;

import static de.uni_kiel.progOOproject17.model.PLGameModel.LH_HEIGHT;
import static de.uni_kiel.progOOproject17.model.PLGameModel.LH_WIDTH;
import static de.uni_kiel.progOOproject17.model.PLGameModel.lhToGame;

import de.uni_kiel.progOOproject17.model.abs.Distance;
import de.uni_kiel.progOOproject17.model.abs.Environment;
import de.uni_kiel.progOOproject17.model.abs.Ticked;

public class LevelGenerator implements Ticked {

	private final Distance standartVelocity = new Distance(-8, 0);
	private final Environment environment;
	private final CreationHelper creatHelp;

	private long nextSequenzeTime = 0;
	private boolean running = false;

	/**
	 * 
	 */
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
			nextSequenzeTime = 0;
			return;
		}

		if (nextSequenzeTime <= timestamp) {
			nextSequenzeTime += spawnRandomSequence();
		}

	}

	/**
	 * Spawns in a new randomly selected sequence of obstacles for the player to
	 * master, And returns the time in ms that new sequence will take before a
	 * new one should be spawned.
	 * 
	 * @return the time the new sequence will take to run thru
	 */
	public long spawnRandomSequence() {

		Enemy e = new Enemy("enemy", lhToGame(LH_WIDTH, LH_HEIGHT - 2), environment, creatHelp);
		e.setGravityActive(false);
		e.setVelocity(standartVelocity);
		creatHelp.create(e);

		Enemy e2 = new Enemy("enemy", lhToGame(LH_WIDTH + 12, LH_HEIGHT - 3.1f), environment, creatHelp);
		e2.setGravityActive(false);
		e2.setVelocity(standartVelocity);
		creatHelp.create(e2);

		return 3500;
	}

}
