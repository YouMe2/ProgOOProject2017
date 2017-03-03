package de.uni_kiel.progOOproject17.model;

import java.awt.Rectangle;

import de.uni_kiel.progOOproject17.model.abs.GameComponent;
import de.uni_kiel.progOOproject17.view.abs.Viewable;

public class Background extends GameComponent implements Viewable {

	private String resKey;
	private int layer = BG_LAYER;

	public Background(String resKey, int x, int y, int w, int h) {
		super(x, y, w, h);
		this.resKey = resKey;
	}

	@Override
	public void tick(long timestamp) {
		// TODOanimatedBG?
	}

	@Override
	public String getResourceKey() {
		return resKey;
	}
	
	/**
	 * 
	 */
	public void setResKey(String resKey) {
		this.resKey = resKey;
	}

	@Override
	public Rectangle getViewRect() {
		return getBoundingRect();
	}

	@Override
	public int getLayer() {
		return layer;
	}
	
	/**
	 * @param layer the layer to set
	 */
	public void setLayer(int layer) {
		this.layer = layer;
	}

}
