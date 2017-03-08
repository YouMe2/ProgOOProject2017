package de.uni_kiel.progOOproject17.model.abs;

import de.uni_kiel.progOOproject17.view.abs.Viewable;

/**
 * This class represents the most basic outline of any {@link TickedBaseModel}.
 *
 * @see Ticked
 *
 */
public abstract class TickedBaseModel extends AbstractBaseModel implements Ticked {

	
	/* (non-Javadoc)
	 * @see de.uni_kiel.progOOproject17.model.abs.Ticked#tick(long)
	 */
	@Override
	public abstract void tick(long timestamp);
	
	/* (non-Javadoc)
	 * @see de.uni_kiel.progOOproject17.model.abs.AbstractBaseModel#getViewables()
	 */
	@Override
	public abstract Viewable[] getViewables();
}
