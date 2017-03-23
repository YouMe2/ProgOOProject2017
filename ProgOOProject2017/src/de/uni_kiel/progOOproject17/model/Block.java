package de.uni_kiel.progOOproject17.model;

import de.uni_kiel.progOOproject17.model.abs.GameEntity;
import de.uni_kiel.progOOproject17.model.abs.GameObject;
import de.uni_kiel.progOOproject17.model.abs.Hitbox;

import java.awt.Rectangle;

/**
 * This class represents a {@link GameEntity} that serves as a Block.
 *
 */
public class Block extends GameEntity {


	/**
	 * Constructs a new Block.
	 * Which will not be active until it is activated by the {@link #activate(Environment, CreationHelper)} method.
	 * 
	 * @param resKey the resource key
	 * @param x the x coord
	 * @param y the y coord
	 * @param w the width
	 * @param h the height
	 */
	public Block(Hitbox hitbox) {
		super(hitbox);
		setGravityActive(false);
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
