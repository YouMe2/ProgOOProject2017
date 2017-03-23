package de.uni_kiel.progOOproject17.model.abs;

/**
 * This class is a simple mutable util class which represents a vectorial
 * {@link Distance} in x and y components.
 * 
 */
public class Distance {

	public int x, y;

	/**
	 * Constructs a new {@link Distance} with length both components being 0.
	 */
	public Distance() {
		this(0, 0);
	}

	/**
	 * Constructs a new {@link Distance} which will be an exact copy of the
	 * given {@link Distance} d.
	 * 
	 * @param d
	 *            the {@link Distance} to copy
	 */
	public Distance(Distance d) {
		this(d.x, d.y);
	}

	/**
	 * Constructs a new {@link Distance} from the two given components x and y,
	 * 
	 * @param x
	 *            the x component
	 * @param y
	 *            the y component
	 */
	public Distance(int x, int y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * @return the absolute length of the {@link Distance}
	 * @see #getSqLength()
	 */
	public double getLength() {
		return Math.sqrt(getSqLength());
	}

	/**
	 * @return the square of the absolute length of the {@link Distance}
	 */
	public int getSqLength() {
		return dotProduct(this);
	}

	/**
	 * Multiplies this {@link Distance} with the given factor.
	 * 
	 * @param factor
	 */
	public void scale(double factor) {
		x *= factor;
		y *= factor;
	}

	/**
	 * Multiplies this {@link Distance} componentwise with the given
	 * {@link Distance} dis.
	 * 
	 * @param dis
	 */
	public void scale(Distance dis) {
		x *= dis.x;
		y *= dis.y;
	}
	
	/**
	 * Calcualtes the dot product of the two {@link Distance}s as vectors.
	 * @param dis
	 * @return the dot product of this and dis
	 */
	public int dotProduct(Distance dis) {
		return x * dis.x + y * dis.y;
	}

	/**
	 * Adds the given {@link Distance} d to this {@link Distance}
	 * 
	 * @param d
	 */
	public void add(Distance d) {
		x += d.x;
		y += d.y;
	}

	/**
	 * Returns a new {@link Distance} with the components being only the sign of
	 * this {@link Distance}. For Example if this {@link Distance} was (-42,11)
	 * the returning {@link Distance} would be (-1,1).
	 * 
	 * @return the signs of this {@link Distance}
	 */
	public Distance getSignDistance() {
		return new Distance((int) Math.signum(x), (int) Math.signum(y));
	}

	/**
	 * Returns a new {@link Distance} with the components being only the
	 * absolute value of this {@link Distance}. For Example if this {@link Distance} was (-42,11)
	 * the returning {@link Distance} would be (42,11).
	 * 
	 * @return the absoule {@link Distance} (NOT the length)
	 */
	public Distance getAbsDistance() {
		return new Distance(Math.abs(x), Math.abs(y));
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		
		return "("+x+", "+y+")";
	}

}
