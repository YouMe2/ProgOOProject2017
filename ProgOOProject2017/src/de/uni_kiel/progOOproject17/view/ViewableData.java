/**
 * 
 */
package de.uni_kiel.progOOproject17.view;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Vector;

/**
 * @author Yannik Eikmeier
 * @since 21.02.2017
 *
 */
public class ViewableData extends ArrayList<Viewable> implements Viewable {

    @Override
    public void render(Graphics gr) {
	synchronized (this) {

	    for (Viewable v : this) {
		if (v != null)
		    v.render(gr);
	    }
	}

    }

}
