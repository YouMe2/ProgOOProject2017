/**
 * 
 */
package de.uni_kiel.progOOproject17.tests.mvc.abst.v;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;

/**
 * @author Yannik Eikmeier
 * @since 21.02.2017
 *
 */
public class ShapeViewable implements Viewable{
    public enum Shape{ OVAL, RECT}
    
    private Point pos;
    private Dimension dim;
    private Shape s;
    private Color c;
  
    public ShapeViewable(int x, int y, int w, int h, Shape s, Color c ) {
	pos = new Point(x, y);
	dim = new  Dimension(w, h);
	this.s = s;
	this.c = c;
    }
    
    public Shape getShape() {
	return s;
    }
    public Color getColor() {
	return c;
    }
    
    @Override
    public Point getPosion() {
	return pos;
    }
    
    @Override
    public Dimension getDimension() {
	return dim;
    }
    
}
