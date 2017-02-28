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
		implements Gravitational, Destroyable {

	private Velocity velocity;

	/**
	 * @param x
	 * @param y
	 * @param w
	 * @param h
	 */
	public GameEntity(String resKey, int x, int y, int w, int h) {
		super(resKey, x, y, w, h);
		velocity = new Velocity(0, 0);
		setLayer(Viewable.ENTITY_LAYER);
	}

	@Override
	public void applyGravity() {
		// Dimension dis = getCollisionDistance(OBJECTS, velocity.width,
		// GRAVITY_ACCELERATION + velocity.height);
		// translate(dis.width, dis.height);

		velocity.speedY += GRAVITY_ACCELERATION;

	}

	public Velocity getVelocity() {
		return velocity;
	}

	public void setVelocity(Velocity v) {
		setVelocity(v.speedX, v.speedY);
	}

	public void setVelocity(int dx, int dy) {
		velocity = new Velocity(dx, dy);
	}

}
