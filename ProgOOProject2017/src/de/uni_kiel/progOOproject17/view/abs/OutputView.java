package de.uni_kiel.progOOproject17.view.abs;

/**
 * This interface represents the most abstract functionality of any
 * {@link OutputView}.
 *
 * @see #render(Viewable[])
 *
 *
 */
public interface OutputView {

	/**
	 * Renders all the {@link Viewable}s to this {@link OutputView}.
	 *
	 * @param viewables
	 */
	public void render(Viewable[] viewables);

}
