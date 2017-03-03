package de.uni_kiel.progOOproject17.model;

import java.awt.Point;

import de.uni_kiel.progOOproject17.model.abs.Environment;
import de.uni_kiel.progOOproject17.model.abs.GameEntity;
import de.uni_kiel.progOOproject17.model.abs.GameObject;

public class Enemy extends GameEntity {

	private boolean deadly = true;
	private int killcounter = 0;

	private boolean alive = true;

	public Enemy(String resKey, Point pos, Environment environment, CreationHelper creatHelp) {
		this(resKey, pos.x, pos.y, environment, creatHelp);
	}
	
	public Enemy(String resKey, int x, int y, Environment environment, CreationHelper creatHelp) {
		super(resKey, x, y, PLGameModel.LHPIXEL_WIDTH * 2, Math.round(PLGameModel.LHPIXEL_HEIGHT * 0.9f), environment, creatHelp);
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
