package de.uni_kiel.progOOproject17.model.abs;

import java.awt.Point;
import java.awt.Rectangle;

import de.uni_kiel.progOOproject17.model.CreationHelper;
import de.uni_kiel.progOOproject17.model.SimpleViewable;
import de.uni_kiel.progOOproject17.view.abs.Viewable;

public abstract class GameElement implements Viewable, Destroyable, Ticked {

	private boolean alive = false;
	private SimpleViewable view;

	/**
	 * stores the {@link Environment} after this {@link GameElement} was
	 * {@link #activate(Environment, CreationHelper)}d
	 */
	protected Environment environment;
	/**
	 * stores the {@link CreationHelper} after this {@link GameElement} was
	 * {@link #activate(Environment, CreationHelper)}d
	 */
	protected CreationHelper creationHelper;

	public GameElement() {
		this(null, 0, 0, 0, 0, -1);
	}

	/**
	 * Constructs a new {@link GameElement} which still needs to be activated.
	 * 
	 * @param resKey
	 *            the resource key for the internal {@link Viewable}
	 * @param x
	 *            the x coord
	 * @param y
	 *            the y coord
	 * @param w
	 *            the width
	 * @param h
	 *            the height
	 */
	public GameElement(String resKey, int x, int y, int w, int h, int layer) {
		setView(resKey, x, y, w, h, layer);
	}

	/**
	 * @param view
	 *            the view to set
	 */
	public void setView(String resKey, int x, int y, int w, int h, int layer) {
		view = new SimpleViewable(resKey, x, y, w, h, layer);
	}


	public SimpleViewable getView() {
		return view;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.uni_kiel.progOOproject17.view.abs.Viewable#getResourceKey()
	 */
	@Override
	public Key getContentKey() {
		return view.getContentKey();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.uni_kiel.progOOproject17.view.abs.Viewable#getViewRect()
	 */
	@Override
	public Rectangle getViewRect() {
		return view.getViewRect();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.uni_kiel.progOOproject17.view.abs.Viewable#getLayer()
	 */
	@Override
	public int getLayer() {
		return view.getLayer();
	}
	
	/* (non-Javadoc)
	 * @see de.uni_kiel.progOOproject17.view.abs.Viewable#isVisble()
	 */
	@Override
	public boolean isVisble() {
		return view.isVisble();
	}
	
	/* (non-Javadoc)
	 * @see de.uni_kiel.progOOproject17.view.abs.Viewable#getPriority()
	 */
	@Override
	public float getPriority() {
		return view.getPriority();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.uni_kiel.progOOproject17.model.abs.Destroyable#activate(de.uni_kiel.
	 * progOOproject17.model.abs.Environment,
	 * de.uni_kiel.progOOproject17.model.CreationHelper)
	 */
	@Override
	public void activate(Environment environment, CreationHelper creationHelper) {
		alive = true;
		this.environment = environment;
		this.creationHelper = creationHelper;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.uni_kiel.progOOproject17.model.abs.Destroyable#destroy()
	 */
	@Override
	public void destroy() {
		if (isAlive()) {
			alive = false;
			creationHelper.onDestruction(this);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.uni_kiel.progOOproject17.model.abs.Destroyable#isAlive()
	 */
	@Override
	public boolean isAlive() {
		return alive;
	}

}
