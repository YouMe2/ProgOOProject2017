
package de.uni_kiel.progOOproject17.view.abs;

import java.awt.Rectangle;
import java.awt.image.RenderedImage;

import de.uni_kiel.progOOproject17.model.Background;
import de.uni_kiel.progOOproject17.model.Floor;
import de.uni_kiel.progOOproject17.model.Particle;
import de.uni_kiel.progOOproject17.model.Scoreboard;
import de.uni_kiel.progOOproject17.model.abs.GameEntity;

/**
 * This interface needs to be implemented by anything to be rendered by any {@link OutputView}.
 * 
 * @author Yannik Eikmeier
 * @since 03.03.2017
 *
 */
public interface Viewable {

	/**
	 * The number of layers there are.
	 */
	public static final int LAYERsSIZE = 5; // 0 1 2 3 4;

	/**
	 * The {@link Background} layer.
	 */
	public static final int BG_LAYER = 0;
	/**
	 * The {@link Scoreboard} layder.
	 */
	public static final int SB_LAYER = 1;
	/**
	 * The {@link Floor} layer.
	 */
	public static final int FLOOR_LAYER = 2;
	/**
	 * The {@link GameEntity} layer.
	 */
	public static final int ENTITY_LAYER = 3;
	/**
	 * The {@link Particle} layer.
	 */
	public static final int PARTICLE_LAYER = 4;

	/**
	 * Returns the resource key of this {@link Viewable} which is used by the
	 * {@link OutputView} to determine how this {@link Viewable} will be
	 * rendered.
	 * 
	 * @return the resource key
	 */
	public String getResourceKey();

	/**
	 * Returns the {@link Rectangle} in which this {@link Viewable} needs to be
	 * rendered.
	 * 
	 * @return the {@link Rectangle}
	 */
	public Rectangle getViewRect();

	/**
	 * Retruns the layer on which this {@link Viewable} needs to be redered.
	 * 
	 * @see #LAYERsSIZE
	 * @see #BG_LAYER
	 * @see #SB_LAYER
	 * @see #FLOOR_LAYER
	 * @see #ENTITY_LAYER
	 * @see #PARTICLE_LAYER
	 * 
	 * @return the layer.
	 */
	public int getLayer();

}
