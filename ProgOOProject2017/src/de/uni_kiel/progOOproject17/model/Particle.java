package de.uni_kiel.progOOproject17.model;

import java.security.Key;

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
	private final String resKey;
	
	private Key key = new Key() {
		@Override
		public String getText() {
			return resKey + "_" + counter;
		}
		
	};

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
		super(resKey, x, y, w, h, PARTICLE_LAYER);
		this.resKey = resKey;
		this.max = lenghth;
		this.dtime = dtime;
	}

	@Override
	public Key getContentKey() {
		return key;
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
