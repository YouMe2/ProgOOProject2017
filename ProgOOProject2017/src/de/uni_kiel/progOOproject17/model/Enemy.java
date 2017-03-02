package de.uni_kiel.progOOproject17.model;

import java.awt.Point;
import java.awt.Rectangle;

import de.uni_kiel.progOOproject17.model.abs.DestroyListener;
import de.uni_kiel.progOOproject17.model.abs.Environment;
import de.uni_kiel.progOOproject17.model.abs.GameEntity;
import de.uni_kiel.progOOproject17.model.abs.GameObject;

public class Enemy extends GameEntity {

	private boolean deadly = true;
	private int killcounter = 0;

	private boolean alive = true;

	public Enemy(String resKey, Point pos, Environment environment, DestroyListener destroyListener) {
		this(resKey, pos.x, pos.y, environment, destroyListener);
	}
	
	public Enemy(String resKey, int x, int y, Environment environment, DestroyListener destroyListener) {
		super(resKey, x, y, PLGameModel.LHPIXEL_WIDTH * 2, PLGameModel.LHPIXEL_HEIGHT, environment, destroyListener);
	}

	@Override
	public boolean isAlive() {
		return alive;
	}

	@Override
	public void tick(long timestamp) {

		if (!isAlive()) {
			System.out.println("NON ALIVE ENTITY TICKED!");
			return;
		}

		// movement
		doMovement();

//		if (!getBoundingRect().intersects(new Rectangle(0, 0, PLGameModel.GAME_WIDTH, PLGameModel.GAME_HEIGHT)))
//			// out of game area!!
//			destroy();

	}

	@Override
	public boolean isDeadly() {
		return deadly;
	}

	@Override
	public void addKill() {
		killcounter++;

	}

	public void setDeadly(boolean deadly) {
		this.deadly = deadly;
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
