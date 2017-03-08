package de.uni_kiel.progOOproject17.model.abs;

/**
 * This interface serves as a listener that is used to call back when a {@link Destroyable} got {@link Destroyable#destroy()}ed.
 */
@FunctionalInterface
public interface DestroyListener {

	/**
	 * Will be called when the {@link Destroyable} d got {@link Destroyable#destroy()}ed
	 * 
	 * @param d the {@link Destroyable}
	 */
	public void onDestruction(Destroyable d);

}
