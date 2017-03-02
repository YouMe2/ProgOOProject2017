package de.uni_kiel.progOOproject17.model;

import de.uni_kiel.progOOproject17.view.abs.Viewable;
import java.awt.Rectangle;

public abstract class GameObject extends GameComponent implements Destroyable, Deadly, Viewable {

	private boolean alive = true;

	private String resKey;
	private int layer;

	public final Environment environment;

	public GameObject(String resKey, int x, int y, int w, int h, Environment environment) {
		super(x, y, w, h);
		this.environment = environment;
		setResKey(resKey);
		setLayer(0);
	}

	@Override
	public String getResourceKey() {
		return resKey;
	}

	@Override
	public Rectangle getViewRect() {
		return getBoundingRect();
	}

	@Override
	public int getLayer() {
		return layer;
	}

	public void setResKey(String resKey) {
		this.resKey = resKey;
	}

	public void setLayer(int layer) {
		this.layer = layer;
	}

	@Override
	public void activate() {
		alive = true;
	}

	@Override
	public void destroy() {
		if (isAlive()) {
			System.out.println("DESTROYED: " + resKey);
			alive = false;
		}
	}

	@Override
	public boolean isAlive() {
		return alive;
	}
}
