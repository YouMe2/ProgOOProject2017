package de.uni_kiel.progOOproject17.model;

import de.uni_kiel.progOOproject17.model.abs.Environment;
import de.uni_kiel.progOOproject17.model.abs.GameEntity;
import de.uni_kiel.progOOproject17.model.abs.GameObject;
import de.uni_kiel.progOOproject17.model.abs.Hitbox;
import de.uni_kiel.progOOproject17.model.abs.MoveCommand;

/**
 * This class represents a {@link GameEntity} that serves as a Block.
 *
 */
public class Block extends GameEntity {

	/**
	 * The current {@link MoveCommand} which will be evaluated by the tick()
	 * method
	 */
	private MoveCommand currMoveCommand = MoveCommand.NONE;

	/**
	 * Constructs a new Block. Which will not be active until it is activated by
	 * the {@link #activate(Environment, CreationHelper)} method.
	 * 
	 * @param hitbox the {@link Hitbox} of this Block
	 */
	public Block(Hitbox hitbox) {
		super(hitbox);
		setGravityActive(false);
	}
	
	/**
	 * @param currMoveCommand the currMoveCommand to set
	 */
	public void setCurrMoveCommand(MoveCommand currMoveCommand) {
		this.currMoveCommand = currMoveCommand;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.uni_kiel.progOOproject17.model.abs.Ticked#tick(long)
	 */
	@Override
	public void tick(long timestamp) {

		if (!isAlive()) {
			return;
		}
//		System.out.println(currMoveCommand);
		switch (currMoveCommand) {
		case UP:
			
			setVelocity(0, -1);

			break;
		case DOWN:
			setVelocity(0, 1);
			break;
		case LEFT:
			setVelocity(-1, 0);
			break;
		case RIGHT:
			setVelocity(1, 0);
			break;
		default:
			setVelocity(0, 0);
			break;
		}

		// movement
		doMovement();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.uni_kiel.progOOproject17.model.abs.GameEntity#onContactWith(de.
	 * uni_kiel.progOOproject17.model.abs.GameObject)
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
