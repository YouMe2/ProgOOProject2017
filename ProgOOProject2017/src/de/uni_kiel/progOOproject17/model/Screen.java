/**
 * 
 */
package de.uni_kiel.progOOproject17.model;

import java.util.HashMap;

import javax.swing.Action;

import de.uni_kiel.progOOproject17.model.abs.GameCompound;

/**
 * @author Yannik Eikmeier
 * @since 04.03.2017
 *
 */
public abstract class Screen extends GameCompound implements Actionable {

	private final HashMap<InputActionKeys, Action> actions = new HashMap<>();
	
	public Screen( int w, int h) {
		super(0, 0, w, h);
	}
	
	public void putAction(InputActionKeys iA, Action action) {
		actions.put(iA, action);
	}

	 
	@Override
	public Action getAction(InputActionKeys iA) {
		return actions.get(iA);
	}
	
	
	@Override
	public void forwardAllActions(Actionable a) {
		actions.clear();
		
		for (InputActionKeys key : InputActionKeys.values()) {
			putAction(key, a.getAction(key));
		}
	}
	

}
