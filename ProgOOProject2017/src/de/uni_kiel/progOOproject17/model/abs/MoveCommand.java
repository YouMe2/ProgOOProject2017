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
	CROUCH_START,
	/**
	 * stop to crouch
	 */
	CROUCH_END,
	
	UP,
	UP_END,
	DOWN,
	DOWN_END,
	LEFT,
	LEFT_END,
	RIGHT,
	RIGHT_END,
	

}
