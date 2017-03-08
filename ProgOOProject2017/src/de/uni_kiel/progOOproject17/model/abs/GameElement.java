package de.uni_kiel.progOOproject17.model.abs;

import de.uni_kiel.progOOproject17.model.CreationHelper;
import de.uni_kiel.progOOproject17.view.abs.Viewable;
import java.awt.Point;
import java.awt.Rectangle;

/**
 * This class represents a advanced {@link GameComponent} that specifically acts
 * as a {@link Viewable} and a {@link Destroyable}.
 * Therefore it need to be activated!
 */
public abstract class GameElement extends GameComponent implements Viewable, Destroyable {

	private boolean alive = false;

	private String resKey;
	private int layer;

	/**
	 * stores the {@link Environment} after this {@link GameElement} was {@link #activate(Environment, CreationHelper)}d
	 */
	protected Environment environment;
	/**
	 * stores the {@link CreationHelper} after this {@link GameElement} was {@link #activate(Environment, CreationHelper)}d
	 */
	protected CreationHelper creationHelper;

	/**
	 * Constructs a new {@link GameElement} which still needs to be activated.
	 * 
	 * @param resKey the resource key for the internal {@link Viewable}
	 * @param x the x coord
	 * @param y the y coord
	 * @param w the width
	 * @param h the height
	 */
	public GameElement(String resKey, int x, int y, int w, int h) {
		super(x, y, w, h);
		setResKey(resKey);
		setLayer(0);
	}

	/* (non-Javadoc)
	 * @see de.uni_kiel.progOOproject17.view.abs.Viewable#getResourceKey()
	 */
	@Override
	public String getResourceKey() {
		return resKey;
	}

	/* (non-Javadoc)
	 * @see de.uni_kiel.progOOproject17.view.abs.Viewable#getViewRect()
	 */
	@Override
	public Rectangle getViewRect() {
		Point p = environment.getScreenRect().getLocation();

		Rectangle rect = getBoundingRect();
		rect.translate(-p.x, -p.y);
		return rect;
	}

	/* (non-Javadoc)
	 * @see de.uni_kiel.progOOproject17.view.abs.Viewable#getLayer()
	 */
	@Override
	public int getLayer() {
		return layer;
	}

	/**
	 * Sets the resource key.
	 * 
	 * @param resKey
	 */
	public void setResKey(String resKey) {
		this.resKey = resKey;
	}

	/**
	 * Sets the layer.
	 * 
	 * @param layer
	 */
	public void setLayer(int layer) {
		this.layer = layer;
	}

	/* (non-Javadoc)
	 * @see de.uni_kiel.progOOproject17.model.abs.Destroyable#activate(de.uni_kiel.progOOproject17.model.abs.Environment, de.uni_kiel.progOOproject17.model.CreationHelper)
	 */
	@Override
	public void activate(Environment environment, CreationHelper creationHelper) {
		alive = true;
		this.environment = environment;
		this.creationHelper = creationHelper;
	}

	/* (non-Javadoc)
	 * @see de.uni_kiel.progOOproject17.model.abs.Destroyable#destroy()
	 */
	@Override
	public void destroy() {
		if (isAlive()) {
			alive = false;
			creationHelper.onDestruction(this);
		}
	}

	/* (non-Javadoc)
	 * @see de.uni_kiel.progOOproject17.model.abs.Destroyable#isAlive()
	 */
	@Override
	public boolean isAlive() {
		return alive;
	}

}
