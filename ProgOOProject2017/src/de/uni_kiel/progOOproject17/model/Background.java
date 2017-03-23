package de.uni_kiel.progOOproject17.model;

import de.uni_kiel.progOOproject17.model.abs.GameElement;

/**
 * This class represents a {@link GameElement} that serves as a simple
 * Background.
 *
 */
public class Background extends GameElement {

	/**
	 * Constructs a new {@link Background}.
	 * Which will not be active until it is activated by the {@link #activate(Environment, CreationHelper)} method.
	 * 
	 * @param resKey the resource Key for this Background
	 * @param x the x coord 
	 * @param y the y coord 
	 * @param w the width
	 * @param h the height
	 */
	public Background(String resKey, int x, int y, int w, int h) {
		super(resKey, x, y, w, h, BG_LAYER);
	}

	@Override
	public void tick(long timestamp) {
	}

}
