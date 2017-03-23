package de.uni_kiel.progOOproject17.model;

import de.uni_kiel.progOOproject17.model.abs.Distance;
import de.uni_kiel.progOOproject17.view.abs.Viewable;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;

/**
 * This class serves as a simple implementation on the {@link Viewable} interface.
 *
 */
public class ImageViewable implements Viewable {

	private String resKey;
	private int layer;
	private Rectangle rect;
	private Rectangle relativeAnchor  = new Rectangle(0, 0, 0, 0);

	/**
	 * Constructs a new {@link ImageViewable}.
	 * 
	 * @param resKey the resource key
	 * @param x the x coord
	 * @param y the y coord 
	 * @param w the width
	 * @param h the height
	 * @param layer the layer
	 */
	public ImageViewable(String resKey, int x, int y, int w, int h, int layer) {
		this(resKey, new Rectangle(x, y, w, h), layer);
	}

	/**
	 * Constructs a new {@link ImageViewable}.
	 * 
	 * @param resKey the resource key
	 * @param rect the {@link Rectangle} 
	 * @param layer the layer
	 */
	public ImageViewable(String resKey, Rectangle rect, int layer) {
		this.resKey = resKey;
		this.rect = rect;
		this.layer = layer;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see de.uni_kiel.progOOproject17.view.abs.Viewable#getResourceKey()
	 */
	@Override
	public String getResourceKey() {
		return resKey;
	}

	
	/* (non-Javadoc)
	 * @see de.uni_kiel.progOOproject17.view.abs.Viewable#getViewRect(java.awt.Point)
	 */
	@Override
	public Rectangle getViewRect() {		
		Rectangle r = new Rectangle(rect);
		r.translate(-relativeAnchor.x, -relativeAnchor.y);
		return r;
		
	}
	

	/*
	 * (non-Javadoc)
	 *
	 * @see de.uni_kiel.progOOproject17.view.abs.Viewable#getLayer()
	 */
	@Override
	public int getLayer() {
		return layer;
	}
	
	public void setRelativeAnchor(Rectangle relativeAnchor) {
		this.relativeAnchor = relativeAnchor;
	}
	

	/**
	 * sets the location
	 * 
	 * @param x
	 * @param y
	 */
	public void setLocation(int x, int y) {
		rect.setLocation(x, y);
	}

	/**
	 * sets the location
	 * @param p
	 */
	public void setLocation(Point p) {
		setLocation(p.x, p.y);
	}

	/**
	 * translates the location
	 * 
	 * @param dx
	 * @param dy
	 */
	public void translate(int dx, int dy) {
		rect.translate(dx, dy);
	}

	/**
	 * translates the location
	 * 
	 * @param dis the {@link Distance}
	 */
	public void translate(Distance dis) {
		translate(dis.x, dis.y);
	}

	/**
	 * sets the size
	 * 
	 * @param w
	 * @param h
	 */
	public void setSize(int w, int h) {
		rect.setSize(w, h);
	}

	/**
	 * sets the size
	 * 
	 * @param d the {@link Dimension}
	 */
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

	/**
	 * @return the position
	 */
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
		resKey = key;

	}


}
