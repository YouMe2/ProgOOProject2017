package de.uni_kiel.progOOproject17.model.abs;

import java.util.HashMap;
import javax.swing.Action;

import de.uni_kiel.progOOproject17.model.Actionable;
import de.uni_kiel.progOOproject17.model.InputActionKey;

/**
 * This class represents a advanced {@link GameCompound} that acts as a
 * {@link Screen} which specifly is {@link Actionable}.
 *
 */
public abstract class Screen extends GameCompound implements Actionable {

	private final HashMap<InputActionKey, Action> actions = new HashMap<>();

	/**
	 * Constructs a new Screen with no action preset!
	 * 
	 * @param w
	 *            the width
	 * @param h
	 *            the height
	 */
	public Screen(int w, int h) {
		super(0, 0, w, h);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.uni_kiel.progOOproject17.model.Actionable#putAction(de.uni_kiel.
	 * progOOproject17.model.InputActionKey, javax.swing.Action)
	 */
	@Override
	public void putAction(InputActionKey iA, Action action) {
		actions.put(iA, action);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.uni_kiel.progOOproject17.model.Actionable#getAction(de.uni_kiel.
	 * progOOproject17.model.InputActionKey)
	 */
	@Override
	public Action getAction(InputActionKey iA) {
		return actions.get(iA);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.uni_kiel.progOOproject17.model.Actionable#forwardAllActions(de.
	 * uni_kiel.progOOproject17.model.Actionable)
	 */
	@Override
	public void forwardAllActions(Actionable a) {
		actions.clear();

		for (InputActionKey key : InputActionKey.values())
			putAction(key, a.getAction(key));
	}

}
