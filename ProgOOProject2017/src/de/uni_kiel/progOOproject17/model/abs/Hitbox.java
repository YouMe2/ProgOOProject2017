package de.uni_kiel.progOOproject17.model.abs;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.geom.Line2D;

import javax.annotation.Generated;

/**
 * @author Yannik Eikmeier
 * @since 14.03.2017
 *
 */
public abstract class Hitbox {

	private int x, y;
	private boolean movementRestricting = true;

	private Hitbox(int x, int y) {
		this.x = x;
		this.y = y;
	}

	// PUBLIC --------------------------------------------------

	/**
	 * @return the x
	 */
	public int getX() {
		return x;
	}

	protected void setX(int x) {
		this.x = x;
	}

	/**
	 * @return the y
	 */
	public int getY() {
		return y;
	}

	/**
	 * @param y
	 *            the y to set
	 */
	protected void setY(int y) {
		this.y = y;
	}

	/**
	 * 
	 * @return a new {@link Point} holding the Location of this {@link Hitbox}
	 */
	public Point getLocation() {
		return new Point(x, y);
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
			return new Rectangle(this.getX(), this.getY(), getSize().width, getSize().height);
		}

		public void setSize(int w, int h) {
			ur.setLocation(getX()+w, getY());
			dr.setLocation(getX()+w, getY()+h);
			dl.setLocation(getX(), getY()+h);
			super.updateMinMax();
		}

		public Dimension getSize() {
			return new Dimension(ur.x-getX(), dl.y-getY());
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
			setX(x);
			setY(y);

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
				return Line2D.linesIntersect(this.getX(), this.getY(), getToPoint().x, getToPoint().y, hb.getX(), hb.getY(), hb.getToPoint().x,
						hb.getToPoint().y);

			}

			return other.intersects(this);
		}

		public void setToPoint(int x2, int y2) {
			to.setLocation(x2, y2);
			super.updateMinMax();
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

	}

	public static class PolygonHitbox extends Hitbox {

		private Point[] points;

		private int maxX, minX, maxY, minY;

		public PolygonHitbox(Point[] points) {
			super(points[0].x, points[0].y);
			assert points.length >= 1;
			this.points = points;

			updateMinMax();

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
		 * @see de.uni_kiel.progOOproject17.model.abs.Hitbox#setLocation(int,
		 * int)
		 */
		@Override
		public void setLocation(int x, int y) {
			Distance movedDistance = new Distance(x - getX(), y - getY());
			for (Point p : points) {
				p.move(movedDistance.x, movedDistance.y);
			}
			this.setX(x);
			this.setY(y);

			minX = maxX = getX();
			minY = maxY = getY();
			updateMinMax();

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

				for (int j = 0; j < oPoints.length; j++) {
					if (isInside(oPoints[j])) {
						return true;
					}
					for (int i = 0; i < points.length; i++) {

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
		private void updateMinMax() {
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
	}

}
