package de.uni_kiel.progOOproject17.model.abs;

import java.awt.Rectangle;

/**
 * This interface implies that instances of this it are
 * {@link Collidable} and therefore must have a bounding rect other
 * {@link Collidable}s could collide with.
 */
public interface Collidable {

	/**
	 * Returns the bounding {@link Rectangle} of this {@link Collidable}.
	 * 
	 * @return the bounding {@link Rectangle}
	 */
	public Rectangle getBoundingRect();

}
