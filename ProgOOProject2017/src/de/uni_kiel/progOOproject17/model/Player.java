package de.uni_kiel.progOOproject17.model;

import static de.uni_kiel.progOOproject17.model.abs.MoveState.CROUCHING;
import static de.uni_kiel.progOOproject17.model.abs.MoveState.JUMPING;
import static de.uni_kiel.progOOproject17.model.abs.MoveState.JUMPING_AND_CROUCHING;
import static de.uni_kiel.progOOproject17.model.abs.MoveState.NORMAL;

import java.awt.Point;

import de.uni_kiel.progOOproject17.model.abs.Distance;
import de.uni_kiel.progOOproject17.model.abs.GameEntity;
import de.uni_kiel.progOOproject17.model.abs.GameObject;
import de.uni_kiel.progOOproject17.model.abs.Hitbox;
import de.uni_kiel.progOOproject17.model.abs.MoveCommand;
import de.uni_kiel.progOOproject17.model.abs.MoveState;
import de.uni_kiel.progOOproject17.resources.GameProperties;
import de.uni_kiel.progOOproject17.resources.ResourceManager;
import de.uni_kiel.progOOproject17.view.abs.Viewable.Key;

/**
 * This class represents a {@link GameEntity} that acts as the {@link Player}.
 * The player has a certain number of lifes and points. The player can be
 * interacted with view setting a {@link MoveCommand}.
 * 
 *
 */
public class Player extends GameEntity {

	/**
	 * The number of points the player scored.
	 */
	private int points = 0;

	/**
	 * The max lifes the player can have
	 */
	private final int maxLifes = 9;
	
	/**
	 * The current number of lifes
	 */
	private int lifes = Math.min(Integer.valueOf(GameProperties.getInstance().getProperty("playerLifes")), maxLifes);

	/**
	 * The current {@link MoveCommand} which will be evaluated by the tick() method
	 */
	private MoveCommand currMoveCommand = MoveCommand.NONE;

	/**
	 * The current {@link MoveState} of the player
	 */
	private MoveState currMoveState = MoveState.NORMAL;

	/**
	 * The velocity the player will gain when starting to jump.
	 */
	public static final Distance JUMPVELOCITY = new Distance(0,
			Integer.valueOf(GameProperties.getInstance().getProperty("jumpVelocity")));

	/**
	 * The player width
	 */
	public static final int PLAYER_W = PLBaseModel.LHPIXEL_WIDTH
			* Integer.valueOf(GameProperties.getInstance().getProperty("playerW"));
	/**
	 * The player height when normal
	 */
	public static final int PLAYER_H_NORMAL = PLBaseModel.LHPIXEL_HEIGHT
			* Integer.valueOf(GameProperties.getInstance().getProperty("playerH"));
	/**
	 * The player height when crouch
	 */
	public static final int PLAYER_H_CROUCH = PLAYER_H_NORMAL / 2;

	
	private final String resKey;
	
	private Key key = new Key() {
		@Override
		public String getText() {
			return (currMoveState == CROUCHING || currMoveState == MoveState.JUMPING_AND_CROUCHING) ? resKey + "_C" : resKey;
		}
		
	};
	
	
	/**
	 * Constructs a new Player.
	 * 
	 * @param resKey
	 *            the resource key
	 * @param pos
	 *            the position
	 */
	public Player(String resKey, Point pos) {
		this(resKey, pos.x, pos.y);
	}

	/**
	 * Constructs a new Player
	 * 
	 * @param resKey
	 *            the resource key
	 * @param x
	 *            the x coord
	 * @param y
	 *            the y coord
	 */
	public Player(String resKey, int x, int y) {
		super(new Hitbox.RectHitbox(x, y, PLAYER_W, PLAYER_H_NORMAL), resKey, x, y, PLAYER_W, PLAYER_H_NORMAL);
		this.resKey = resKey;
	}

