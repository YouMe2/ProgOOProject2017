package de.uni_kiel.progOOproject17.model;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.util.ArrayList;

public class Enemy extends GameEntity {

	private boolean deadly = true;
	private int killcounter = 0;

	private boolean alive = true;

	public Enemy(String resKey, int x, int y) {
		super(resKey, x, y, PLGameModel.LHPIXEL_WIDTH * 2,
				PLGameModel.LHPIXEL_HEIGHT);

		// TODO ENEMY VELOCITY
		setVelocity(-5, 0); // gehen immer nach rechts

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

		Dimension dis = getCollisionDistance(OBJECTS, getVelocity().speedX,
				getVelocity().speedY);

		// collision
		if (!dis.equals(getVelocity()) && isDeadly()) {
			// es gabe ne collision
			ArrayList<GameObject> colls = getCollObjects(OBJECTS,
					getVelocity().speedX, getVelocity().speedY);

			for (GameObject obj : colls)
				if (obj instanceof Player) {
					Player player = (Player) obj;
					if (player.damage(1))
						addKill();

				}

		}

		// movement
		this.translate(dis);

		if (!getBoundingRect().intersects(new Rectangle(0, 0,
				PLGameModel.GAME_WIDTH, PLGameModel.GAME_HEIGHT)))
			// out of game area!!
			alive = false;

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

}
