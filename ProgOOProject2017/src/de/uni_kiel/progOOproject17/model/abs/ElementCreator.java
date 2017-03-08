package de.uni_kiel.progOOproject17.model.abs;

/**
 * This interface can be used to fastforward the power to create a new {@link GameElement}.
 */
@FunctionalInterface
public interface ElementCreator {

	/**
	 * Creates and activates the {@link GameElement} g.
	 * 
	 * @param g
	 */
	public void create(GameElement g);

}
