package de.uni_kiel.progOOproject17.model;

import static de.uni_kiel.progOOproject17.model.abs.MoveState.CROUCHING;
import static de.uni_kiel.progOOproject17.model.abs.MoveState.JUMPING;
import static de.uni_kiel.progOOproject17.model.abs.MoveState.JUMPING_AND_CROUCHING;
import static de.uni_kiel.progOOproject17.model.abs.MoveState.NORMAL;

import de.uni_kiel.progOOproject17.model.abs.Distance;
import de.uni_kiel.progOOproject17.model.abs.GameEntity;
import de.uni_kiel.progOOproject17.model.abs.GameObject;
import de.uni_kiel.progOOproject17.model.abs.MoveCommand;
import de.uni_kiel.progOOproject17.model.abs.MoveState;
import de.uni_kiel.progOOproject17.resources.ResourceManager;
import java.awt.Point;

public class Player extends GameEntity {

	private int points = 0;

	private int lifes = 8;

	private MoveCommand currMoveCommand = MoveCommand.NONE;

	private MoveState currMoveState = MoveState.NORMAL;

	public static final Distance JUMPVELOCITY = new Distance(0, -22);
	
	public static final int PLAYER_W = PLGameModel.LHPIXEL_WIDTH * 4;
	public static final int PLAYER_H_NORMAL = PLGameModel.LHPIXEL_HEIGHT * 2;
	public static final int PLAYER_H_CROUCH = PLGameModel.LHPIXEL_HEIGHT * 1;

	public Player(String resKey, Point pos) {
		this(resKey, pos.x, pos.y);
	}

	public Player(String resKey, int x, int y) {
		super(resKey, x, y, PLAYER_W, PLAYER_H_NORMAL);
	}

	// TESTWISE
	private static int counter = 0;

	@Override
	public void tick(long timestamp) {
		if (counter++ % 100 == 0)
			System.out.println("Player bei " + getX());

		if (!isAlive()) {
			return;
		}

		// movement die erste
		switch (currMoveCommand) {

		case NONE:
			break;
		case START_CROUCH:
			switch (currMoveState) {
			case NORMAL:
				currMoveState = CROUCHING;
				if (environment.isOnGround(this))
					translate(0, PLAYER_H_NORMAL - PLAYER_H_CROUCH);
				setSize(PLAYER_W, PLAYER_H_CROUCH);
				ResourceManager.getInstance().getSound("crouch").play();
				break;
			case JUMPING:
				currMoveState = JUMPING_AND_CROUCHING;
				setSize(PLAYER_W, PLAYER_H_CROUCH);
				ResourceManager.getInstance().getSound("crouch").play();
				break;
			default:
				break;
			}
			break;
		case END_CROUCH:
			Distance crouchingDifference = new Distance(0, PLAYER_H_NORMAL - PLAYER_H_CROUCH);
			switch (currMoveState) {
			case CROUCHING:
				currMoveState = NORMAL;
				if (environment.isOnGround(this))
					translate(0, PLAYER_H_CROUCH - PLAYER_H_NORMAL);
				else if (environment.willCollide(this, crouchingDifference)) {
					Distance maxDistance = environment.getCollisionDistance(this, crouchingDifference);
					crouchingDifference.multiply(-1.0);
					maxDistance.add(crouchingDifference);
					translate(maxDistance);
				}
				setSize(PLAYER_W, PLAYER_H_NORMAL);
				break;
			case JUMPING_AND_CROUCHING:
				currMoveState = JUMPING;
				if (environment.willCollide(this, crouchingDifference)) {
					Distance maxDistance = environment.getCollisionDistance(this, crouchingDifference);
					crouchingDifference.multiply(-1.0);
					maxDistance.add(crouchingDifference);
					translate(maxDistance);
				}
				setSize(PLAYER_W, PLAYER_H_NORMAL);
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

	@Override
	public void onContactWith(GameObject obj) {
		assert !obj.equals(this);

		if (currMoveState == JUMPING)
			setVelocity(getVelocity().x, (int) (getVelocity().y * 0.7));

		if (obj.isDeadly())
			if (damage(1))
				obj.addKill();
			else
				obj.destroy();

	}

	public boolean damage(int dmg) {

		if (!isAlive())
			return false;

		lifes -= dmg;
		ResourceManager.getInstance().getSound("playerhurt").play();

		if (lifes <= 0) {
			destroy();

			creationHelper.create(new Particle("playerDeath", getX(), getY(), PLAYER_H_NORMAL, PLAYER_H_NORMAL, 200, 6));
			ResourceManager.getInstance().getSound("death").play();

			return true;
		}

		return false;
	}

	public void addPoint() {
		System.out.println(getResourceKey());
		points++;
	}

	public int getPoints() {
		return points;
	}

	public int getLifes() {
		return lifes;
	}

	/**
	 * @param currMoveCommand
	 *            the currMoveCommand to set
	 */
	public void setCurrMoveCommand(MoveCommand currMoveCommand) {
		this.currMoveCommand = currMoveCommand;
	}

	@Override
	public String getResourceKey() {
		String key = super.getResourceKey();
		return (currMoveState == CROUCHING || currMoveState == MoveState.JUMPING_AND_CROUCHING) ? key + "_C" : key;
	}

}
