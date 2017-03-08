package de.uni_kiel.progOOproject17.model.abs;

import de.uni_kiel.progOOproject17.view.abs.OutputView;
import de.uni_kiel.progOOproject17.view.abs.Viewable;

/**
 * This class serves as the most abstract BaseModel for the MVC structure.
 *
 * @see #getViewables()
 *
 *
 */
public abstract class AbstractBaseModel {

	/**
	 * Returns all the {@link Viewable}s this model need to be rendered by some
	 * {@link OutputView}.
	 *
	 * @return all the {@link Viewable}s this model provides
	 */
	public abstract Viewable[] getViewables();

}
