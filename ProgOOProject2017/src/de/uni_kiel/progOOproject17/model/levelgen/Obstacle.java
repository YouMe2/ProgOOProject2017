package de.uni_kiel.progOOproject17.model.levelgen;

import static de.uni_kiel.progOOproject17.model.levelgen.LevelGenerator.FLOOR_POS;

import de.uni_kiel.progOOproject17.model.CreationHelper;
import de.uni_kiel.progOOproject17.model.Enemy;
import de.uni_kiel.progOOproject17.model.abs.Environment;
import de.uni_kiel.progOOproject17.model.abs.GameElement;
import java.awt.Point;

public enum Obstacle {

	SINGLE(30, x -> {
		Enemy e = new Enemy("enemy", new Point(x, FLOOR_POS));
		e.translate(0, -e.getHeight());
		e.setGravityActive(true);
		return new GameElement[] { e };
	}), DOUBLE(70, x -> {
		int distance = 10;
		Enemy e0 = new Enemy("enemy", new Point(x, FLOOR_POS));
		e0.translate(0, -e0.getHeight());
		e0.setGravityActive(true);
		Enemy e1 = new Enemy("enemy", new Point(x + e0.getWidth() + distance, FLOOR_POS));
		e1.translate(0, -e1.getHeight());
		e1.setGravityActive(true);
		return new GameElement[] { e0, e1 };
	}), HOVERING(30, x -> {
		int hoveringHeight = 50;
		Enemy e = new Enemy("enemy", new Point(x, FLOOR_POS));
		e.translate(0, -e.getHeight() - hoveringHeight);
		e.setGravityActive(false);
		return new GameElement[] { e };
	}), DOUBLE_HOVERING(75, x -> {
		int distance = 15;
		int hoveringHeight = 50;
		Enemy e0 = new Enemy("enemy", new Point(x, FLOOR_POS));
		e0.translate(0, -e0.getHeight() - hoveringHeight);
		e0.setGravityActive(false);
		Enemy e1 = new Enemy("enemy", new Point(x + e0.getWidth() + distance, FLOOR_POS));
		e1.translate(0, -e1.getHeight() - hoveringHeight);
		e1.setGravityActive(false);
		return new GameElement[] { e0, e1 };
	}), TRIPLE_HOVERING(250, x -> {
		int distance = 15;
		int hoveringHeight = 50;
		Enemy e0 = new Enemy("enemy", new Point(x, FLOOR_POS));
		e0.translate(0, -e0.getHeight() - hoveringHeight);
		e0.setGravityActive(false);
		Enemy e1 = new Enemy("enemy", new Point(x + e0.getWidth() + distance, FLOOR_POS));
		e1.translate(0, -e1.getHeight() - hoveringHeight);
		e1.setGravityActive(false);
		Enemy e2 = new Enemy("enemy", new Point(e1.getX() + e1.getWidth() + distance, FLOOR_POS));
		e2.translate(0, -e1.getHeight() - hoveringHeight);
		e2.setGravityActive(false);
		return new GameElement[] { e0, e1, e2 };
	}), TWO_ON_TOP(80, x -> {
		int hoveringHeightUpper = 70;
		int hoveringHeightLower = 10;
		int spaceBefore = 50;
		Enemy e0 = new Enemy("enemy", new Point(x + spaceBefore, FLOOR_POS));
		e0.translate(0, -e0.getHeight() - hoveringHeightLower);
		e0.setGravityActive(false);
		Enemy e1 = new Enemy("enemy", new Point(x + spaceBefore, FLOOR_POS));
		e1.translate(0, -e1.getHeight() - hoveringHeightUpper);
		e1.setGravityActive(false);
		return new GameElement[] { e0, e1 };
	}), TWO_ON_TOP_HIGH(30, x -> {
		int hoveringHeightUpper = 100;
		int hoveringHeightLower = 50;
		int spaceBefore = 50;
		Enemy e0 = new Enemy("enemy", new Point(x + spaceBefore, FLOOR_POS));
		e0.translate(0, -e0.getHeight() - hoveringHeightLower);
		e0.setGravityActive(false);
		Enemy e1 = new Enemy("enemy", new Point(x + spaceBefore, FLOOR_POS));
		e1.translate(0, -e1.getHeight() - hoveringHeightUpper);
		e1.setGravityActive(false);
		return new GameElement[] { e0, e1 };
	}), CENTER(70, x -> {
		int spaceBefore = 20;
		Enemy e = new Enemy("enemy", new Point(x + spaceBefore, FLOOR_POS));
		e.translate(0, -e.getHeight());
		e.setGravityActive(true);
		return new GameElement[] { e };
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
