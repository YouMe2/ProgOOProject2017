package de.uni_kiel.progOOproject17.model.levelgen;

import static de.uni_kiel.progOOproject17.model.levelgen.LevelGenerator.FLOOR_POS;

import de.uni_kiel.progOOproject17.model.Enemy;
import de.uni_kiel.progOOproject17.model.abs.GameElement;
import java.awt.Point;

/**
 * An obstacle is a list of <code>GameElement</code>s. To specify an obstacle,
 * it needs to know how much space it's going to take up and how to create the
 * list of <code>GameElement</code>s. For the latter, an {@link ObstacleCreator}
 * must be passed that defines how to instantiate the elements. This creator
 * will be used later to create the obstacle.
 *
 */
public enum Obstacle {

	SINGLE(30, x -> {
		// creates a single enemy on floor level
		Enemy e = new Enemy("enemy", new Point(x, FLOOR_POS));
		e.translate(0, -e.getHeight());
		e.setGravityActive(true);
		return new GameElement[] { e };
	}), DOUBLE(70, x -> {
		// creates two enemies next to each other on floor level
		int distance = 10;
		Enemy e0 = new Enemy("enemy", new Point(x, FLOOR_POS));
		e0.translate(0, -e0.getHeight());
		e0.setGravityActive(true);
		Enemy e1 = new Enemy("enemy", new Point(x + e0.getWidth() + distance, FLOOR_POS));
		e1.translate(0, -e1.getHeight());
		e1.setGravityActive(true);
		return new GameElement[] { e0, e1 };
	}), HOVERING(30, x -> {
		// creates a single enemy hovering above the floor
		int hoveringHeight = 50;
		Enemy e = new Enemy("enemy", new Point(x, FLOOR_POS));
		e.translate(0, -e.getHeight() - hoveringHeight);
		e.setGravityActive(false);
		return new GameElement[] { e };
	}), DOUBLE_HOVERING(75, x -> {
		// creates two enemies next to each other hovering above the floor
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
		// creates three enemies next to each other hovering above the floor
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
		// Creates two enemies on top of each other, one on the floor, the other
		// hovering above. The player has to crouch in the air to master this
		// obstacle safely.
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
		// Creates two hovering enemies on top of each other. This obstacle can
		// easily be confused with the one before, TWO_ON_TOP.
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
		// Creates an enemy hovering a little bit above the ground so the player
		// has to decide quickly whether to crouch or to jump.
		int spaceBefore = 20;
		int hoveringHeight = 25;
		Enemy e = new Enemy("enemy", new Point(x + spaceBefore, FLOOR_POS));
		e.translate(0, -e.getHeight() - hoveringHeight);
		e.setGravityActive(false);
		return new GameElement[] { e };
	});

	/**
	 * The width of the obstacle.
	 */
	private int width;
	/**
	 * The <code>ObstacleCreator</code> used to define how to instantiate a new
	 * obstacle.
	 */
	private final ObstacleCreator creator;

	/**
	 * Creates a new obstacle with the given width and obstacle creator.
	 *
	 * @param width
	 *            the width of the obstacle
	 * @param c
	 *            the obstacle creator used to define how to instantiate a new
	 *            obstacle
	 */
	private Obstacle(int width, ObstacleCreator c) {
		this.width = width;
		creator = c;
	}

	/**
	 * Creates a new instance of this obstacle, represented as a list of
	 * <code>GameElement</code>s.
	 *
	 * @param obstacleStart
	 *            the starting position used to create the obstacle
	 * @return the obstacle as a list of <code>GameElement</code>s
	 */
	public GameElement[] createNew(int obstacleStart) {
		return creator.createNew(obstacleStart);
	}

	/**
	 * Gets the width of this obstacle.
	 *
	 * @return the width of this obstacle
	 */
	public int getWidth() {
		return width;
	}
}
