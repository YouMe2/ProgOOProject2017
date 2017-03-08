package de.uni_kiel.progOOproject17.model;

/**
 * This interface represents the functionality of being able to track some
 * statistics about progress, points and lifes for eg of the player.
 *
 */
public interface Stats {

	/**
	 * Returns the points p this object gained. <code> (0 <= p)</code>
	 * 
	 * @return the points
	 */
	int getPoints();

	/**
	 * Returns the progress made by this object as a double p.
	 * 
	 * <code> (0 <= p <= 1)</code>
	 * 
	 * @return the progress
	 */
	double getProgress();

	/**
	 * Returns the lifes l of this object.<code> (0 <= p)</code>
	 * 
	 * @return the lifes
	 */
	int getLifes();

}
