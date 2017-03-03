package de.uni_kiel.progOOproject17.model.levelgen;

import de.uni_kiel.progOOproject17.model.abs.GameElement;

public enum Obstacle {

	SINGLE(100, x -> {
		return new GameElement[] {};
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
	private final ObstacleCreator c;

	private Obstacle(int width, ObstacleCreator c) {
		this.width = width;
		this.c = c;
	}

	public GameElement[] createNew(int obstacleStart) {
		return c.createNew(obstacleStart);
	}

	public int getWidth() {
		return width;
	}
}
