/**
 * 
 */
package de.uni_kiel.progOOproject17.tests.mvc.abst.v;

import java.util.Vector;

/**
 * @author Yannik Eikmeier
 * @since 21.02.2017
 *
 */
public abstract class AbstractViewableData {

    protected final int width;
    protected final int height;
    
    protected Vector<AbstractLayer> layers = new Vector<>();
    
    public AbstractViewableData(int w, int h) {
	this.width = w;
	this.height = h;
    }
    
    public AbstractLayer getLayer(int n) {
	return layers.get(n);
    }
    
    public int size() {
	return layers.size();
    }
    
    
    
}
