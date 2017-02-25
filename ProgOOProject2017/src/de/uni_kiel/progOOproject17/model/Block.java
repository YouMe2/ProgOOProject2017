package de.uni_kiel.progOOproject17.model;

import de.uni_kiel.progOOproject17.view.abs.Viewable;

public class Block extends GameEntity implements Deadly{


	private boolean deadly = false;
	private int killcounter;
	
	public Block(int x, int y, int w, int h) {
		super(x, y, w, h);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean isAlive() {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public void tick(long timestamp) {
		// TODO Auto-generated method stub

	}

	@Override
	public Viewable getViewable() {
		// TODO Auto-generated method stub
		return null;
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
