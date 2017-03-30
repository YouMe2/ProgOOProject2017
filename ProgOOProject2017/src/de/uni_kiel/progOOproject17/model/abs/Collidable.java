package de.uni_kiel.progOOproject17.model.abs;

/**
 * This interface implies that instances of this it are
 * {@link Collidable} and therefore must have a bounding rect other
 * {@link Collidable}s could collide with.
 */
public interface Collidable {

	/**
	 * Returns the {@link Hitbox} of this {@link Collidable}.
	 * 
	 * @return the {@link Hitbox}
	 */
	public Hitbox getHitbox();
	
	/**
	 * @return whether this {@link Hitbox} is restrictin Movent of other
	 *         Hitboxes
	 */
	public boolean isMovementRestricting();
	
	
	//TODO on hit effects
//	public void getOnHitEffects();

}
