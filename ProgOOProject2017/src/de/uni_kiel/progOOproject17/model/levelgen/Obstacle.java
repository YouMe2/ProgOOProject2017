package de.uni_kiel.progOOproject17.model.levelgen;

import de.uni_kiel.progOOproject17.model.CreationHelper;
import de.uni_kiel.progOOproject17.model.Enemy;
import de.uni_kiel.progOOproject17.model.abs.Distance;
import de.uni_kiel.progOOproject17.model.abs.Environment;
import de.uni_kiel.progOOproject17.model.abs.GameElement;
import java.awt.Point;

public enum Obstacle {

	SINGLE(100, (x, e, c) -> {
		// System.out.println("single obstacle for " + x);
		Distance standardVelocity = new Distance(-5, 0);

		Enemy e1 = new Enemy("enemy", new Point(x, 200), e, c);
		e1.setVelocity(standardVelocity);

		// Enemy e2 = new Enemy("enemy", new Point(300, 300), e, c);
		// e2.setVelocity(standardVelocity);
		//
		// Block b = new Block("floor", new Point(300, 300), e, c);

		return new GameElement[] { e1/* , e2, b */ };
	}), DOUBLE(180, (x, e, c) -> {
		// System.out.println("double obstacle for " + x);
		return new GameElement[] {};
	}), TRIPLE(250, (x, e, c) -> {
		// System.out.println("triple obstacle for " + x);
		return new GameElement[] {};
	}), HOVERING(100, (x, e, c) -> {
		// System.out.println("hovering obstacle for " + x);
		return new GameElement[] {};
	}), DOUBLE_HOVERING(180, (x, e, c) -> {
		// System.out.println("double hovering obstacle for " + x);
		return new GameElement[] {};
	}), TRIPLE_HOVERING(250, (x, e, c) -> {
		// System.out.println("triple hovering obstacle for " + x);
		return new GameElement[] {};
	});

	private int width;
	private final ObstacleCreator creator;

	private Obstacle(int width, ObstacleCreator c) {
		this.width = width;
		creator = c;
	}

	public GameElement[] createNew(int obstacleStart, Environment e, CreationHelper c) {
		System.out.println("obs " + width + " c!");
		return creator.createNew(obstacleStart, e, c);
	}

	public int getWidth() {
		return width;
	}
}
