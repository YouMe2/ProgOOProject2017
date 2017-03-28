package de.uni_kiel.progOOproject17.model;

import java.awt.Rectangle;

import de.uni_kiel.progOOproject17.model.abs.Environment;
import de.uni_kiel.progOOproject17.model.abs.GameObject;
import de.uni_kiel.progOOproject17.model.abs.Hitbox;
import de.uni_kiel.progOOproject17.view.abs.Viewable;

/**
 * This class represents a {@link GameObject} that serves as a Floor.
 *
 */
public class Floor extends GameObject {

	/**
	 * Constructs a new Floor.
	 * Which will not be active until it is activated by the {@link #activate(Environment, CreationHelper)} method.
	 * 
	 * @param resKey the resource key
	 * @param rect the {@link Rectangle} specifying the position and size
	 */
	public Floor(String resKey, Rectangle rect) {
		this(resKey, rect.x, rect.y, rect.width, rect.height);
	}

	/**
	 * Constructs a new Floor.
	 * Which will not be active until it is activated by the {@link #activate(Environment, CreationHelper)} method.
	 * 
	 * @param resKey the resource key
	 * @param x the x coord
	 * @param y the y coord
	 * @param w the width
	 * @param h the height
	 */
	public Floor(String resKey, int x, int y, int w, int h) {
		super(new Hitbox.RectHitbox(x, y, w, h), resKey, x, y, w, h, FLOOR_LAYER);

	}

	/* (non-Javadoc)
	 * @see de.uni_kiel.progOOproject17.model.abs.Ticked#tick(long)
	 */
	@Override
	public void tick(long timestamp) {
	}

}
