package de.uni_kiel.progOOproject17.model.abs;

import de.uni_kiel.progOOproject17.model.CreationHelper;

public abstract class GameEntity extends GameObject implements Gravitational {

	private Distance velocity;
	private boolean gravity = true;
	
	
	private boolean permaMoveX = false;
	private int permaXVel = 0;
	private boolean permaMoveY = false;
	private int permaYVel = 0;
	

	public GameEntity(String resKey, int x, int y, int w, int h, Environment environment, CreationHelper creatHelp) {
		super(resKey, x, y, w, h, environment, creatHelp);
		velocity = new Distance(0, 0);
		setLayer(ENTITY_LAYER);
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
	

	public void setGravityActive(boolean gravity) {
		this.gravity = gravity;
	}
	

	public void setPermaXVel(int dx) {
		if(dx == 0) {
			permaMoveX = false;
			return;
		}
		permaMoveX = true;
		permaXVel = dx;
	}
	
	public void setPermaYVel(int dy) {
		if(dy == 0) {
			permaMoveY = false;
			return;
		}
		permaMoveY = true;
		permaYVel = dy;
	}


	public void doMovement() {
		if(permaMoveX){
			setVelocity(permaXVel,getVelocity().y);
		}
		if (gravity)
			applyGravity();
		Distance collDist = environment.getCollisionDistance(this, getVelocity());
		translate(collDist);
		setVelocity(collDist);
		environment.forEachContact(this, t -> onContactWith(t));
	}

	public abstract void onContactWith(GameObject obj);

}
