package de.uni_kiel.progOOproject17.model;

import javax.swing.Action;

public interface Actionable {

	public Action getAction(InputActionKeys iA);

	public void putAction(InputActionKeys iA, Action action);

	public void forwardAllActions(Actionable a);

}
