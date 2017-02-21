/**
 * 
 */
package de.uni_kiel.progOOproject17.tests.mvc.abst.v;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Point;

/**
 * @author Yannik Eikmeier
 * @since 21.02.2017
 *
 */
public class ImageViewable implements Viewable{

    private Image img;
    private Point pos;
    private Dimension dim;
    
    /**
     * 
     */
    public ImageViewable(int x, int y, int w, int h, Image img) {
	pos = new Point(x, y);
	this.img = img;
	dim = new Dimension(w, h);
    }

    
    @Override
    public Point getPosion() {
	return pos;
    }

    @Override
    public Dimension getDimension() {
	return dim;
    }
    
    public Image getImage() {
	return img;
    }
    
}
