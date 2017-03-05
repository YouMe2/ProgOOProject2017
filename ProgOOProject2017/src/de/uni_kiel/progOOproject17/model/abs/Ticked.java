package de.uni_kiel.progOOproject17.model.abs;

/**
 * This interface represents the functionality of executing some code on a
 * constant continuously basis.
 *
 * @see #tick(long)
 *
 *
 */
@FunctionalInterface
public interface Ticked {

	/**
	 * Tickes (or Updates) this Object.
	 *
	 * @param timestamp
	 *            The program time in ms on which the tick is executed.
	 */
	public void tick(long timestamp);

}
