package de.uni_kiel.progOOproject17.model;

import de.uni_kiel.progOOproject17.model.abs.GameEntity;
import de.uni_kiel.progOOproject17.model.abs.GameObject;
import java.awt.Point;

public class Enemy extends GameEntity {

	public Enemy(String resKey, Point pos) {
		this(resKey, pos.x, pos.y);
	}

	public Enemy(String resKey, int x, int y) {
		super(resKey, x, y, PLGameModel.LHPIXEL_WIDTH * 2, Math.round(PLGameModel.LHPIXEL_HEIGHT * 0.9f));
		setDeadly(true);
	}

	@Override
	public void tick(long timestamp) {

		if (!isAlive()) {
			return;
		}

		// movement
		doMovement();

		// if (!getBoundingRect().intersects(new Rectangle(0, 0,
		// PLGameModel.GAME_WIDTH, PLGameModel.GAME_HEIGHT)))
		// // out of game area!!
		// destroy();

	}

	@Override
	public void onContactWith(GameObject obj) {
		assert !obj.equals(this);
		if (obj.isDeadly()) {
			destroy();
			obj.addKill();
		}

	}

}
