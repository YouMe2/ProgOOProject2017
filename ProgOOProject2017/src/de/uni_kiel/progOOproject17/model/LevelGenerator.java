package de.uni_kiel.progOOproject17.model;

import de.uni_kiel.progOOproject17.model.abs.GameObjectCreator;
import de.uni_kiel.progOOproject17.model.abs.Ticked;

public class LevelGenerator implements Ticked {

	private GameObjectCreator creator;

	/**
	 * 
	 */
	public LevelGenerator(GameObjectCreator creator) {
		this.creator = creator;
	}

	@Override
	public void tick(long timestamp) {
	}

}
