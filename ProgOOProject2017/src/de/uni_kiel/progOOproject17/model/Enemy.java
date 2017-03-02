package de.uni_kiel.progOOproject17.model;

import java.awt.Rectangle;

public class Enemy extends GameEntity {

	private boolean deadly = true;
	private int killcounter = 0;

	private boolean alive = true;

	public Enemy(String resKey, int x, int y, Environment environment) {
		super(resKey, x, y, PLGameModel.LHPIXEL_WIDTH * 2, PLGameModel.LHPIXEL_HEIGHT, environment);

		// TODO ENEMY VELOCITY
		setVelocity(-5, 0); // gehen immer nach links
	}

	@Override
	public boolean isAlive() {
		return alive;
	}

	@Override
	public void tick(long timestamp) {

		if (!isAlive())
			return;

		// gravity ??

		applyGravity();

		Distance collDist = environment.getCollisionDistance(this, getVelocity());

		// movement
		doMovement(collDist);

		if (!getBoundingRect().intersects(new Rectangle(0, 0, PLGameModel.GAME_WIDTH, PLGameModel.GAME_HEIGHT)))
			// out of game area!!
			destroy();

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

		if (obj.isDeadly()) {
			destroy();
			obj.addKill();
		}

	}

}
