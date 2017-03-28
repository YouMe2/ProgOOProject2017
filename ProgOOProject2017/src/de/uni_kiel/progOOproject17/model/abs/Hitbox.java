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
	private boolean movementRestricting = true;

	private Hitbox(Point p) {
		this.pos = p;
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
	protected void setPos(Point pos) {
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
	 * @param movementRestricting
	 *            whether this {@link Hitbox} is restrictin Movent of other
	 *            Hitboxes
	 */
	public void setMovementRestricting(boolean movementRestricting) {
		this.movementRestricting = movementRestricting;
	}

	/**
	 * @return whether this {@link Hitbox} is restrictin Movent of other
	 *         Hitboxes
	 */
	public boolean isMovementRestricting() {
		return movementRestricting;
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
		setLocation(getX() + d.x, getY() + d.y);
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
		return (other.minX() >= this.minX() && other.minX() <= this.maxX()
				|| other.maxX() >= this.minX() && other.maxX() <= this.maxX())
				&& (other.minY() >= this.minY() && other.minY() <= this.maxY()
						|| other.maxY() >= this.minY() && other.maxY() <= this.maxY());

	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public abstract String toString();
	
	// ABSTRACT -----------------------------------------------------

	/**
	 * sets the Location of this {@link Hitbox} to (x,y)
	 * 
	 * @param x
	 * @param y
	 */
	public abstract void setLocation(int x, int y);

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

		private final Point ur, dr, dl;

		public RectHitbox(int x, int y, int w, int h) {
			super(new Point[] { new Point(x, y), new Point(x + w, y), new Point(x + w, y + h), new Point(x, y + h) });

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

//		/*
//		 * (non-Javadoc)
//		 * 
//		 * @see
//		 * de.uni_kiel.progOOproject17.model.abs.Hitbox#intersects(de.uni_kiel.
//		 * progOOproject17.model.abs.Hitbox)
//		 */
//		@Override
//		public boolean intersects(Hitbox other) {
//
//			if (other instanceof RectHitbox) {
//
//				return this.getRectangle().intersects(((RectHitbox) other).getRectangle());
//			}
//
//			return super.intersects(this);
//		}

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
			ur.setLocation(getX() + w, getY());
			dr.setLocation(getX() + w, getY() + h);
			dl.setLocation(getX(), getY() + h);
			super.updateMinMaxXY();
		}

		public Dimension getSize() {
			return new Dimension(ur.x - getX(), dl.y - getY());
		}

	}

	public static class CircleHitbox extends Hitbox {

		private int r;

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

			if (other instanceof CircleHitbox) {
				CircleHitbox hb = ((CircleHitbox) other);
				Point oCenter = hb.getCenter();
				return this.getDistanceToCenter(oCenter.x, oCenter.y).getLength() < this.getRadius() + hb.getRadius();

			}

			if (other instanceof PolygonHitbox) {
				PolygonHitbox oPoly = (PolygonHitbox) other;
				Point[] points = oPoly.getPoints();

				for (int i = 0; i < points.length; i++) {
					Point p1 = points[i];
					Point p2 = points[(i - 1 >= 0) ? (i - 1) : (points.length - 1)];
					Point center = getCenter();
					if (Line2D.ptSegDistSq(p1.x, p1.y, p2.x, p2.y, center.x, center.y) < r * r)
						return true;

				}
				return false;

			}

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
			return new Point(getX() + r, getY() + r);
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

		/*
		 * (non-Javadoc)
		 * 
		 * @see de.uni_kiel.progOOproject17.model.abs.Hitbox#setLocation(int,
		 * int)
		 */
		@Override
		public void setLocation(int x, int y) {
			getLocation().setLocation(x, y);

		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see de.uni_kiel.progOOproject17.model.abs.Hitbox#maxX()
		 */
		@Override
		public int maxX() {
			return getX() + 2 * r;
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

			return getY() + 2 * r;
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

			return new Viewable[] { new SimpleViewable(Viewable.DEBUGKEY_PREFIX + CIRCLE_KEY, getX(), getY(),
					getRadius() * 2, getRadius() * 2, Viewable.DEBUG_LAYER) };
		}

		/* (non-Javadoc)
		 * @see de.uni_kiel.progOOproject17.model.abs.Hitbox#toString()
		 */
		@Override
		public String toString() {
			return "CircleHitbox: C("+getX()+", "+getY()+"), R = "+getRadius();
		}

	}

	public static class LineHitbox extends PolygonHitbox {

		private final Point to;

		public LineHitbox(int x1, int y1, int x2, int y2) {
			super(new Point[] { new Point(x1, y1), new Point(x2, y2) });
			to = getPoints()[1];
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see de.uni_kiel.progOOproject17.model.abs.Hitbox#clone()
		 */
		@Override
		public LineHitbox clone() {
			return new LineHitbox(this.getX(), this.getY(), getToPoint().x, getToPoint().y);
		}

//		/*
//		 * (non-Javadoc)
//		 * 
//		 * @see
//		 * de.uni_kiel.progOOproject17.model.abs.Hitbox#intersects(de.uni_kiel.
//		 * progOOproject17.model.abs.Hitbox)
//		 */
//		@Override
//		public boolean intersects(Hitbox other) {
//
//			if (other instanceof LineHitbox) {
//				LineHitbox hb = ((LineHitbox) other);
//				return Line2D.linesIntersect(this.getX(), this.getY(), getToPoint().x, getToPoint().y, hb.getX(),
//						hb.getY(), hb.getToPoint().x, hb.getToPoint().y);
//
//			}
//
//			return super.intersects(this);
//		}

		public void setToPoint(int x2, int y2) {
			to.setLocation(x2, y2);
			super.updateMinMaxXY();
		}

		public Point getToPoint() {
			return new Point(to);
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

//		/*
//		 * (non-Javadoc)
//		 * 
//		 * @see
//		 * de.uni_kiel.progOOproject17.model.abs.Hitbox#intersects(de.uni_kiel.
//		 * progOOproject17.model.abs.Hitbox)
//		 */
//		@Override
//		public boolean intersects(Hitbox other) {
//
//			if (other instanceof PointHitbox) {
//
//				return this.getX() == other.getX() && this.getY() == other.getY();
//
//			}
//			return super.intersects(this);
//
//		}
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
		 * @see de.uni_kiel.progOOproject17.model.abs.Hitbox#setLocation(int,
		 * int)
		 */
		@Override
		public void setLocation(int x, int y) {
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

		/* (non-Javadoc)
		 * @see de.uni_kiel.progOOproject17.model.abs.Hitbox#toString()
		 */
		@Override
		public String toString() {
			return "NoHitbox: ("+getX()+", "+getY()+")";
		}

	}

	public static class PolygonHitbox extends Hitbox {

		private final Point[] points;

		private int maxX, minX, maxY, minY;

		public PolygonHitbox(Point[] points) {
			super(points[0]);
			assert points.length >= 1;
			this.points = points;
//			System.out.println(this);
			updateMinMaxXY();

			// for (Point p : points) {
			// System.out.print(p +" ");
			// }

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
			
			return new PolygonHitbox(ps);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see de.uni_kiel.progOOproject17.model.abs.Hitbox#setLocation(int,
		 * int)
		 */
		@Override
		public void setLocation(int x, int y) {
			Distance movedDistance = new Distance(x - getX(), y - getY());
			for (int i = 0; i < points.length; i++) {
				points[i].translate(movedDistance.x, movedDistance.y);
			}
			assert getLocation() == points[0];
			updateMinMaxXY();

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

				if (oPoints.length < 2) {
					assert oPoints.length == 1;
					return isInside(oPoints[0]);
				}
				
				if (points.length < 2) {
					assert points.length == 1;
					return oPoly.isInside(points[0]);
				}

				for (int j = 0; j < oPoints.length; j++) {
					if (isInside(oPoints[j])) {
						return true;
					}
					for (int i = 0; i < points.length; i++) {

						Point p1 = points[i];
						Point p2 = points[(i - 1 >= 0) ? (i - 1) : (points.length - 1)];

						Point p3 = oPoints[j];
						Point p4 = oPoints[(j - 1 >= 0) ? (j - 1) : (oPoints.length - 1)];

						if (Line2D.linesIntersect(p1.x, p1.y, p2.x, p2.y, p3.x, p3.y, p4.x, p4.y))
							return true;

					}
				}

				return false;

			}

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
		public boolean contacts(Hitbox hitbox) {

			// TODO CONTACTS WITH POLYGONS

			return intersects(this.getCloneTranslate(new Distance(0, 1)))
					|| intersects(this.getCloneTranslate(new Distance(0, -1)))
					|| intersects(this.getCloneTranslate(new Distance(1, 0)))
					|| intersects(this.getCloneTranslate(new Distance(-1, 0)));
//			return false;
		}

		public boolean isInside(Point p) {

			if (points.length < 2) {
				assert points.length == 1;
				return p.equals(getLocation());
			}

			assert points.length >= 2;
			// es gibt kanten!

			int hitCounter = 0;

			for (int i = 0; i < points.length; i++) {

				Point edgeP1 = points[i];
				Point edgeP2 = points[i + 1 == points.length ? 0 : i + 1];

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
			return maxX;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see de.uni_kiel.progOOproject17.model.abs.Hitbox#minX()
		 */
		@Override
		public int minX() {
			return minX;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see de.uni_kiel.progOOproject17.model.abs.Hitbox#maxY()
		 */
		@Override
		public int maxY() {
			return maxY;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see de.uni_kiel.progOOproject17.model.abs.Hitbox#minY()
		 */
		@Override
		public int minY() {
			return minY;
		}

		/**
		 * @return the points
		 */
		public Point[] getPoints() {
			return points;
		}

		/**
		 * 
		 */
		private void updateMinMaxXY() {
			minX = maxX = getX();
			minY = maxY = getY();
			for (Point p : points) {

				if (p.x < minX)
					minX = p.x;
				if (p.y < minY)
					minY = p.y;

				if (p.x > maxX)
					maxX = p.x;
				if (p.y > maxY)
					maxY = p.y;

			}

		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see de.uni_kiel.progOOproject17.model.abs.Hitbox#getDebugViewable()
		 */
		@Override
		public Viewable[] getDebugViewables() {

			Viewable[] views = new Viewable[points.length];

			for (int i = 0; i < views.length; i++) {
				Point edgeP1 = points[i];
				Point edgeP2 = points[i + 1 == points.length ? 0 : i + 1];

				views[i] = new SimpleViewable(Viewable.DEBUGKEY_PREFIX + Hitbox.LINE_KEY, edgeP1.x, edgeP1.y,
						edgeP2.x - edgeP1.x, edgeP2.y - edgeP1.y, Viewable.DEBUG_LAYER);

				// System.out.println(edgeP1 + " " +edgeP2);
			}

			return views;

		}

		/* (non-Javadoc)
		 * @see de.uni_kiel.progOOproject17.model.abs.Hitbox#toString()
		 */
		@Override
		public String toString() {
			
			StringBuilder res = new StringBuilder("PolygonHB: ");
			for (Point p : points) {
				res.append("("+p.x+", "+p.y+"), ");
			}		
			return res.toString();
		}
	}

}
