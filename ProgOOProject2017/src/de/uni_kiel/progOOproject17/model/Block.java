package de.uni_kiel.progOOproject17.model;

import java.awt.Rectangle;

public class Block extends GameEntity {

	private boolean deadly = false;
	private int killcounter = 0;
	private boolean gravity = true;

	public Block(String resKey, int x, int y, int w, int h, Environment environment) {
		super(resKey, x, y, w, h, environment);
	}

	public void setGravityActive(boolean gravity) {
		this.gravity = gravity;
	}

	@Override
	public void tick(long timestamp) {

		if (!isAlive())
			return;

		// TODO BLOCK gravity ??
		if (gravity)
			applyGravity();

		Distance collDist = environment.getCollisionDistance(this, getVelocity());

		// movement
		doMovement(collDist);

		if (!getBoundingRect().intersects(new Rectangle(0, 0, PLGameModel.GAME_WIDTH, PLGameModel.GAME_HEIGHT)))
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
