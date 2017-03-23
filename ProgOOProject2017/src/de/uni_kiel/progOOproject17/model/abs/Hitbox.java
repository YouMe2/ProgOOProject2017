package de.uni_kiel.progOOproject17.model.abs;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Line2D;

/**
 * @author Yannik Eikmeier
 * @since 14.03.2017
 *
 */
public abstract class Hitbox {

	/**
	 * position the upper left corner of this {@link Hitbox}
	 */
	private int x, y;

	private Hitbox(Point p) {
		this(p.x, p.y);
	}

	private Hitbox(int x, int y) {
		setLocation(x, y);
	}

	/**
	 * sets the LOcation of this {@link Hitbox} to (x,y)
	 * 
	 * @param x
	 * @param y
	 */
	public void setLocation(int x, int y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Translates this {@link Hitbox}.
	 * 
	 * @param d
	 */
	public void translate(Distance d) {
		x += d.x;
		y += d.y;
	}

	/**
	 * Returns a new translated version of this {@link Hitbox} and leaves this
	 * {@link Hitbox} untouched.
	 * 
	 * @param d
	 *            the {@link Distance}
	 * @return a translated version of this {@link Hitbox}
	 * @throws CloneNotSupportedException
	 *             if this {@link Hitbox} is does not support the clone
	 *             operation.
	 */
	public Hitbox getCloneTranslate(Distance d) {
		Hitbox h = this.clone();
		h.translate(d);
		return h;

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

	/**
	 * 
	 * @return a new {@link Point} holding the Location of this {@link Hitbox}
	 */
	public Point getLocation() {
		return new Point(x, y);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#clone()
	 */
	@Override
	public abstract Hitbox clone();

	/**
	 * Determins whether this {@link Hitbox} intersects with the other
	 * {@link Hitbox}.
	 * 
	 * @param other
	 *            the other {@link Hitbox}
	 * @return whether the two {@link Hitbox}es intersect.
	 */
	public abstract boolean intersects(Hitbox other);

	/**
	 * @param hitbox
	 * @return
	 */
	public abstract boolean contacts(Hitbox hitbox);

	// STATIC --------------------------------------------------

	public static enum Types {
		RECT, CIRCLE, LINE, POINT, NONE
	}

	public static Hitbox newHitbox(Types type, int... arg) {
		try {
			switch (type) {
			case RECT:
				return new RectHitbox(arg[0], arg[1], arg[2], arg[3]);

			case CIRCLE:
				return new CircleHitbox(arg[0], arg[1], arg[2]);

			case LINE:
				return new LineHitbox(arg[0], arg[1], arg[2], arg[3]);

			case POINT:
				return new PointHitbox(arg[0], arg[1]);

			case NONE:
				return new NoHitbox(arg[0], arg[1]);
			default:
				break;
			}
		} catch (Exception e) {
		}

		throw new IllegalArgumentException("Too few arguments!");

	}

	public static Hitbox.Types getHitboxSubType(Hitbox h) {

		if (h instanceof RectHitbox)
			return Types.RECT;
		if (h instanceof CircleHitbox)
			return Types.CIRCLE;
		if (h instanceof LineHitbox)
			return Types.LINE;
		if (h instanceof PointHitbox)
			return Types.POINT;
		if (h instanceof NoHitbox)
			return Types.NONE;

		return Types.NONE;
	}

	public static class RectHitbox extends PolygonHitbox {

		private int w, h;

		public RectHitbox(int x, int y, int w, int h) {
			super(new Point[] { new Point(x, y), new Point(x + w, y), new Point(x + w, y + h), new Point(x, y + h) });
			this.w = w;
			this.h = h;

		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see de.uni_kiel.progOOproject17.model.abs.Hitbox#clone()
		 */
		@Override
		public RectHitbox clone() {
			return new RectHitbox(this.getX(), this.getY(), w, h);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * de.uni_kiel.progOOproject17.model.abs.Hitbox#intersects(de.uni_kiel.
		 * progOOproject17.model.abs.Hitbox)
		 */
		@Override
		public boolean intersects(Hitbox other) {

			if (other instanceof RectHitbox) {

				return this.getRectangle().intersects(((RectHitbox) other).getRectangle());
			}
			return other.intersects(this);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * de.uni_kiel.progOOproject17.model.abs.Hitbox.PolygonHitbox#contacts(
		 * de.uni_kiel.progOOproject17.model.abs.Hitbox)
		 */
		@Override
		public boolean contacts(Hitbox hitbox) {

			return new RectHitbox(getX() - 1, getY() - 1, getSize().width + 2, getSize().height + 2).intersects(hitbox);

		}

		protected Rectangle getRectangle() {
			return new Rectangle(this.getX(), this.getY(), w, h);
		}

		public void setSize(int w, int h) {
			this.w = w;
			this.h = h;
		}

		public Dimension getSize() {
			return new Dimension(w, h);
		}

	}

	public static class CircleHitbox extends Hitbox {

		private int r;

		public CircleHitbox(int x, int y, int r) {
			super(x, y);
			this.r = r;

		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see de.uni_kiel.progOOproject17.model.abs.Hitbox#clone()
		 */
		@Override
		public CircleHitbox clone() {
			return new CircleHitbox(this.getX(), this.getY(), r);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * de.uni_kiel.progOOproject17.model.abs.Hitbox#intersects(de.uni_kiel.
		 * progOOproject17.model.abs.Hitbox)
		 */
		@Override
		public boolean intersects(Hitbox other) {

			if (other instanceof CircleHitbox) {
				CircleHitbox hb = ((CircleHitbox) other);

				return this.getDistanceToCenter(hb.getX() + hb.r, hb.getY() + hb.r).getSqLength() < r * r;

			}

			if (other instanceof PolygonHitbox) {
				PolygonHitbox oPoly = (PolygonHitbox) other;
				Point[] points = oPoly.getPoints();

				for (int i = 0; i < points.length; i++) {
					Point p1 = points[i];
					Point p2 = points[(i - 1 >= 0) ? (i - 1) : (points.length - 1)];
					if (Line2D.ptSegDistSq(p1.x, p1.y, p2.x, p2.y, getX() + r, getY() + r) < r * r)
						return true;

				}
				return false;

			}

			return other.intersects(this);

		}

		protected Distance getDistanceToCenter(int x, int y) {
			return new Distance(Math.abs(this.getX() + r - x), Math.abs(this.getY() + r - y));
		}

		public void setRadius(int r) {
			this.r = r;
		}

		/**
		 * @return the r
		 */
		public int getRadius() {
			return r;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * de.uni_kiel.progOOproject17.model.abs.Hitbox#contacts(de.uni_kiel.
		 * progOOproject17.model.abs.Hitbox)
		 */
		@Override
		public boolean contacts(Hitbox hitbox) {
			return new CircleHitbox(getX() - 1, getY() - 2, getRadius() + 2).intersects(hitbox);
		}

	}

	public static class LineHitbox extends PolygonHitbox {

		protected int x2, y2;

		public LineHitbox(int x1, int y1, int x2, int y2) {
			super(new Point[] { new Point(x1, y1), new Point(x2, y2) });
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see de.uni_kiel.progOOproject17.model.abs.Hitbox#clone()
		 */
		@Override
		public LineHitbox clone() {
			return new LineHitbox(this.getX(), this.getY(), x2, y2);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * de.uni_kiel.progOOproject17.model.abs.Hitbox#intersects(de.uni_kiel.
		 * progOOproject17.model.abs.Hitbox)
		 */
		@Override
		public boolean intersects(Hitbox other) {

			if (other instanceof LineHitbox) {
				LineHitbox hb = ((LineHitbox) other);

				return Line2D.linesIntersect(this.getX(), this.getY(), this.x2, this.x2, hb.getX(), hb.getY(), hb.x2,
						hb.y2);

			}

			return other.intersects(this);
		}

		public void setToPoint(int x2, int y2) {
			this.x2 = x2;
			this.y2 = y2;
		}

		public Point getToPoint() {
			return new Point(x2, y2);
		}

	}

	public static class PointHitbox extends PolygonHitbox {

		public PointHitbox(int x, int y) {
			super(new Point[] { new Point(x, y) });
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see de.uni_kiel.progOOproject17.model.abs.Hitbox#clone()
		 */
		@Override
		public PointHitbox clone() {
			return new PointHitbox(this.getX(), this.getY());
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * de.uni_kiel.progOOproject17.model.abs.Hitbox#intersects(de.uni_kiel.
		 * progOOproject17.model.abs.Hitbox)
		 */
		@Override
		public boolean intersects(Hitbox other) {

			if (other instanceof PointHitbox) {

				return this.getX() == other.x && this.getY() == other.y;

			}
			return other.intersects(this);

		}
	}

	public static class NoHitbox extends Hitbox {

		/**
		 * 
		 */
		public NoHitbox(int x, int y) {
			super(x, y);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see de.uni_kiel.progOOproject17.model.abs.Hitbox#clone()
		 */
		@Override
		public Hitbox clone() {

			return new NoHitbox(getX(), getY());
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * de.uni_kiel.progOOproject17.model.abs.Hitbox#intersects(de.uni_kiel.
		 * progOOproject17.model.abs.Hitbox)
		 */
		@Override
		public boolean intersects(Hitbox other) {
			return false;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * de.uni_kiel.progOOproject17.model.abs.Hitbox#contacts(de.uni_kiel.
		 * progOOproject17.model.abs.Hitbox)
		 */
		@Override
		public boolean contacts(Hitbox hitbox) {

			return false;
		}

	}

	public static class PolygonHitbox extends Hitbox {

		private Point[] points;

		public PolygonHitbox(Point[] points) {
			super(points[0]);
			this.points = points;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see de.uni_kiel.progOOproject17.model.abs.Hitbox#clone()
		 */
		@Override
		public Hitbox clone() {
			return new PolygonHitbox(points);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * de.uni_kiel.progOOproject17.model.abs.Hitbox#intersects(de.uni_kiel.
		 * progOOproject17.model.abs.Hitbox)
		 */
		@Override
		public boolean intersects(Hitbox other) {

			if (other instanceof PolygonHitbox) {
				PolygonHitbox oPoly = (PolygonHitbox) other;
				Point[] oPoints = oPoly.getPoints();

				// FIXME INSIDE CHECK?

				for (int i = 0; i < points.length; i++) {
					for (int j = 0; j < oPoints.length; j++) {

						Point p1 = points[i];
						Point p2 = points[(i - 1 >= 0) ? (i - 1) : (points.length - 1)];

						Point p3 = oPoints[j];
						Point p4 = oPoints[(i - 1 >= 0) ? (i - 1) : (oPoints.length - 1)];

						if (Line2D.linesIntersect(p1.x, p1.y, p2.x, p2.y, p3.x, p3.y, p4.x, p4.y))
							return true;

					}
				}
				
				return false;

			}

			return other.intersects(this); // NOHitbox or Circle or something
											// else

		}

		/**
		 * @return the points
		 */
		public Point[] getPoints() {
			return points;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * de.uni_kiel.progOOproject17.model.abs.Hitbox#contacts(de.uni_kiel.
		 * progOOproject17.model.abs.Hitbox)
		 */
		@Override
		public boolean contacts(Hitbox hitbox) {

			// TODO CONTACTS WITH POLYGONS

			return intersects(hitbox);
		}

	}

}
