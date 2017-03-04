package de.uni_kiel.progOOproject17.model.levelgen;

import de.uni_kiel.progOOproject17.model.CreationHelper;
import de.uni_kiel.progOOproject17.model.Enemy;
import de.uni_kiel.progOOproject17.model.PLGameModel;
import de.uni_kiel.progOOproject17.model.abs.Environment;
import de.uni_kiel.progOOproject17.model.abs.GameElement;
import java.awt.Point;

public enum Obstacle {

	SINGLE(30, x -> {
		Enemy e = new Enemy("enemy", new Point(x,
				PLGameModel.GAME_HEIGHT - LevelGenerator.FLOOR_HEIGHT));
		e.translate(0, -e.getHeight());
		e.setGravityActive(true);
		return new GameElement[] { e };
	}),
	DOUBLE(70, x -> {
		int distance = 10;
		Enemy e1 = new Enemy("enemy", new Point(x,
				PLGameModel.GAME_HEIGHT - LevelGenerator.FLOOR_HEIGHT));
		e1.translate(0, -e1.getHeight());
		e1.setGravityActive(true);
		Enemy e2 = new Enemy("enemy", new Point(x + e1.getWidth() + distance,
				PLGameModel.GAME_HEIGHT - LevelGenerator.FLOOR_HEIGHT));
		e2.translate(0, -e2.getHeight());
		e2.setGravityActive(true);
		return new GameElement[] { e1, e2 };
	}),
	// TRIPLE(95, x -> {
	// int distance = 10;
	// Enemy e1 = new Enemy("enemy", new Point(x,
	// PLGameModel.GAME_HEIGHT - LevelGenerator.FLOOR_HEIGHT));
	// e1.translate(0, -e1.getHeight());
	// e1.setGravityActive(true);
	// Enemy e2 = new Enemy("enemy", new Point(x + e1.getWidth() + distance,
	// PLGameModel.GAME_HEIGHT - LevelGenerator.FLOOR_HEIGHT));
	// e2.translate(0, -e2.getHeight());
	// e2.setGravityActive(true);
	// Enemy e3 = new Enemy("enemy",
	// new Point(e2.getX() + e2.getWidth() + distance,
	// PLGameModel.GAME_HEIGHT - LevelGenerator.FLOOR_HEIGHT));
	// e2.translate(0, -e2.getHeight());
	// e2.setGravityActive(true);
	// return new GameElement[] { e1, e2, e3 };
	// }),
	HOVERING(30, x -> {
		int hoveringHeight = 50;
		Enemy e = new Enemy("enemy", new Point(x,
				PLGameModel.GAME_HEIGHT - LevelGenerator.FLOOR_HEIGHT));
		e.translate(0, -e.getHeight() - hoveringHeight);
		e.setGravityActive(false);
		return new GameElement[] { e };
	}),
	DOUBLE_HOVERING(75, x -> {
		int distance = 15;
		int hoveringHeight = 50;
		Enemy e1 = new Enemy("enemy", new Point(x,
				PLGameModel.GAME_HEIGHT - LevelGenerator.FLOOR_HEIGHT));
		e1.translate(0, -e1.getHeight() - hoveringHeight);
		e1.setGravityActive(false);
		Enemy e2 = new Enemy("enemy", new Point(x + e1.getWidth() + distance,
				PLGameModel.GAME_HEIGHT - LevelGenerator.FLOOR_HEIGHT));
		e2.translate(0, -e2.getHeight() - hoveringHeight);
		e2.setGravityActive(false);
		return new GameElement[] { e1, e2 };
	}),
	TRIPLE_HOVERING(250, x -> {
		int distance = 15;
		int hoveringHeight = 50;
		Enemy e1 = new Enemy("enemy", new Point(x,
				PLGameModel.GAME_HEIGHT - LevelGenerator.FLOOR_HEIGHT));
		e1.translate(0, -e1.getHeight() - hoveringHeight);
		e1.setGravityActive(false);
		Enemy e2 = new Enemy("enemy", new Point(x + e1.getWidth() + distance,
				PLGameModel.GAME_HEIGHT - LevelGenerator.FLOOR_HEIGHT));
		e2.translate(0, -e2.getHeight() - hoveringHeight);
		e2.setGravityActive(false);
		Enemy e3 = new Enemy("enemy",
				new Point(e2.getX() + e2.getWidth() + distance,
						PLGameModel.GAME_HEIGHT - LevelGenerator.FLOOR_HEIGHT));
		e3.translate(0, -e2.getHeight() - hoveringHeight);
		e3.setGravityActive(false);
		return new GameElement[] { e1, e2, e3 };
	});

	private int width;
	private final ObstacleCreator creator;

	private Obstacle(int width, ObstacleCreator c) {
		this.width = width;
		creator = c;
	}

	public GameElement[] createNew(int obstacleStart, Environment e,
			CreationHelper c) {
		return creator.createNew(obstacleStart);
	}

	public int getWidth() {
		return width;
	}
}
