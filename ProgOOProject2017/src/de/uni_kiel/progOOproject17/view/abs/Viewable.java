
package de.uni_kiel.progOOproject17.view.abs;

import java.awt.Graphics;
import java.awt.Rectangle;

/**
 * @author Yannik Eikmeier
 * @since 21.02.2017
 */
public interface Viewable {

	public static final int MAXLAYER = 5; // 0 1 2 3 4;
	public static final int PARTICLE_LAYER = MAXLAYER - 1;
	
	public String getResourceKey();
	public Rectangle getRect();
	public int getLayer();

}
