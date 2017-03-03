/**
 * 
 */
package de.uni_kiel.progOOproject17.model;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;

import de.uni_kiel.progOOproject17.model.abs.Distance;
import de.uni_kiel.progOOproject17.model.abs.GameComponent;
import de.uni_kiel.progOOproject17.view.abs.Viewable;

/**
 * @author Yannik Eikmeier
 * @since 03.03.2017
 *
 */
public class ImageViewable  implements Viewable{

	
	private String resKey;
	private int layer;
	private Rectangle rect;

	public ImageViewable(String resKey, int x, int y, int w, int h, int layer) {
		this(resKey, new Rectangle(x, y, w, h), layer);
	}
	
	public ImageViewable(String resKey, Rectangle rect, int layer) {
		this.resKey = resKey;
		this.rect = rect;
		this.layer = layer;
	}
	
	
	/* (non-Javadoc)
	 * @see de.uni_kiel.progOOproject17.view.abs.Viewable#getResourceKey()
	 */
	@Override
	public String getResourceKey() {
		return resKey;
	}

	/* (non-Javadoc)
	 * @see de.uni_kiel.progOOproject17.view.abs.Viewable#getViewRect()
	 */
	@Override
	public Rectangle getViewRect() {
		return rect;
	}

	/* (non-Javadoc)
	 * @see de.uni_kiel.progOOproject17.view.abs.Viewable#getLayer()
	 */
	@Override
	public int getLayer() {
		return layer;
	}



	public void setLocation(int x, int y) {
		rect.setLocation(x, y);
	}

	public void setLocation(Point p) {
		setLocation(p.x, p.y);
	}

	public void translate(int dx, int dy) {
		rect.translate(dx, dy);
	}

	public void translate(Distance dis) {
		translate(dis.x, dis.y);
	}

	public void setSize(int w, int h) {
		rect.setSize(w, h);
	}

	public void setSize(Dimension d) {
		rect.setSize(d);
	}

	/**
	 * @return the x
	 */
	public int getX() {
		return rect.x;
	}

	/**
	 * @return the y
	 */
	public int getY() {
		return rect.y;
	}

	public Point getPosition() {
		return rect.getLocation();
	}

	/**
	 * @return the w
	 */
	public int getWidth() {
		return rect.width;
	}

	/**
	 * @return the h
	 */
	public int getHeight() {
		return rect.height;
	}

	/**
	 * @param pointsKey
	 */
	public void setResKey(String key) {
		this.resKey = key;
		
	}
	
	
}