	// TESTWISE
	// private static int counter = 0;

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.uni_kiel.progOOproject17.model.abs.Ticked#tick(long)
	 */
	@Override
	public void tick(long timestamp) {
		// if (counter++ % 100 == 0)
		// System.out.println("Player bei " + getX());

		if (!isAlive()) {
			return;
		}

		// movement die erste
		switch (currMoveCommand) {

		case NONE:
			break;
		case CROUCH_START:
			switch (currMoveState) {
			case NORMAL:
				currMoveState = CROUCHING;
				if (environment.isOnGround(this)){
					Distance headDown = new Distance(0, PLAYER_H_NORMAL - PLAYER_H_CROUCH);
					move(headDown);
				}
				getView().setSize(PLAYER_W, PLAYER_H_CROUCH);
				((Hitbox.RectHitbox)getThisHitbox()).setSize(PLAYER_W, PLAYER_H_CROUCH);
				ResourceManager.getInstance().getSound("crouch").play();
				break;
			case JUMPING:
				currMoveState = JUMPING_AND_CROUCHING;
				getView().setSize(PLAYER_W, PLAYER_H_CROUCH);
				((Hitbox.RectHitbox)getThisHitbox()).setSize(PLAYER_W, PLAYER_H_CROUCH);
				ResourceManager.getInstance().getSound("crouch").play();
				break;
			default:
				break;
			}
			break;
		case CROUCH_END:
			Distance crouchingDifference = new Distance(0, PLAYER_H_NORMAL - PLAYER_H_CROUCH);
			switch (currMoveState) {
			case CROUCHING:
				currMoveState = NORMAL;
				if (environment.isOnGround(this)){
					Distance headUp = new Distance(0, PLAYER_H_CROUCH - PLAYER_H_NORMAL);
					move(headUp);
				}
				else if (environment.willCollide(this, crouchingDifference)) {
					Distance maxDistance = environment.getCollisionDistance(this, crouchingDifference);
					crouchingDifference.scale(-1.0);
					maxDistance.add(crouchingDifference);
					move(maxDistance);
				}
				getView().setSize(PLAYER_W, PLAYER_H_NORMAL);
				((Hitbox.RectHitbox)getThisHitbox()).setSize(PLAYER_W, PLAYER_H_NORMAL);
				break;
			case JUMPING_AND_CROUCHING:
				currMoveState = JUMPING;
				if (environment.willCollide(this, crouchingDifference)) {
					Distance maxDistance = environment.getCollisionDistance(this, crouchingDifference);
					crouchingDifference.scale(-1.0);
					maxDistance.add(crouchingDifference);
					move(maxDistance);
				}
				getView().setSize(PLAYER_W, PLAYER_H_NORMAL);
				((Hitbox.RectHitbox)getThisHitbox()).setSize(PLAYER_W, PLAYER_H_NORMAL);
			default:
				break;
			}
			break;
		case JUMP:
			switch (currMoveState) {
			case NORMAL:
				if (environment.isOnGround(this)) {
					currMoveState = JUMPING;
					addVelocity(JUMPVELOCITY);
					ResourceManager.getInstance().getSound("jump").play();
				}
				break;
			case CROUCHING:
				if (environment.isOnGround(this)) {
					currMoveState = JUMPING_AND_CROUCHING;
					addVelocity(JUMPVELOCITY);
					ResourceManager.getInstance().getSound("jump").play();
				}
				break;
			default:
				break;
			}
			break;
		}
		currMoveCommand = MoveCommand.NONE;

		// movement
		doMovement();

		if (getVelocity().y >= 0)
			if (currMoveState == JUMPING)
				currMoveState = NORMAL;
			else if (currMoveState == JUMPING_AND_CROUCHING)
				currMoveState = CROUCHING;
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

		if (currMoveState == JUMPING)
			setVelocity(getVelocity().x, (int) (getVelocity().y * 0.7));

		if (obj.isDeadly())
			if (damage(1)){
				obj.addKill();
				System.out.println("Killed by: " + obj);
			}
			else
				obj.destroy();

	}

	/**
	 * Damages the player.
	 * 
	 * @param dmg
	 *            the damage to be done
	 * @return true only if this damage killed the palyer
	 */
	public boolean damage(int dmg) {

		if (!isAlive())
			return false;

		lifes -= dmg;
		ResourceManager.getInstance().getSound("playerhurt").play();

		if (lifes <= 0) {
			destroy();

			creationHelper.create(new Particle("playerDeath", getThisHitbox().getX(), getThisHitbox().getY(), PLAYER_H_NORMAL, PLAYER_H_NORMAL, 200, 6));
			ResourceManager.getInstance().getSound("death").play();

			return true;
		}

		return false;
	}

	/**
	 * Adds a point to the player
	 */
	public void addPoint() {
		// System.out.println(getResourceKey());
		points++;
	}

	/**
	 * @return the points
	 */
	public int getPoints() {
		return points;
	}

	/**
	 * @return the current lifes
	 */
	public int getLifes() {
		return lifes;
	}

	/**
	 * Sets a new {@link MoveCommand}.
	 * 
	 * @param currMoveCommand
	 *            the currMoveCommand to set
	 */
	public void setCurrMoveCommand(MoveCommand currMoveCommand) {
		this.currMoveCommand = currMoveCommand;
	}

	/* (non-Javadoc)
	 * @see de.uni_kiel.progOOproject17.model.abs.GameElement#getResourceKey()
	 */
	@Override
	public Key getContentKey() {
		return key;
	}

	/**
	 * Adds a life to the player;
	 */
	public void addLife() {
		lifes = Math.min(maxLifes, lifes+1);
		
	}

}
