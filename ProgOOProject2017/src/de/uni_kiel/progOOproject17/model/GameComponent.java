/**
 * 
 */
package de.uni_kiel.progOOproject17.model;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;

import de.uni_kiel.progOOproject17.model.abs.TickedDataModel;

/**
 * @author Yannik Eikmeier
 * @since 23.02.2017
 *
 */
public abstract class GameComponent extends TickedDataModel {

	private int x;
	private int y;

	private int w;
	private int h;

	public GameComponent(int x, int y, int w, int h) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
	}

	public void move(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public void translate(int dx, int dy) {
		this.x = this.x + dx;
		this.y = this.y + dy;
	}
	
	public void translate(Dimension dis) {
		translate(dis.width, dis.height);
	}

	public void move(Point p) {
		move(p.x, p.y);
	}

	public void setSize(int w, int h) {
		this.w = w;
		this.h = h;
	}

	public void setSize(Rectangle rect) {
		setSize(rect.width, rect.height);
	}

	/**
	 * @return the x
	 */
	public int getX() {
		return x;
	}

	/**
	 * @return the y
	 */
	public int getY() {
		return y;
	}

	public Point getPosition() {
		return new Point(x, y);
	}

	/**
	 * @return the w
	 */
	public int getWidth() {
		return w;
	}

	/**
	 * @return the h
	 */
	public int getHeight() {
		return h;
	}

	public Rectangle getBoundingRect() {
		return new Rectangle(x, y, w, h);
	}

}
