/**
 *
 */
package de.uni_kiel.progOOproject17.model;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;

import de.uni_kiel.progOOproject17.model.abs.Ticked;
import de.uni_kiel.progOOproject17.view.abs.Viewable;

/**
 * @author Yannik Eikmeier
 * @since 23.02.2017
 */
public abstract class GameComponent implements Ticked{

	private Rectangle rect;

	public GameComponent(int x, int y, int w, int h) {
		rect.setBounds(x, y, w, h);
	}

	public void move(int x, int y) {
		rect.setLocation(x, y);
	}

	public void translate(int dx, int dy) {

		rect.translate(dx, dy);
	}

	public void translate(Dimension dis) {
		translate(dis.width, dis.height);
	}

	public void move(Point p) {
		move(p.x, p.y);
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
		return rect;
	}

}
