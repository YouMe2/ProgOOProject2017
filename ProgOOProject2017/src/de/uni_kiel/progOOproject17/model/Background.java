package de.uni_kiel.progOOproject17.model;

import de.uni_kiel.progOOproject17.model.abs.Environment;
import de.uni_kiel.progOOproject17.model.abs.GameElement;

public class Background extends GameElement {

	public Background(String resKey, int x, int y, int w, int h, Environment environment, CreationHelper creatHelp) {
		super(resKey, x, y, w, h, environment, creatHelp);
		setLayer(BG_LAYER);
	}

	@Override
	public void tick(long timestamp) {
		// TODO animatedBG?
	}

}
