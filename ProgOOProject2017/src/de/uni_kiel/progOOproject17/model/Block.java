package de.uni_kiel.progOOproject17.model;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.util.ArrayList;

public class Block extends GameEntity {

	private boolean deadly = false;
	private int killcounter = 0;
	private boolean gravity = true;

	public Block(String resKey, int x, int y, int w, int h) {
		super(resKey, x, y, w, h);

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

		Dimension distPerTick = getVelocity().getDistancePerTick();
		Dimension collDist = getCollisionDistance(OBJECTS, distPerTick);

		// collision
		// TODO nicht andersherum? Die müssen doch gleich sein
		if (!collDist.equals(distPerTick) && isDeadly()) {
			// es gabe ne collision
			ArrayList<GameObject> colls = getCollObjects(OBJECTS, distPerTick);

			for (GameObject obj : colls)
				if (obj instanceof Player) {
					Player player = (Player) obj;
					if (player.damage(1))
						addKill();

				}

		}

		// movement
		this.translate(collDist);

		if (!getBoundingRect().intersects(new Rectangle(0, 0,
				PLGameModel.GAME_WIDTH, PLGameModel.GAME_HEIGHT)))
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

}
