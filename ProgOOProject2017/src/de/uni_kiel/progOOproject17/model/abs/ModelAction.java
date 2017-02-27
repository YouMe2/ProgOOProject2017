/**
 *
 */
package de.uni_kiel.progOOproject17.model.abs;

import java.awt.event.ActionEvent;
import java.util.HashMap;
import javax.swing.AbstractAction;

/**
 * @author Yannik Eikmeier
 * @since 22.02.2017
 */
public abstract class ModelAction extends AbstractAction {

	private static final HashMap<String, ModelAction> ACTIONS = new HashMap<>();

	public ModelAction(String name) {
		super(name);
		ACTIONS.put(name, this);

	}

	public static ModelAction getAction(String name) {
		return ACTIONS.get(name);
	}

	public static boolean performAction(String name, ActionEvent e) {
		ModelAction a = ACTIONS.get(name);
		if (a != null) {
			a.actionPerformed(e);
			return true;
		}
		return false;
	}

}
