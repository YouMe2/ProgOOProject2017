/**
 * 
 */
package de.uni_kiel.progOOproject17.tests.mvc.abst.v;

import java.awt.Graphics;
import java.util.Vector;

/**
 * @author Yannik Eikmeier
 * @since 21.02.2017
 *
 */
public class ViewableData extends Vector<Viewable> implements Viewable{

    @Override
    public void render(Graphics gr) {

	for (Viewable v : this) {
	    v.render(gr);
	}
	
    }
    
}
