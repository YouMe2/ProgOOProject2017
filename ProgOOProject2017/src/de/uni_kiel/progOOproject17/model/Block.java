package de.uni_kiel.progOOproject17.model;

import de.uni_kiel.progOOproject17.model.abs.GameEntity;
import de.uni_kiel.progOOproject17.model.abs.GameObject;
import java.awt.Rectangle;

public class Block extends GameEntity {

	public Block(String resKey, Rectangle rect) {
		this(resKey, rect.x, rect.y, rect.width, rect.height);
	}

	public Block(String resKey, int x, int y, int w, int h) {
		super(resKey, x, y, w, h);
		setGravityActive(false);
	}

	@Override
	public void tick(long timestamp) {

		if (!isAlive()) {
			System.out.println("NON ALIVE ENTITY TICKED!");
			return;
		}

		// movement
		doMovement();

		// if (!getBoundingRect().intersects(new Rectangle(0, 0,
		// PLGameModel.GAME_WIDTH, PLGameModel.GAME_HEIGHT)))
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
