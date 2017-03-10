package de.uni_kiel.progOOproject17.view.abs;

import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.KeyStroke;

/**
 * This class provides the useful functionality of combining an {@link InputMap}
 * and and {@link ActionMap} for key inputs into an {@link InputView}.
 *
 * @see KeyStroke#getKeyStroke(String)
 *
 *
 */
public class MappedKeyInput implements InputView {

	private InputMap inMap;
	private ActionMap aMap;
	private boolean enabeled = false;

	/**
	 * Creates a new {@link MappedKeyInput} with the specified
	 * {@link InputMap} and {@link ActionMap}.
	 *
	 * @param inMap
	 *            the {@link InputMap}
	 * @param aMap
	 *            the {@link ActionMap}
	 */
	public void initMaps(InputMap inMap, ActionMap aMap) {
		this.inMap = inMap;
		this.aMap = aMap;
		enabeled = true;

	}

	/*
	 * (non-Javadoc)
	 *
	 * @see de.uni_kiel.progOOproject17.view.abs.InputView#addAction(java.lang.
	 * String, javax.swing.Action)
	 */
	@Override
	public void addAction(String actionKey, Action action) {
		KeyStroke key = KeyStroke.getKeyStroke(actionKey);
		if (key != null)
			addKeyAction(key, action);
	}

	/**
	 * Adds a Keyaction to this {@link InputView}.
	 *
	 * @param key
	 *            The {@link KeyStroke} for the action
	 * @param action
	 *            The Action
	 */
	public void addKeyAction(KeyStroke key, Action action) {
		if (enabeled)
			inMap.put(key, key);
		else
			inMap.put(key, "none");
		aMap.put(key, action);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see de.uni_kiel.progOOproject17.view.abs.InputView#setEnabeled(boolean)
	 */
	@Override
	public void setEnabeled(boolean enabeled) {
		this.enabeled = enabeled;

		for (KeyStroke key : inMap.keys())
			if (enabeled)
				inMap.put(key, key);
			else
				inMap.put(key, "none");

	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * de.uni_kiel.progOOproject17.view.abs.InputView#setEnabeled(java.lang.
	 * String, boolean)
	 */
	@Override
	public void setEnabeled(String actionKey, boolean enabeled) {
		KeyStroke key = KeyStroke.getKeyStroke(actionKey);
		if (enabeled)
			inMap.put(key, key);
		else
			inMap.put(key, "none");

	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * de.uni_kiel.progOOproject17.view.abs.InputView#addActionMap(javax.swing.
	 * ActionMap)
	 */
	@Override
	public void addActionMap(ActionMap actionMap) {

		for (Object o : actionMap.keys())
			addAction((String) o, actionMap.get(o));
	}

}
