package de.uni_kiel.progOOproject17.model.abs;

/**
 * This enum stores all the valid {@link MoveState} the player can be in.
 */
public enum MoveState {

	/**
	 * no special movement
	 */
	NORMAL, 
	/**
	 * jumping
	 */
	JUMPING, 
	/**
	 * crouching
	 */
	CROUCHING, 
	/**
	 * jumping and crouching in the same time
	 */
	JUMPING_AND_CROUCHING

}
