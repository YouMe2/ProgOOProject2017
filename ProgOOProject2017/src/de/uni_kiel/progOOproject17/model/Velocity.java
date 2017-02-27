package de.uni_kiel.progOOproject17.model;

public class Velocity {

	public int speedX, speedY;

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

	// TODO advance functionality of Veclocity class?

}
