package de.uni_kiel.progOOproject17.model.abs;

import de.uni_kiel.progOOproject17.resources.GameProperties;

/**
 * This interface implies that instances of it are effected by gravity.
 */
public interface Gravitational {

	/**
	 * The gravitational acceleration stored as a {@link Distance} measured in (ingamepixels / tick^2).
	 */
	public static final Distance GRAVITY_ACCELERATION = new Distance(Integer.valueOf(GameProperties.getInstance().getProperty("xGravity")), Integer.valueOf(GameProperties.getInstance().getProperty("yGravity")));

	/**
	 * Applies the gravitational acceleration to this object.
	 */
	public void applyGravity();

}
