package de.uni_kiel.progOOproject17.view.abs;

import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.KeyStroke;

public interface InputView {

	/**
	 * Adds the action to the Input view, so that it will be performed when an
	 * event occurs that need to be specified by the String key. For example a
	 * keystroke.
	 *
	 * @see KeyStroke#getKeyStroke(String)
	 *
	 * @param key
	 *            a String specifying an event
	 * @param action
	 *            the action to be performed on that event
	 */
	public void addAction(String key, Action action);

	/**
	 * Adds an {@link ActionMap} to the {@link InputView}, so that the actions
	 * in it will be performed on specific events.
	 *
	 * @param actionMap
	 */
	public void addActionMap(ActionMap actionMap);

	/**
	 * Enables or disables all actions in this {@link InputView}.
	 *
	 * @param enabeled
	 */
	public void setEnabeled(boolean enabeled);

	/**
	 * Enables a specific action in this {@link InputView}.
	 *
	 * @param key
	 *            the key specifying the action
	 * @param enabeled
	 */
	public void setEnabeled(String key, boolean enabeled);

}
