package de.uni_kiel.progOOproject17.model;

import java.awt.Point;

import de.uni_kiel.progOOproject17.model.abs.Environment;
import de.uni_kiel.progOOproject17.model.abs.GameEntity;
import de.uni_kiel.progOOproject17.model.abs.GameObject;
import de.uni_kiel.progOOproject17.model.abs.Hitbox;

/**
 * This class represents a {@link GameEntity} that serves as a Enemy.
 *
 */
public class Enemy extends GameEntity {

	/**
	 * Constructs a new Enemy which by default is set to deadly.
	 * Which will not be active until it is activated by the {@link #activate(Environment, CreationHelper)} method.
	 * 
	 * @param resKey the resource key
	 * @param x the x coord
	 * @param y the y coord
	 */
	public Enemy(String resKey, int x, int y) {
		super(new Hitbox.RectHitbox(x, y, PLBaseModel.LHPIXEL_WIDTH * 2, Math.round(PLBaseModel.LHPIXEL_HEIGHT * 0.9f)), resKey, x, y, PLBaseModel.LHPIXEL_WIDTH * 2, Math.round(PLBaseModel.LHPIXEL_HEIGHT * 0.9f));
		setDeadly(true);
	}
	
	
	public Enemy(String resKey, Point p) {
		this(resKey, p.x, p.y);
	}

	/* (non-Javadoc)
	 * @see de.uni_kiel.progOOproject17.model.abs.Ticked#tick(long)
	 */
	@Override
	public void tick(long timestamp) {

		if (!isAlive()) {
			return;
		}

		// movement
		doMovement();

	}

	/* (non-Javadoc)
	 * @see de.uni_kiel.progOOproject17.model.abs.GameEntity#onContactWith(de.uni_kiel.progOOproject17.model.abs.GameObject)
	 */
	@Override
	public void onContactWith(GameObject obj) {
		assert !obj.equals(this);
		if (obj.isDeadly()) {
			destroy();
			obj.addKill();
		}

	}

}
