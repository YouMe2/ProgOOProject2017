package de.uni_kiel.progOOproject17.model.abs;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.geom.Line2D;
import java.lang.reflect.GenericArrayType;
import java.security.SecurityPermission;

import javax.annotation.Generated;

import de.uni_kiel.progOOproject17.model.SimpleViewable;
import de.uni_kiel.progOOproject17.view.abs.Viewable;

/**
 * @author Yannik Eikmeier
 * @since 14.03.2017
 *
 */
public abstract class Hitbox {

	public static final String LINE_KEY = "line";
	public static final String CIRCLE_KEY = "circle";

	private Point pos;

	private Hitbox(Point position) {
		this.pos = position;
	}

	// PUBLIC --------------------------------------------------

	/**
	 * @return the x
	 */
	public int getX() {
		return pos.x;
	}

	/**
	 * @return the y
	 */
	public int getY() {
		return pos.y;
	}

	/**
	 * @param pos
	 *            the pos to set
	 */
	protected void setLocation(Point pos) {
		this.pos = pos;
	}

	/**
	 * 
	 * @return the {@link Point} holding the Location of this {@link Hitbox}
	 */
	public Point getLocation() {
		return pos;
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
	 * Translates this {@link Hitbox}.
	 * 
	 * @param d
	 */
	public void translate(Distance d) {
		pos.translate(d.x, d.y);
	}

	/**
	 * Determins whether this {@link Hitbox} could intersect with the other
	 * {@link Hitbox}. If this returns false, the two have no chance to
	 * intersect. If true, the two could, but dont have to be intersecting!
	 * 
	 * @param other
	 * @return
	 */
	public boolean intersectsFAST(Hitbox other) {

		return ((other.minX() <= this.minX() && this.minX() <= other.maxX()
				|| other.minX() <= this.maxX() && this.maxX() <= other.maxX())
				&& (other.minY() <= this.minY() && this.minY() <= other.maxY()
						|| other.minY() <= this.maxY() && this.maxY() <= other.maxY()))
				|| ((this.minX() <= other.minX() && other.minX() <= this.maxX()
						|| this.minX() <= other.maxX() && other.maxX() <= this.maxX())
						&& (this.minY() <= other.minY() && other.minY() <= this.maxY()
								|| this.minY() <= other.maxY() && other.maxY() <= this.maxY()));

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public abstract String toString();

	// ABSTRACT -----------------------------------------------------

	public abstract int maxX();

	public abstract int minX();

	public abstract int maxY();

	public abstract int minY();

	/**
	 * return new Point(x, y); }
	 * 
	 * /* (non-Javadoc)
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

	public abstract Viewable[] getDebugViewables();

	// STATIC --------------------------------------------------

	public static class RectHitbox extends PolygonHitbox {

		private final Point ul, ur, dr, dl;

		public RectHitbox(int x, int y, int w, int h) {
			super(new Point(x, y), new Point[] { new Point(0, 0), new Point(w, 0), new Point(w, h), new Point(0, h) });

			ul = getPoints()[0];
			ur = getPoints()[1];
			dr = getPoints()[2];
			dl = getPoints()[3];

		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see de.uni_kiel.progOOproject17.model.abs.Hitbox#clone()
		 */
		@Override
		public RectHitbox clone() {
			return new RectHitbox(this.getX(), this.getY(), getSize().width, getSize().height);
		}

		// /*
		// * (non-Javadoc)
		// *
		// * @see
		// *
		// *
		// de.uni_kiel.progOOproject17.model.abs.Hitbox#intersects(de.uni_kiel.
		// * progOOproject17.model.abs.Hitbox)
		// */
		// @Override
		// public boolean intersects(Hitbox other) {
		//
		// if (other instanceof RectHitbox) {
		//
		// return this.getRectangle().intersects(((RectHitbox)
		// other).getRectangle());
		// }
		//
		// return super.intersects(other);
		// }

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
			return new Rectangle(this.getX(), this.getY(), getSize().width, getSize().height);
		}

		public void setSize(int w, int h) {
			ul.setLocation(0, 0);
			ur.setLocation(w, 0);
			dr.setLocation(w, h);
			dl.setLocation(0, h);
			super.updateMinMax();
		}

		public Dimension getSize() {
			return new Dimension(dr.x, dr.y);
		}

	}

