/**
 * 
 */
package de.uni_kiel.progOOproject17.model.abs;

import java.awt.Rectangle;

import de.uni_kiel.progOOproject17.model.CreationHelper;
import de.uni_kiel.progOOproject17.view.abs.Viewable;

/**
 * @author Yannik Eikmeier
 * @since 03.03.2017
 *
 */
public abstract class GameElement extends GameComponent implements Viewable, Destroyable {


	private boolean alive = true;

	
	private String resKey;
	private int layer;

	public final Environment environment;
	public final CreationHelper creatHelp;
	

	/**
	 * 
	 */
	public GameElement(String resKey, int x, int y, int w, int h, Environment environment, CreationHelper creatHelp) {
		super(x, y, w, h);
		this.environment = environment;
		this.creatHelp = creatHelp;
		setResKey(resKey);
		setLayer(0);
	}
	
	@Override
	public String getResourceKey() {
		return resKey;
	}

	@Override
	public Rectangle getViewRect() {
		//TODO rect translated
		return getBoundingRect();
	}

	@Override
	public int getLayer() {
		return layer;
	}

	public void setResKey(String resKey) {
		this.resKey = resKey;
	}

	public void setLayer(int layer) {
		this.layer = layer;
	}

	@Override
	public void activate() {
		alive = true;
	}

	@Override
	public void destroy() {
		if (isAlive()) {
			alive = false;
			creatHelp.onDestruction(this);
		}
	}

	@Override
	public boolean isAlive() {
		return alive;
	}
	
}
