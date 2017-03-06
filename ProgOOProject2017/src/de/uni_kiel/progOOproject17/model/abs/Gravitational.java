package de.uni_kiel.progOOproject17.model.abs;

public interface Gravitational {

	// in (pixels / (2 * tick^2)) // s=1/2at^2 + vt + s
	public static final Distance GRAVITY_ACCELERATION = new Distance(0, 3);

	public void applyGravity();

}