	public static class CircleHitbox extends Hitbox {

		private int r;

		/**
		 * Centered on x, y!
		 * 
		 * @param x
		 * @param y
		 * @param r
		 */
		public CircleHitbox(int x, int y, int r) {
			super(new Point(x, y));
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
			Point center = getCenter();

			if (other instanceof CircleHitbox) {
				CircleHitbox hb = ((CircleHitbox) other);
				Point oCenter = hb.getCenter();
				return this.getDistanceToCenter(oCenter.x, oCenter.y).getLength() <= this.getRadius() + hb.getRadius();

			}

			if (other instanceof PolygonHitbox) {
				PolygonHitbox poly = (PolygonHitbox) other;
				Point[] points = poly.getRealPoints();

				for (int i = 0; i < points.length; i++) {
					Point p1 = points[i];
					Point p2 = points[(i + 1 == points.length) ? 0 : i + 1];
					if (Line2D.ptSegDistSq(p1.x, p1.y, p2.x, p2.y, center.x, center.y) <= r * r)
						return true;

				}
				return false;

			}

			// if (other instanceof LineHitbox) {
			// LineHitbox line = (LineHitbox) other;
			//
			// return Line2D.ptLineDistSq(line.getX(), line.getY(),
			// line.getToPoint().x, line.getToPoint().y, center.x,
			// center.y) <= r * r;
			//
			// }

			if (other instanceof PointHitbox) {
				PointHitbox point = (PointHitbox) other;
				return getDistanceToCenter(point.getX(), point.getY()).getSqLength() <= getRadius() * getRadius();
			}

			System.err.println("Potential loop with circle intersection!!");
			return other.intersects(this);

		}

		protected Distance getDistanceToCenter(int x, int y) {
			Point center = getCenter();
			return new Distance(Math.abs(center.x - x), Math.abs(center.y - y));
		}

		public void setRadius(int r) {
			this.r = r;
		}

