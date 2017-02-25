/**
 * 
 */
package de.uni_kiel.progOOproject17.model;

import java.awt.Dimension;
import java.awt.Point;
import java.lang.reflect.GenericArrayType;
import java.util.ArrayList;

import de.uni_kiel.progOOproject17.view.abs.Viewable;

/**
 * @author Yannik Eikmeier
 * @since 24.02.2017
 *
 */
public abstract class GameEntity extends GameObject implements Gravitational, Destroyable {


	private Dimension velocity;

	/**
	 * @param x
	 * @param y
	 * @param w
	 * @param h
	 */
	public GameEntity(int x, int y, int w, int h) {
		super(x, y, w, h);
		velocity = new Dimension(0, 0);
	}

	@Override
	public void applyGravity() {
//		Dimension dis = getCollisionDistance(OBJECTS, velocity.width, GRAVITY_ACCELERATION + velocity.height);
//		translate(dis.width, dis.height);
		
		velocity.height += GRAVITY_ACCELERATION;
		

	}
	
	public Dimension getVelocity() {
		return velocity;
	}
	
	public void setVelocity(Dimension velocity) {
		this.velocity = velocity;
	}
	
	public void setVelocity(int dx, int dy) {
		setVelocity(new Dimension(dx, dy));
	}
	
	
	

}
