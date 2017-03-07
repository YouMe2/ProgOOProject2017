package de.uni_kiel.progOOproject17.model;

import javax.swing.Action;

/**
 * This interface will be implemented by anything that will react to some
 * forwarded InputAction specified by a member of {@link InputActionKeys}.
 * 
 */
public interface Actionable {

	/**
	 * Returns the corresponding action for the iAkey.
	 * 
	 * @param iAkey
	 *            the key which determines the action
	 * @return the corresponding action
	 */
	public Action getAction(InputActionKeys iAkey);

	/**
	 * Puts a action for a specific member of {@link InputActionKeys}.
	 * 
	 * @param iAkey
	 *            the {@link InputActionKeys}
	 * @param action
	 *            the action
	 */
	public void putAction(InputActionKeys iAkey, Action action);

	/**
	 * Forwards all actions to this {@link Actionable} from the
	 * {@link Actionable} a.
	 * 
	 * @param a
	 *            the {@link Actionable} to forward the actions from.
	 */
	public void forwardAllActions(Actionable a);

}
