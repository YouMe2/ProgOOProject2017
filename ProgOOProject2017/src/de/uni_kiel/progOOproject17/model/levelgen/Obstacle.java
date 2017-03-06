package de.uni_kiel.progOOproject17.model.levelgen;

import static de.uni_kiel.progOOproject17.model.levelgen.LevelGenerator.FLOOR_POS;
import static de.uni_kiel.progOOproject17.model.PLGameModel.*;

import de.uni_kiel.progOOproject17.model.CreationHelper;
import de.uni_kiel.progOOproject17.model.Enemy;
import de.uni_kiel.progOOproject17.model.abs.Environment;
import de.uni_kiel.progOOproject17.model.abs.GameElement;
import java.awt.Point;

public enum Obstacle {
	
	

	SINGLE(LHPIXEL_WIDTH*4, x -> {
		Enemy e = new Enemy("enemy", new Point(LHPIXEL_WIDTH*2+x, FLOOR_POS));
		e.translate(0, -e.getHeight());
		e.setGravityActive(true);
		return new GameElement[] { e };
	}), DOUBLE(LHPIXEL_WIDTH*6, x -> {
		int distance = LHPIXEL_WIDTH;
		Enemy e0 = new Enemy("enemy", new Point(LHPIXEL_WIDTH*2+x, FLOOR_POS));
		e0.translate(0, -e0.getHeight());
		e0.setGravityActive(true);
		Enemy e1 = new Enemy("enemy", new Point(LHPIXEL_WIDTH*2+x + e0.getWidth() + distance, FLOOR_POS));
		e1.translate(0, -e1.getHeight());
		e1.setGravityActive(true);
		return new GameElement[] { e0, e1 };
	}), HOVERING(LHPIXEL_WIDTH*8, x -> {
		int hoveringHeight = (int)(LHPIXEL_HEIGHT*1.5);
		Enemy e = new Enemy("enemy", new Point(LHPIXEL_WIDTH*2+x, FLOOR_POS));
		e.translate(0, -e.getHeight() - hoveringHeight);
		e.setGravityActive(false);
		return new GameElement[] { e };
	}), DOUBLE_HOVERING(LHPIXEL_WIDTH*10, x -> {
		int distance = (int)(LHPIXEL_WIDTH*1.0);
		int hoveringHeight = (int)(LHPIXEL_HEIGHT*1.5);
		Enemy e0 = new Enemy("enemy", new Point(LHPIXEL_WIDTH*2+x, FLOOR_POS));
		e0.translate(0, -e0.getHeight() - hoveringHeight);
		e0.setGravityActive(false);
		Enemy e1 = new Enemy("enemy", new Point(LHPIXEL_WIDTH*2+x + e0.getWidth() + distance, FLOOR_POS));
		e1.translate(0, -e1.getHeight() - hoveringHeight);
		e1.setGravityActive(false);
		return new GameElement[] { e0, e1 };
	}), TRIPLE_HOVERING(LHPIXEL_WIDTH*20, x -> {
		int distance = (int)(LHPIXEL_WIDTH*1.0);
		int hoveringHeight = (int)(LHPIXEL_HEIGHT*1.5);
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
	}), TWO_ON_TOP(LHPIXEL_WIDTH*16, x -> {
		int hoveringHeightUpper = (int)(LHPIXEL_HEIGHT*2);
		int hoveringHeightLower = (int)(LHPIXEL_HEIGHT*1.5);
		int spaceBefore = (int)(LHPIXEL_HEIGHT*2.5);
		Enemy e0 = new Enemy("enemy", new Point(x + spaceBefore, FLOOR_POS));
		e0.translate(0, -e0.getHeight() - hoveringHeightLower);
		e0.setGravityActive(false);
		Enemy e1 = new Enemy("enemy", new Point(x + spaceBefore, FLOOR_POS));
		e1.translate(0, -e1.getHeight() - hoveringHeightUpper);
		e1.setGravityActive(false);
		return new GameElement[] { e0, e1 };
	}), TWO_ON_TOP_HIGH(LHPIXEL_WIDTH*6, x -> {
		int hoveringHeightUpper = (int)(LHPIXEL_HEIGHT*3);
		int hoveringHeightLower = (int)(LHPIXEL_HEIGHT*1.5);
		int spaceBefore = (int)(LHPIXEL_HEIGHT*2.5);
		Enemy e0 = new Enemy("enemy", new Point(x + spaceBefore, FLOOR_POS));
		e0.translate(0, -e0.getHeight() - hoveringHeightLower);
		e0.setGravityActive(false);
		Enemy e1 = new Enemy("enemy", new Point(x + spaceBefore, FLOOR_POS));
		e1.translate(0, -e1.getHeight() - hoveringHeightUpper);
		e1.setGravityActive(false);
		return new GameElement[] { e0, e1 };
	}), CENTER(LHPIXEL_WIDTH*9, x -> {
		int spaceBefore = (int)(LHPIXEL_WIDTH*1.4);
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
