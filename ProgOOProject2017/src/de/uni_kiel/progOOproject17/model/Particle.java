package de.uni_kiel.progOOproject17.model;

import de.uni_kiel.progOOproject17.view.abs.Viewable;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.util.ArrayList;

public class Particle extends GameComponent implements Destroyable, Viewable{

	public static final ArrayList<Particle> PARTICLES = new ArrayList<>();

	private int counter = 0;
	private final int dtime;
	private long lasttime = 0;
	private final int max;

	private boolean alive = true;

	private String resKey;

	public Particle(String resKey, int x, int y, int w, int h, int dtime, int max) {
		super(x, y, w, h);
		PARTICLES.add(this);
		this.max = max;
		this.resKey = resKey;

		this.dtime = dtime;
	}

	@Override
	public String getResourceKey() {
		return Particle.this.resKey + "_" + counter;
	}

	@Override
	public Rectangle getViewRect() {
		return Particle.this.getBoundingRect();
	}

	@Override
	public int getLayer() {
		return Viewable.PARTICLE_LAYER;
	}
	
	@Override
	public void tick(long timestamp) {
		// init
		if (lasttime == 0)
			lasttime = timestamp;

		// next
		if (timestamp - lasttime > dtime && counter < max - 1) {
			lasttime = timestamp;
			counter++;

		}
		// end
		else if (counter == max - 1)
			alive = false;

	}


	@Override
	public boolean isAlive() {
		return alive;
	}

	@Override
	public void destroy() {
		alive = false;
		PARTICLES.remove(this);
		COMPONENTS.remove(this);
	}

	@Override
	public void activate() {
		// TODO Auto-generated method stub
		
	}

}
