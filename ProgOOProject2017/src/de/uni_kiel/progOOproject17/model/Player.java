package de.uni_kiel.progOOproject17.model;

import static de.uni_kiel.progOOproject17.model.abs.MoveState.CROUCHING;
import static de.uni_kiel.progOOproject17.model.abs.MoveState.JUMPING;
import static de.uni_kiel.progOOproject17.model.abs.MoveState.NORMAL;

import de.uni_kiel.progOOproject17.model.abs.Distance;
import de.uni_kiel.progOOproject17.model.abs.GameEntity;
import de.uni_kiel.progOOproject17.model.abs.GameObject;
import de.uni_kiel.progOOproject17.model.abs.MoveCommand;
import de.uni_kiel.progOOproject17.model.abs.MoveState;
import de.uni_kiel.progOOproject17.resources.ResourceManager;
import java.awt.Point;

public class Player extends GameEntity implements Stats {

	// FIXME man darf nicht in den Boden crouchen d�rfen

	private int points = 0;

	private int steps;
	private int lifes = 3;

	private MoveCommand currMoveCommand = MoveCommand.NONE;

	private MoveState currMoveState = MoveState.NORMAL;

	public static final Distance JUMPVELOCITY = new Distance(0, -22);
//
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
			// System.out.println("started chrouching!");
			if (currMoveState != CROUCHING) {

				ResourceManager.getInstance().getSound("crouch").play();
				currMoveState = CROUCHING;
				if (environment.isOnGround(this))
					translate(0, -PLAYER_H_CROUCH + PLAYER_H_NORMAL);

				setSize(PLAYER_W, PLAYER_H_CROUCH);
			}

			break;
		case END_CROUCH:

			// System.out.println("stopped crouching");

			if (currMoveState == CROUCHING) {
				currMoveState = NORMAL;

				if (environment.willCollide(this, new Distance(0, -PLAYER_H_CROUCH + PLAYER_H_NORMAL)))
					translate(0, PLAYER_H_CROUCH - PLAYER_H_NORMAL);
				setSize(PLAYER_W, PLAYER_H_NORMAL);
			}

			break;

		case JUMP:

			if (currMoveState != MoveState.JUMPING)
				if (environment.isOnGround(this)) {
					addVelocity(JUMPVELOCITY);
					currMoveState = MoveState.JUMPING;
					ResourceManager.getInstance().getSound("jump").play();

				}

			break;
		}
		currMoveCommand = MoveCommand.NONE;

		// movement
		doMovement();

		if (currMoveState == JUMPING && getVelocity().y >= 0)
			currMoveState = NORMAL;

		// points
		// points++;

		addStep();

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

			creationHelper.create(new Particle("playerDeath", getX(), getY(), 60, 60, 200, 6));
			ResourceManager.getInstance().getSound("death").play();

			return true;
		}

		return false;
	}

	@Override
	public int getPoints() {
		return points;
	}

	public void addStep() {
		steps++;
		if (steps > PLGameModel.GAME_WIDTH) {
			// TODO PARTICLE HERE
			ResourceManager.getInstance().getSound("pickup").play();
			steps -= PLGameModel.GAME_WIDTH;
			points++;

		}
	}

	@Override
	public int getSteps() {
		return steps;
	}

	@Override
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
		return currMoveState == CROUCHING ? key + "_C" : key;
	}

}
