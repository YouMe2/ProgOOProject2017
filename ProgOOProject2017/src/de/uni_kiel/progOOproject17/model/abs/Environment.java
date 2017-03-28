package de.uni_kiel.progOOproject17.model.abs;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.function.Consumer;

/**
 * This interface represents some functionality the {@link Environment} the
 * {@link GameObject} are living in, which manly are collision detection methods.
 * 
 */
public interface Environment {
	// FIXME impossible to check for collision when the size changes!
	// (crouching)
	// FIXME no collision detected when the moved Distance jumps over an other
	// Collidable

	/**
	 * Checks for all colliding game objects and returns true if there's a
	 * collision after the given object would be translated by the specified
	 * distance.
	 *
	 * @param obj
	 *            the object to check for collisions
	 * @param dist
	 *            the distance to check for
	 * @return <code>true</code> if there's a collision
	 */
	public boolean willCollide(Collidable obj, Distance dist);

	/**
	 * Computes the furthest distance the object can be moved in the given
	 * distance without encountering a collision with any other object.
	 *
	 * @param obj
	 *            the object to regard
	 * @param maxDist
	 *            the distance to check for
	 * @return the maximal distance the object can be moven in the given
	 *         distance
	 */
	public Distance getCollisionDistance(Collidable obj, Distance maxDist);

	/**
	 * Scans the game for all objects that would collide with the given object
	 * if it was moved by the given distance and returns all positives as a
	 * list.
	 *
	 * @param g
	 *            the object to be checked for
	 * @param dist
	 *            the distance to check for
	 * @return a list of all objects that would collide with the given object if
	 *         it was moved by the given distance
	 */
	public ArrayList<Collidable> getCollObjects(Collidable g, Distance dist);

	/**
	 * Checks if the given objects are in contact.
	 *
	 * @param obj
	 *            the object to be checked for
	 * @return <code>true</code> if the given objects are in contact
	 */
	public boolean contacts(Collidable o1, Collidable o2);

	/**
	 * Checks if the given object is currently touching another object from
	 * above (that is, if it is currently on top of another object)
	 *
	 * @param obj
	 *            the object to be regarded
	 * @return <code>true</code> if the given object is currently touching
	 *         another object from above
	 */
	public boolean isOnGround(Collidable obj);

	/**
	 * Executes a given action for each colliding object as specified in
	 * {@link #willCollide(GameObject, Distance)}.
	 *
	 * @param obj
	 *            the object to check for collisions
	 * @param dist
	 *            the distance to check for
	 * @param consumer
	 * @see #willCollide(GameObject, Distance)
	 */
	public void forEachCollision(Collidable obj, Distance dist, Consumer<GameObject> consumer);

	/**
	 * Executes a given action for each contacting object as specified in
	 * {@link #contacts(GameObject)}.
	 *
	 * @param obj
	 *            the object to check for collisions
	 * @param dist
	 *            the distance to check for
	 * @param consumer
	 * @see #contacts(GameObject)
	 */
	public void forEachContact(Collidable obj, Consumer<GameObject> consumer);

	

}
