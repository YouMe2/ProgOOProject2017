/**
 *
 */
package de.uni_kiel.progOOproject17.model;

import de.uni_kiel.progOOproject17.view.abs.Viewable;

/**
 * @author Yannik Eikmeier
 * @since 24.02.2017
 */
public abstract class GameEntity extends GameObject
		implements Gravitational {

	private Distance velocity;

	/**
	 * @param x
	 * @param y
	 * @param w
	 * @param h
	 */
	public GameEntity(String resKey, int x, int y, int w, int h) {
		super(resKey, x, y, w, h);
		velocity = new Distance(0, 0);
		setLayer(Viewable.ENTITY_LAYER);
	}

	@Override
	public void applyGravity() {
		velocity.add(GRAVITY_ACCELERATION);
		
	}

	public Distance getVelocity() {
		return velocity;
	}
	
	public void addVelocity(Distance vel) {
	    velocity.add(vel);
	}

	public void setVelocity(Distance v) {
		setVelocity(v.x, v.y);
	}

	public void setVelocity(int dx, int dy) {
		velocity = new Distance(dx, dy);
	}
	
	public void doMovement(Distance dis) {
	    translate(dis);
	    setVelocity(dis);
	}
	
	
	public boolean isOnGround(){
	    return willCollide(OBJECTS, new Distance(0,1));
	}
	

}