		public Point getCenter() {
			return getLocation();
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
		public boolean contacts(Hitbox other) {
			return new CircleHitbox(getCenter().x, getCenter().y, getRadius() + 1).intersects(other);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see de.uni_kiel.progOOproject17.model.abs.Hitbox#maxX()
		 */
		@Override
		public int maxX() {
			return getCenter().x + r;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see de.uni_kiel.progOOproject17.model.abs.Hitbox#minX()
		 */
		@Override
		public int minX() {

			return getCenter().x - r;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see de.uni_kiel.progOOproject17.model.abs.Hitbox#maxY()
		 */
		@Override
		public int maxY() {

			return getCenter().y + r;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see de.uni_kiel.progOOproject17.model.abs.Hitbox#minY()
		 */
		@Override
		public int minY() {
			return getCenter().y - r;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see de.uni_kiel.progOOproject17.model.abs.Hitbox#getDebugViewables()
		 */
		@Override
		public Viewable[] getDebugViewables() {

			Point center = getCenter();
			int r = getRadius();
			return new Viewable[] {
					new SimpleViewable(Viewable.DEBUGKEY_PREFIX + CIRCLE_KEY, center.x - r, center.y - r, r * 2, r * 2,
							Viewable.DEBUG_LAYER),
					new SimpleViewable(Viewable.DEBUGKEY_PREFIX + LINE_KEY, center.x, center.y, center.x, center.y,
							Viewable.DEBUG_LAYER) };
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see de.uni_kiel.progOOproject17.model.abs.Hitbox#toString()
		 */
		@Override
		public String toString() {
			return "CircleHitbox: C(" + getX() + ", " + getY() + "), R = " + getRadius();
		}

	}

	public static class LineHitbox extends PolygonHitbox {

		private final Point to;

		public LineHitbox(int x1, int y1, int x2, int y2) {
			super(new Point(x1, y1), new Point[] { new Point(0, 0), new Point(x2 - x1, y2 - y1) });
			to = getPoints()[1];

		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see de.uni_kiel.progOOproject17.model.abs.Hitbox#clone()
		 */
		@Override
		public LineHitbox clone() {
			return new LineHitbox(this.getX(), this.getY(), getToPoint().x + getX(), getToPoint().y + getY());
		}

		public void setToPoint(int x2, int y2) {
			// relative to anchor!
			to.setLocation(x2 - getX(), y2 - getY());
			super.updateMinMax();
		}

		public Point getToPoint() {
			return new Point(to);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * de.uni_kiel.progOOproject17.model.abs.Hitbox.PolygonHitbox#toString()
		 */
		@Override
		public String toString() {
			return "LineHitbox: (" + getX() + ", " + getY() + ") -> (" + (to.x + getX()) + ", " + (to.y + getY()) + ")";
		}

	}

	public static class PointHitbox extends Hitbox {

		public PointHitbox(int x, int y) {
			super(new Point(x, y));
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
				return this.getX() == other.getX() && this.getY() == other.getY();

			} else if (other instanceof PolygonHitbox) {
				return ((PolygonHitbox) other).isInside(this.getLocation());

			} else {
				return other.intersects(this); // No hitbox or what ever
			}

		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see de.uni_kiel.progOOproject17.model.abs.Hitbox#toString()
		 */
		@Override
		public String toString() {

			return "PointHitbox: " + getX() + ", " + getY();

		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see de.uni_kiel.progOOproject17.model.abs.Hitbox#maxX()
		 */
		@Override
		public int maxX() {
			return getX();
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see de.uni_kiel.progOOproject17.model.abs.Hitbox#minX()
		 */
		@Override
		public int minX() {
			return getX();
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see de.uni_kiel.progOOproject17.model.abs.Hitbox#maxY()
		 */
		@Override
		public int maxY() {
			return getY();
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see de.uni_kiel.progOOproject17.model.abs.Hitbox#minY()
		 */
		@Override
		public int minY() {
			return getY();
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * de.uni_kiel.progOOproject17.model.abs.Hitbox#contacts(de.uni_kiel.
		 * progOOproject17.model.abs.Hitbox)
		 */
		@Override
		public boolean contacts(Hitbox other) {
			return new CircleHitbox(getX(), getY(), 1).intersects(other);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see de.uni_kiel.progOOproject17.model.abs.Hitbox#getDebugViewables()
		 */
		@Override
		public Viewable[] getDebugViewables() {
			return new Viewable[] { new SimpleViewable(Viewable.DEBUGKEY_PREFIX + Hitbox.LINE_KEY, getX(), getY(),
					getX(), getY(), Viewable.DEBUG_LAYER) };
		}
	}

	public static class NoHitbox extends Hitbox {

		/**
		 * 
		 */
		public NoHitbox(int x, int y) {
			super(new Point(x, y));
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

		/*
		 * (non-Javadoc)
		 * 
		 * @see de.uni_kiel.progOOproject17.model.abs.Hitbox#maxX()
		 */
		@Override
		public int maxX() {
			return getX();
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see de.uni_kiel.progOOproject17.model.abs.Hitbox#minX()
		 */
		@Override
		public int minX() {
			return getX();
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see de.uni_kiel.progOOproject17.model.abs.Hitbox#maxY()
		 */
		@Override
		public int maxY() {
			return getY();
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see de.uni_kiel.progOOproject17.model.abs.Hitbox#minY()
		 */
		@Override
		public int minY() {
			return getY();
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see de.uni_kiel.progOOproject17.model.abs.Hitbox#getDebugViewables()
		 */
		@Override
		public Viewable[] getDebugViewables() {
			return new Viewable[0];
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see de.uni_kiel.progOOproject17.model.abs.Hitbox#toString()
		 */
		@Override
		public String toString() {
			return "NoHitbox: (" + getX() + ", " + getY() + ")";
		}

	}

	public static class PolygonHitbox extends Hitbox {

		private final Point[] points;
		private int maxDX;
		private int minDX;
		private int maxDY;
		private int minDY;

		/**
		 * Constructs a new {@link PolygonHitbox} that is anchored at the given
		 * anchor point and has the shape given by the points. Has to have at
		 * least 2 points to from a line.
		 * 
		 * @param anchor
		 *            the absolute position of the anchor point
		 * @param points
		 *            the positions of the corners of the polygon relative to
		 *            the anchor.
		 */
		public PolygonHitbox(Point anchor, Point[] points) {
			super(anchor);
			if (points.length < 2)
				throw new IllegalArgumentException("there has to be at least two point is a polygon! " + points.length);
			this.points = points;

			updateMinMax();

		}

		/**
		 * @return
		 */
		public Point[] getRealPoints() {
			Point[] rps = new Point[points.length];
			for (int i = 0; i < rps.length; i++) {
				rps[i] = new Point(points[i]);
				rps[i].translate(getX(), getY());
			}
			return rps;
		}

		/**
		 * 
		 */
		public void updateMinMax() {
			minDX = maxDX = points[0].x;
			minDY = maxDY = points[0].y;
			for (Point p : points) {

				if (p.x < minDX)
					minDX = p.x;
				if (p.y < minDY)
					minDY = p.y;

				if (p.x > maxDX)
					maxDX = p.x;
				if (p.y > maxDY)
					maxDY = p.y;

			}

		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see de.uni_kiel.progOOproject17.model.abs.Hitbox#clone()
		 */
		@Override
		public Hitbox clone() {

			Point[] ps = new Point[points.length];
			for (int i = 0; i < ps.length; i++) {
				ps[i] = new Point(points[i]);
			}

			return new PolygonHitbox(new Point(getLocation()), ps);
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

			// if (other instanceof LineHitbox) {
			// LineHitbox line = (LineHitbox) other;
			// Point p3 = line.getLocation();
			// Point p4 = line.getToPoint();
			//
			// if (isInside(p3) || isInside(p4)) {
			// return true;
			// }
			//
			// Point[] thisPoints = this.getRealPoints();
			// for (int i = 0; i < thisPoints.length; i++) {
			//
			// Point p1 = new Point(thisPoints[i]);
			// Point p2 = thisPoints[(i + 1 == thisPoints.length) ? 0 : i + 1];
			//
			// if (Line2D.linesIntersect(p1.x, p1.y, p2.x, p2.y, p3.x, p3.y,
			// p4.x, p4.y))
			// return true;
			//
			// }
			//
			// return false;
			//
			// }

			if (other instanceof PolygonHitbox) {
				PolygonHitbox otherPoly = (PolygonHitbox) other;
				Point[] otherPoints = otherPoly.getRealPoints();
				Point[] thisPoints = this.getRealPoints();

				for (int j = 0; j < otherPoints.length; j++) {

					Point p3 = otherPoints[j];
					Point p4 = otherPoints[(j + 1 == otherPoints.length) ? 0 : j + 1];

					if (isInside(p3) || isInside(p4)) {
						return true;
					}

					for (int i = 0; i < thisPoints.length; i++) {

						Point p1 = new Point(thisPoints[i]);
						Point p2 = thisPoints[(i + 1 == thisPoints.length) ? 0 : i + 1];

						if (Line2D.linesIntersect(p1.x, p1.y, p2.x, p2.y, p3.x, p3.y, p4.x, p4.y))
							return true;

					}
				}

				return false;

			}
			// return false;
			return other.intersects(this); // NOHitbox or Circle or something
											// else

		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * de.uni_kiel.progOOproject17.model.abs.Hitbox#contacts(de.uni_kiel.
		 * progOOproject17.model.abs.Hitbox)
		 */
		@Override
		public boolean contacts(Hitbox other) {

			// TODO CONTACTS WITH POLYGONS

			Hitbox testBox = this.getCloneTranslate(new Distance(0, 1));
			if (testBox.intersects(other))
				return true;
			testBox.translate(new Distance(0, -2));
			if (testBox.intersects(other))
				return true;
			testBox.translate(new Distance(1, 1));
			if (testBox.intersects(other))
				return true;
			testBox.translate(new Distance(-2, 0));
			if (testBox.intersects(other))
				return true;
			return false;

			// return other.intersects(testBox)
			// || other.intersects(this.getCloneTranslate(new Distance(0, -1)))
			// || other.intersects(this.getCloneTranslate(new Distance(1, 0)))
			// || other.intersects(this.getCloneTranslate(new Distance(-1, 0)));
			// return false;
		}

		public boolean isInside(Point p) {

			Point[] thisPoints = this.getRealPoints();

			if (thisPoints.length == 2) {
				// TODO Point on line?
				return Line2D.ptSegDist(getX() + points[0].x, getY() + points[0].y, getX() + points[1].x,
						getY() + points[1].y, p.x, p.y) <= 0.4;
			}

			assert thisPoints.length > 2;
			// es gibt eine fläche!

			int hitCounter = 0;
			for (int i = 0; i < thisPoints.length; i++) {

				Point edgeP1 = thisPoints[i];
				Point edgeP2 = thisPoints[i + 1 == thisPoints.length ? 0 : i + 1];

				if (Line2D.linesIntersect(p.x, p.y, maxX() + 1, p.y + 1, edgeP1.x, edgeP1.y, edgeP2.x, edgeP2.y)) {
					hitCounter++;
				}

			}

			return hitCounter % 2 == 1;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see de.uni_kiel.progOOproject17.model.abs.Hitbox#maxX()
		 */
		@Override
		public int maxX() {
			return getX() + maxDX;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see de.uni_kiel.progOOproject17.model.abs.Hitbox#minX()
		 */
		@Override
		public int minX() {
			return getX() + minDX;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see de.uni_kiel.progOOproject17.model.abs.Hitbox#maxY()
		 */
		@Override
		public int maxY() {
			return getY() + maxDY;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see de.uni_kiel.progOOproject17.model.abs.Hitbox#minY()
		 */
		@Override
		public int minY() {
			return getY() + minDY;
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
		 * @see de.uni_kiel.progOOproject17.model.abs.Hitbox#getDebugViewable()
		 */
		@Override
		public Viewable[] getDebugViewables() {

			Point[] thisPoints = this.getRealPoints();

			Viewable[] views = new Viewable[thisPoints.length];

			for (int i = 0; i < views.length; i++) {
				Point edgeP1 = new Point(thisPoints[i]);
				Point edgeP2 = new Point(thisPoints[(i + 1) == thisPoints.length ? 0 : i + 1]);

				views[i] = new SimpleViewable(Viewable.DEBUGKEY_PREFIX + Hitbox.LINE_KEY, edgeP1.x, edgeP1.y, edgeP2.x,
						edgeP2.y, Viewable.DEBUG_LAYER);

				// System.out.println(edgeP1 + " " +edgeP2);
			}

			return views;

		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see de.uni_kiel.progOOproject17.model.abs.Hitbox#toString()
		 */
		@Override
		public String toString() {

			StringBuilder res = new StringBuilder("PolygonHB at: (" + getX() + ", " + getY() + ") with: ");
			for (Point p : points) {
				res.append("(" + p.x + ", " + p.y + "), ");
			}
			return res.toString();
		}
	}

}
