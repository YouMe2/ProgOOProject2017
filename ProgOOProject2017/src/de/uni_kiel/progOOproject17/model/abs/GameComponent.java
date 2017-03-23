package de.uni_kiel.progOOproject17.model.abs;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;

/**
 * This class is the base of the ingame-model class hierarchy. Every thing that
 * is some where in the game, therefore will be ticked, with an aspect of
 * locality to it needs to be a {@link GameComponent}.
 *
 */
public abstract class GameComponent implements Ticked {

	private Rectangle rect;

	/**
	 * Constructs a new GameComponent with the given boounds.
	 * 
	 * @param x the x coord
	 * @param y the y coord
	 * @param w the width
	 * @param h the height
	 */
	public GameComponent(int x, int y, int w, int h) {
		rect = new Rectangle(x, y, w, h);
	}
	
	/**
	 * Constructs a new GameComponent at the given position with no size.
	 * 
	 * @param x the x coord
	 * @param y the y coord
	 */
	public GameComponent(int x, int y) {
		rect = new Rectangle(x, y, 0, 0);
	}

	/**
	 * Sets the location to (x,y).
	 * 
	 * @param x the x coord
	 * @param y the y coord
	 */
	public void setLocation(int x, int y) {
		rect.setLocation(x, y);
	}

	/**
	 * Sets the location to p.
	 * 
	 * @param p the {@link Point}
	 */
	public void setLocation(Point p) {
		setLocation(p.x, p.y);
	}

	/**
	 * Translates this {@link GameComponent}.
	 * 
	 * @param dx the x distance
	 * @param dy the y distance
	 */
	public void translate(int dx, int dy) {
		rect.translate(dx, dy);
	}

	/**
	 * Translates this the given {@link Distance} dis.
	 * 
	 * @param dis the {@link Distance}
	 */
	public void translate(Distance dis) {
		translate(dis.x, dis.y);
	}

	/**
	 * Sets the Size.
	 * 
	 * @param w the new width.
	 * @param h the new height.
	 */
	public void setSize(int w, int h) {
		rect.setSize(w, h);
	}

	/**
	 * Sets the Size to d.
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
	 * Returns the bounding {@link Rectangle} of this {@link GameComponent}
	 * 
	 * @return the bounding {@link Rectangle}
	 */
	public Rectangle getBoundingRect() {
		return new Rectangle(rect);
	}

}
