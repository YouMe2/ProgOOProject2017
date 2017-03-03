package de.uni_kiel.progOOproject17.model;

import java.awt.Rectangle;

import de.uni_kiel.progOOproject17.model.abs.Distance;
import de.uni_kiel.progOOproject17.model.abs.Environment;
import de.uni_kiel.progOOproject17.model.abs.GameEntity;
import de.uni_kiel.progOOproject17.model.abs.GameObject;

public class Block extends GameEntity {

	private boolean deadly = false;
	private int killcounter = 0;
	
	private Distance continuesVel = null;

	public Block(String resKey,Rectangle rect, Environment environment, CreationHelper creatHelp) {
		this(resKey, rect.x, rect.y, rect.width, rect.height, environment, creatHelp);
	}
	
	public Block(String resKey, int x, int y, int w, int h, Environment environment, CreationHelper creatHelp) {
		super(resKey, x, y, w, h, environment, creatHelp);
		setGravityActive(false);
	}
	
	

	@Override
	public void tick(long timestamp) {

		if (!isAlive()) {
			System.out.println("NON ALIVE ENTITY TICKED!");
			return;
		}

		if(continuesVel != null){
			setVelocity(continuesVel);
		}
		
		
		// movement
		doMovement();

//		if (!getBoundingRect().intersects(new Rectangle(0, 0, PLGameModel.GAME_WIDTH, PLGameModel.GAME_HEIGHT)))
//			destroy();

	}
	
	
	public void setContinuesVel(Distance vel) {
		this.continuesVel = vel;
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
