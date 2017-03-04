/**
 * 
 */
package de.uni_kiel.progOOproject17.model;

import javax.swing.Action;

/**
 * @author Yannik Eikmeier
 * @since 04.03.2017
 *
 */
public interface Actionable {

	public Action getAction(InputActionKeys iA);
	public void putAction(InputActionKeys iA, Action action);
	public void copyAllActions(Actionable a);
	
}
