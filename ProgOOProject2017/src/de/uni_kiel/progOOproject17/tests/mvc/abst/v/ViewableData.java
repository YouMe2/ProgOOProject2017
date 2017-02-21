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
public class ViewableData implements Viewable{

    private final int width;
    private final int height;
    
    private Vector<AbstractViewableLayer> layers = new Vector<>();
    
    public ViewableData(int w, int h) {
	this.width = w;
	this.height = h;
    }
    
    @Override
    public void render(Graphics gr) {

	for (AbstractViewableLayer layer : layers) {
	    layer.render(gr);
	}
	
    }
    
    
    
}
