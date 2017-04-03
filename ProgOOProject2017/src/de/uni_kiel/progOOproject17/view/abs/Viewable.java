package de.uni_kiel.progOOproject17.view.abs;

import java.awt.Rectangle;

import de.uni_kiel.progOOproject17.model.Background;
import de.uni_kiel.progOOproject17.model.Floor;
import de.uni_kiel.progOOproject17.model.MenuScreen;
import de.uni_kiel.progOOproject17.model.Particle;
import de.uni_kiel.progOOproject17.model.Priority;
import de.uni_kiel.progOOproject17.model.Scoreboard;
import de.uni_kiel.progOOproject17.model.SortedLinkedList;
import de.uni_kiel.progOOproject17.model.abs.GameEntity;

/**
 * This interface needs to be implemented by anything to be rendered by any
 * {@link OutputView}.
 *
 *
 */
public interface Viewable extends Priority {

	public static final String DEBUGKEY_PREFIX = "DEBUG-";

	/**
	 * The number of layers there are.
	 */
	public static final int LAYERsSIZE = 8; // 0 1 2 3 4;

	/**
	 * The {@link Background} layer.
	 */
	public static final int BG_LAYER = 0;
	/**
	 * The {@link Scoreboard} layer.
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
	public static final int DEBUG_LAYER = 5;

	/**
	 * The 1st {@link MenuScreen} layer.
	 */
	public static final int MENU_LAYER = 6;
	/**
	 * The 2nd {@link MenuScreen} layer.
	 */
	public static final int MENU2_LAYER = 7;

	/**
	 * Returns the key of this {@link Viewable} which is used by the
	 * {@link OutputView} to determine how this {@link Viewable} will be
	 * rendered. Or {@code null} if this {@link Viewable} does not provide a
	 * key.
	 *
	 * @return the resource key
	 */
	public Key getContentKey();

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
	 * @see #DEBUG_LAYER
	 * @see #MENU_LAYER
	 * @see #MENU2_LAYER
	 *
	 * @return the layer.
	 */
	public int getLayer();

	/**
	 * @return the visble
	 */
	public boolean isVisble();

	/**
	 * Returns the priority of this {@link Viewable}, when on the same layer
	 * with others.
	 * 
	 * @see de.uni_kiel.progOOproject17.model.Priority#getPriority()
	 */
	@Override
	public float getPriority();

	public static interface Key {

		public String getText();

	}
}
