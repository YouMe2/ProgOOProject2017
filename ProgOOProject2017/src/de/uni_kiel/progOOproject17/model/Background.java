package de.uni_kiel.progOOproject17.model;

import de.uni_kiel.progOOproject17.model.abs.GameElement;

public class Background extends GameElement {

	public Background(String resKey, int x, int y, int w, int h) {
		super(resKey, x, y, w, h);
		setLayer(BG_LAYER);
	}

	@Override
	public void tick(long timestamp) {
	}

}
