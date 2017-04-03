/**
 * 
 */
package de.uni_kiel.progOOproject17.model;

import de.uni_kiel.progOOproject17.view.abs.Viewable;
import de.uni_kiel.progOOproject17.view.abs.Viewable.Key;

/**
 * @author Yannik Eikmeier
 * @since 30.03.2017
 *
 */
public class ViewablesList extends SortedLinkedList <Viewable> implements Key{

	public static final String LISTKEY_TEXT = "ViewablesList";
	
	private static final long serialVersionUID = -326193325738471825L;

	/* (non-Javadoc)
	 * @see de.uni_kiel.progOOproject17.view.abs.Viewable.Key#getText()
	 */
	@Override
	public String getText() {
		return LISTKEY_TEXT;
	}
}
