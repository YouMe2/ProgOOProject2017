package de.uni_kiel.progOOproject17.model;

import de.uni_kiel.progOOproject17.model.abs.GameElement;

/**
 * This class represents a {@link GameElement} that serves as a animated
 * {@link Particle}.
 */
public class Particle extends GameElement {

	private int counter;
	private final int dtime;
	private long lasttime;
	private final int max;

	/**
	 * Constructs a new {@link Particle}. That after its been activated will be
	 * shown for max*dtime.
	 * 
	 * @param resKey
	 *            the base resource key
	 * @param x
	 *            the x coord
	 * @param y
	 *            the y coord
	 * @param w
	 *            the width
	 * @param h
	 *            the height
	 * @param dtime
	 *            the time between the animations
	 * @param lenghth
	 *            the length of the counter
	 */
	public Particle(String resKey, int x, int y, int w, int h, int dtime, int lenghth) {
		super(resKey, x, y, w, h);
		this.max = lenghth;
		this.dtime = dtime;
		setLayer(PARTICLE_LAYER);
	}

	/**
	 *
	 *
	 * @return the resource key ending on "_counter" where counter is an int
	 *         from 0 to length.
	 * @see de.uni_kiel.progOOproject17.model.abs.GameElement#getResourceKey()
	 */
	@Override
	public String getResourceKey() {

		return super.getResourceKey() + "_" + counter;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.uni_kiel.progOOproject17.model.abs.Ticked#tick(long)
	 */
	@Override
	public void tick(long timestamp) {
		// init
		if (lasttime == 0)
			lasttime = timestamp;

		// next
		if (timestamp - lasttime > dtime)
			if (counter < max - 1) {
				lasttime = timestamp;
				counter++;

			} else if (counter == max - 1)
				destroy();

	}

}
