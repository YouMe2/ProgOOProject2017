package de.uni_kiel.progOOproject17.model;

import de.uni_kiel.progOOproject17.view.abs.Viewable;

public abstract class GameEntity extends GameObject implements Gravitational {

	private Distance velocity;

	public GameEntity(String resKey, int x, int y, int w, int h, Environment environment) {
		super(resKey, x, y, w, h, environment);
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
		environment.forEachContact(this, t -> onContactWith(t));
	}

	public abstract void onContactWith(GameObject obj);

}
