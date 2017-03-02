/**
 * 
 */
package de.uni_kiel.progOOproject17.model;

import de.uni_kiel.progOOproject17.model.abs.GameCompound;
import de.uni_kiel.progOOproject17.view.abs.Viewable;

/**
 * @author Yannik Eikmeier
 * @since 02.03.2017
 *
 */
public class Menu extends GameCompound {

	/**
	 * @param x
	 * @param y
	 * @param w
	 * @param h
	 */
	public Menu(int x, int y, int w, int h) {
		super(x, y, w, h);
		
		
		
		
		
	}

	/* (non-Javadoc)
	 * @see de.uni_kiel.progOOproject17.model.abs.Ticked#tick(long)
	 */
	@Override
	public void tick(long timestamp) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see de.uni_kiel.progOOproject17.model.abs.GameCompound#getViewables()
	 */
	@Override
	public Viewable[] getViewables() {
		// TODO Auto-generated method stub
		return null;
	}

}
