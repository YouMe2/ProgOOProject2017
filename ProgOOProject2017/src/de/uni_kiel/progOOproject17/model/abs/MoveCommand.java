package de.uni_kiel.progOOproject17.model.abs;

/**
 * This enum stores all the valid {@link MoveCommand}s the player can react to.
 */
public enum MoveCommand {

	/**
	 * no input
	 */
	NONE,
	/**
	 * jump
	 */
	JUMP,
	/**
	 * start to crouch
	 */
	START_CROUCH,
	/**
	 * stop to crouch
	 */
	END_CROUCH

}
