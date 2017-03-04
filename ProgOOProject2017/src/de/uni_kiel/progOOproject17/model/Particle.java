package de.uni_kiel.progOOproject17.model;

import de.uni_kiel.progOOproject17.model.abs.GameElement;

public class Particle extends GameElement {

	private int counter;
	private final int dtime;
	private long lasttime;
	private final int max;

	public Particle(String resKey, int x, int y, int w, int h, int dtime, int max) {
		super(resKey, x, y, w, h);
		this.max = max;
		this.dtime = dtime;
		setLayer(PARTICLE_LAYER);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.uni_kiel.progOOproject17.model.abs.GameElement#getResourceKey()
	 */
	@Override
	public String getResourceKey() {

		return super.getResourceKey() + "_" + counter;
	}

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
