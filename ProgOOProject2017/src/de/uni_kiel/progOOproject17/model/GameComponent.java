/**
 *
 */
package de.uni_kiel.progOOproject17.model;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.LinkedList;

import de.uni_kiel.progOOproject17.model.abs.Ticked;

/**
 * @author Yannik Eikmeier
 * @since 23.02.2017
 */
public abstract class GameComponent implements Ticked {

	public static final LinkedList<GameComponent> COMPONENTS = new LinkedList<>();

	private Rectangle rect;

	public GameComponent(int x, int y, int w, int h) {
		rect = new Rectangle(x, y, w, h);
		COMPONENTS.add(this);
	}

	public void setLocation(int x, int y) {
		rect.setLocation(x, y);
	}

	public void translate(int dx, int dy) {
		rect.translate(dx, dy);
	}

	public void translate(Distance dis) {
		translate(dis.x, dis.y);
	}

	public void setLocation(Point p) {
		setLocation(p.x, p.y);
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

	public Rectangle getBoundingRect() {
		return new Rectangle(rect);
	}

}
