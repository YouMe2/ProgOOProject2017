
package de.uni_kiel.progOOproject17.view.abs;

import java.awt.Graphics;
import java.awt.Rectangle;

/**
 * @author Yannik Eikmeier
 * @since 21.02.2017
 */
public interface Viewable {

	public static final int MAXLAYER = 5; // 0 1 2 3 4;
	
	public static final int BG_LAYER = 0;
	public static final int SB_LAYER = 1;
	public static final int FLOOR_LAYER = 2;
	public static final int ENTITY_LAYER = 3;
	public static final int PARTICLE_LAYER = 4;

	
	public String getResourceKey();
	public Rectangle getViewRect();
	public int getLayer();

}
