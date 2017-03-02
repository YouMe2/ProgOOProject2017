package de.uni_kiel.progOOproject17.model;

public class Distance {

	public int x, y;

	public Distance() {
		this(0, 0);
	}

	public Distance(Distance d) {
		this(d.x, d.y);
	}

	public Distance(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public double getLength() {
		return Math.sqrt(getSqLenghth());
	}

	public int getSqLenghth() {
		return x * x + y * y;
	}

	public void multiply(double factor) {
		x *= factor;
		y *= factor;
	}

	public void multiply(Distance dis) {
		x *= dis.x;
		y *= dis.y;
	}

	public void add(Distance d) {
		x += d.x;
		y += d.y;

	}

	public Distance getSignDistance() {
		return new Distance((int) Math.signum(x), (int) Math.signum(y));
	}

	public Distance getAbsDistance() {
		return new Distance(Math.abs(x), Math.abs(y));
	}

}
