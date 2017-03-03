package de.uni_kiel.progOOproject17.model;

import static de.uni_kiel.progOOproject17.model.PLGameModel.LH_HEIGHT;
import static de.uni_kiel.progOOproject17.model.PLGameModel.LH_WIDTH;
import static de.uni_kiel.progOOproject17.model.PLGameModel.lhToGam;

import de.uni_kiel.progOOproject17.model.abs.Distance;
import de.uni_kiel.progOOproject17.model.abs.Environment;
import de.uni_kiel.progOOproject17.model.abs.Ticked;

public class LevelGeneratorDEMO implements Ticked {

	private final Distance standardVelocity = new Distance(-8, 0);

	private final Environment environment;
	private final CreationHelper creatHelp;

	private long nextSequenceTime = 0;
	private boolean running = false;

	public LevelGeneratorDEMO(Environment environment, CreationHelper creatHelp) {
		this.environment = environment;
		this.creatHelp = creatHelp;
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

		Enemy e = new Enemy("enemy", PLGameModel.lhToGame(LH_WIDTH, LH_HEIGHT - 2), environment, creatHelp);
		e.setGravityActive(false);
		e.setVelocity(standardVelocity);
		creatHelp.create(e);

		Enemy e2 = new Enemy("enemy", PLGameModel.lhToGame(LH_WIDTH + 12, LH_HEIGHT - 3), environment, creatHelp);
		e2.setGravityActive(false);
		e2.setVelocity(standardVelocity);
		creatHelp.create(e2);

		Block b = new Block("floor", lhToGam(LH_WIDTH + 26, LH_HEIGHT - 4, 12, 1), environment, creatHelp);
		b.setGravityActive(false);
		// b.setVelocity(standartVelocity);
		b.setContinuesVel(standardVelocity); // TODO wie ists besser?
		creatHelp.create(b);

		return 3600;
	}

}
