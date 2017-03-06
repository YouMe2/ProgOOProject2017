package de.uni_kiel.progOOproject17.model.levelgen;

import de.uni_kiel.progOOproject17.model.abs.GameElement;

/**
 * An interface defining how an object that is capable of creating an obstacle
 * should look.
 *
 */
@FunctionalInterface
public interface ObstacleCreator {

	/**
	 * Creates a new obstacle and returns its components as an array of
	 * <code>GameElement</code>s.
	 *
	 * @param obstacleStart
	 *            the position of where to spawn the obstacle
	 * @return the obstacle components as an array of <code>GameElement</code>s
	 */
	public GameElement[] createNew(int obstacleStart);

}
