package de.uni_kiel.progOOproject17.model;

/**
 * @author Yannik Eikmeier
 * @since 01.03.2017
 *
 * //TODO may be make Distance Immutable?
 *
 */
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
	return (x * x + y * y);
    }

    public void multiply(double factor) {
	this.x *= factor;
	this.y *= factor;
    }
    
    public void multiply(Distance dis) {
	this.x *= dis.x;
	this.y *= dis.y;
    }

    public void add(Distance d) {
	this.x += d.x;
	this.y += d.y;

    }
    
    public Distance getSignDistance() {
	return new Distance((int)Math.signum(x), (int)Math.signum(y));
    }
    
    public Distance getAbsDistance() {
	return new Distance(Math.abs(x), Math.abs(y));
    }

}
