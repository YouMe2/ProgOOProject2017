package de.uni_kiel.progOOproject17.model;

import java.awt.Dimension;

public class Velocity {

	public int speedX, speedY;

	public Velocity() {
		this(0, 0);
	}

	public Velocity(Dimension dim) {
		this(dim.width, dim.height);
	}

	public Velocity(int speedX, int speedY) {
		this.speedX = speedX;
		this.speedY = speedY;
	}

	public double speed() {
		return Math.sqrt(speedX * speedX + speedY * speedY);
	}

	public void accelerate(double factor) {
		// TODO acceleration
	}

	public Dimension getDistancePerTick() {
		return new Dimension(speedX, speedY);
	}

	// TODO advance functionality of Veclocity class?

}
