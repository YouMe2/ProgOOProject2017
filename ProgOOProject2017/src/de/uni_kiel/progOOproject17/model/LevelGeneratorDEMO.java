package de.uni_kiel.progOOproject17.model;

import static de.uni_kiel.progOOproject17.model.PLGameModel.*;

import de.uni_kiel.progOOproject17.model.abs.Distance;
import de.uni_kiel.progOOproject17.model.abs.Environment;
import de.uni_kiel.progOOproject17.model.abs.Ticked;

public class LevelGeneratorDEMO implements Ticked {

	private final Distance standartVelocity = new Distance(-8, 0);
	
	private final Environment environment;
	private final CreationHelper creatHelp;
	
	private long nextSequenzeTime = 0;
	private boolean running = false;

	/**
	 * 
	 */
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

		Enemy e = new Enemy("enemy", environment.getScreenRect().x + GAME_WIDTH, (LH_HEIGHT - 2)*LHPIXEL_HEIGHT);
		e.setGravityActive(false);
//		e.setVelocity(standartVelocity);
		creatHelp.create(e);

		Enemy e2 = new Enemy("enemy", environment.getScreenRect().x + GAME_WIDTH+150, (LH_HEIGHT - 3)*LHPIXEL_HEIGHT);
		e2.setGravityActive(false);
//		e2.setVelocity(standartVelocity);
		creatHelp.create(e2);

		Block b = new Block("floor", environment.getScreenRect().x + GAME_WIDTH +400, (LH_HEIGHT - 4)*LHPIXEL_HEIGHT, 12*LHPIXEL_WIDTH, LHPIXEL_HEIGHT);
		b.setGravityActive(false);
//		b.setVelocity(standartVelocity);
//		b.setContinuesVel(standartVelocity); //TODO wie ists besser?
		creatHelp.create(b);

		return 3600;
	}

}
