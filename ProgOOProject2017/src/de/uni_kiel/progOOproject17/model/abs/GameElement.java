package de.uni_kiel.progOOproject17.model.abs;

import de.uni_kiel.progOOproject17.model.CreationHelper;
import de.uni_kiel.progOOproject17.view.abs.Viewable;
import java.awt.Point;
import java.awt.Rectangle;

public abstract class GameElement extends GameComponent implements Viewable, Destroyable {

	private boolean alive = true;

	private String resKey;
	private int layer;

	public Environment environment;
	public CreationHelper creationHelper;

	public GameElement(String resKey, int x, int y, int w, int h) {
		super(x, y, w, h);
		setResKey(resKey);
		setLayer(0);
	}

	@Override
	public String getResourceKey() {
		return resKey;
	}

	@Override
	public Rectangle getViewRect() {
		Point p = environment.getScreenRect().getLocation();

		Rectangle rect = getBoundingRect();
		rect.translate(-p.x, -p.y);
		return rect;
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
	public void activate(Environment environment, CreationHelper creationHelper) {
		this.environment = environment;
		this.creationHelper = creationHelper;
		alive = true;
	}

	@Override
	public void destroy() {
		if (isAlive()) {
			alive = false;
			creationHelper.onDestruction(this);
		}
	}

	@Override
	public boolean isAlive() {
		return alive;
	}

}
