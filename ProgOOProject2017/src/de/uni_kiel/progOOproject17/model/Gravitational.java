/**
 *
 */
package de.uni_kiel.progOOproject17.model;

/**
 * @author Yannik Eikmeier
 * @since 24.02.2017
 */
public interface Gravitational {

	// in (pixels / (2 * tick^2)) // s=1/2at^2 + vt + s
	public static final int GRAVITY_ACCELERATION = 3;

	public void applyGravity();

}