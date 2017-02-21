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
public abstract class AbstractViewableLayer implements Viewable{
    
    protected final Vector<Viewable> viewables = new Vector<>();
    
    @Override
    public void render(Graphics gr) {
        for (Viewable viewable : viewables) {
	    viewable.render(gr);           
	}
        
    }
}
