package de.uni_kiel.progOOproject17.model.abs;

import de.uni_kiel.progOOproject17.model.CreationHelper;

/**
 * This interface implies that instances of this it can be destroyed and need to
 * be activated in the first place!
 * 
 */
public interface Destroyable {

	/**
	 * Returns whether this is still alive.
	 * 
	 * @return
	 */
	public boolean isAlive();

	/**
	 * Destroys this.
	 */
	public void destroy();

	/**
	 * Activates this.
	 * 
	 * @param environment The {@link Environment} in which this object will live.
	 * @param creatHelp The {@link CreationHelper} with which this object will stay in contact with.
	 */
	public void activate(Environment environment, CreationHelper creatHelp);

}
