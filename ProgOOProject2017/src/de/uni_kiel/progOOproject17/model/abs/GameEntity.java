package de.uni_kiel.progOOproject17.model.abs;

import javax.swing.text.html.parser.Entity;

import de.uni_kiel.progOOproject17.model.SimpleViewable;
import de.uni_kiel.progOOproject17.view.abs.Viewable;

/**
 * This class represents a advanced {@link GameObject} that is also
 * {@link Gravitational}. A {@link GameEntity} has a velocity and provides a
 * {@link #doMovement()} method that acts depending of the velocity, gravity and
 * the {@link Environment} in was activated in.
 * 
 * 
 */
public abstract class GameEntity extends GameObject implements Gravitational {

	private Distance velocity;
	private boolean gravity = true;

	private boolean permaMoveX = false;
	private int permaXVel = 0;
	private boolean permaMoveY = false;
	private int permaYVel = 0;

	/**
	 * Creates a new {@link GameEntity} with a no starting velocity and active
	 * gravity.
	 * 
	 * @param resKey
	 *            the resource key for the internal {@link Viewable}.
	 * @param x
	 *            the x coord
	 * @param y
	 *            the y coord
	 * @param w
	 *            the width
	 * @param h
	 *            the heigt
	 */
	public GameEntity(Hitbox hitbox) {
		this(hitbox, null, 0, 0, 0, 0);
	}

	public GameEntity(Hitbox hitbox, String resKey, int x, int y, int w, int h) {
		super(hitbox, resKey, x, y, w, h, ENTITY_LAYER);
		velocity = new Distance(0, 0);
	}



	/*
	 * (non-Javadoc)
	 * 
	 * @see de.uni_kiel.progOOproject17.model.abs.Gravitational#applyGravity()
	 */
	@Override

	public void applyGravity() {
		velocity.add(GRAVITY_ACCELERATION);

	}

	/**
	 * @return a {@link Distance} representing the velocity of this
	 *         {@link Entity}
	 */
	public Distance getVelocity() {
		return new Distance(velocity);
	}

	/**
	 * Adds the given vel to this {@link GameEntity}'s velocity.
	 * 
	 * @param vel
	 */
	public void addVelocity(Distance vel) {
		velocity.add(vel);
	}

	/**
	 * Sets the velocity to v
	 * 
	 * @param v
	 */
	public void setVelocity(Distance v) {
		setVelocity(v.x, v.y);
	}

	/**
	 * Sets the velocity to a new {@link Distance}(dx, dy)
	 * 
	 * @param dx
	 * @param dy
	 */
	public void setVelocity(int dx, int dy) {
		velocity = new Distance(dx, dy);
	}

	/**
	 * Sets whether the gravity is active on this entity or not.
	 * 
	 * @param gravity
	 */
	public void setGravityActive(boolean gravity) {
		this.gravity = gravity;
	}

	/**
	 * Sets a permanent Velocity for the x component. If dx == 0 this will be
	 * disabled.
	 * 
	 * @param dx
	 */
	public void setPermaXVel(int dx) {
		if (dx == 0) {
			permaMoveX = false;
			return;
		}
		permaMoveX = true;
		permaXVel = dx;
	}

	/**
	 * Sets a permanent Velocity for the y component. If dy == 0 this will be
	 * disabled.
	 * 
	 * @param dy
	 */
	public void setPermaYVel(int dy) {
		if (dy == 0) {
			permaMoveY = false;
			return;
		}
		permaMoveY = true;
		permaYVel = dy;
	}

	/**
	 * Performes the movement for this {@link GameEntity} taking into account
	 * the gravity, the velocity and the {@link Environment}.
	 * 
	 */
	public void doMovement() {
		if (permaMoveX)
			setVelocity(permaXVel, getVelocity().y);
		if (permaMoveY)
			setVelocity(getVelocity().x, permaYVel);
		if (gravity)
			applyGravity();
		Distance collDist = environment.getCollisionDistance(this, getVelocity());
		if (collDist.getSqLength() != 0)
			move(collDist);
		setVelocity(collDist);
		environment.forEachContact(this, t -> onContactWith(t));
	}
	
	public void move(Distance d){	
		getThisHitbox().translate(d);
		if (getView() != null)
			getView().translate(d);
	}
	
	public void move(int dx, int dy){	
		move(new Distance(dx, dy));
	}
	
	

	/**
	 * This method will be called every time this {@link GameEntity} made direct
	 * contact with an other {@link GameObject}.
	 * 
	 * @param obj
	 *            the {@link GameObject} this is in contact with
	 */
	public abstract void onContactWith(GameObject obj);

}
