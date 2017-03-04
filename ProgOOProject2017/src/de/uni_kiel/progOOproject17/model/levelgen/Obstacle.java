package de.uni_kiel.progOOproject17.model.levelgen;

import de.uni_kiel.progOOproject17.model.CreationHelper;
import de.uni_kiel.progOOproject17.model.Enemy;
import de.uni_kiel.progOOproject17.model.abs.Distance;
import de.uni_kiel.progOOproject17.model.abs.Environment;
import de.uni_kiel.progOOproject17.model.abs.GameElement;
import java.awt.Point;

public enum Obstacle {

	SINGLE(100, x -> {
		Distance velocity = new Distance(-5, 0);
		Enemy e1 = new Enemy("enemy", new Point(x, 200));
		e1.setGravityActive(false);
		e1.setVelocity(velocity);
		return new GameElement[] { e1/* , e2, b */ };
	}), DOUBLE(180, x -> {
		return new GameElement[] {};
	}), TRIPLE(250, x -> {
		return new GameElement[] {};
	}), HOVERING(100, x -> {
		return new GameElement[] {};
	}), DOUBLE_HOVERING(180, x -> {
		return new GameElement[] {};
	}), TRIPLE_HOVERING(250, x -> {
		return new GameElement[] {};
	});

	private int width;
	private final ObstacleCreator creator;

	private Obstacle(int width, ObstacleCreator c) {
		this.width = width;
		creator = c;
	}

	public GameElement[] createNew(int obstacleStart, Environment e, CreationHelper c) {
		return creator.createNew(obstacleStart);
	}

	public int getWidth() {
		return width;
	}
}
