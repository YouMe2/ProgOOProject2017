package de.uni_kiel.progOOproject17.model;

import static de.uni_kiel.progOOproject17.model.abs.MoveState.CROUCHING;
import static de.uni_kiel.progOOproject17.model.abs.MoveState.JUMPING;
import static de.uni_kiel.progOOproject17.model.abs.MoveState.NORMAL;

import de.uni_kiel.progOOproject17.model.abs.Distance;
import de.uni_kiel.progOOproject17.model.abs.GameEntity;
import de.uni_kiel.progOOproject17.model.abs.GameObject;
import de.uni_kiel.progOOproject17.model.abs.ModelAction;
import de.uni_kiel.progOOproject17.model.abs.MoveCommand;
import de.uni_kiel.progOOproject17.model.abs.MoveState;
import de.uni_kiel.progOOproject17.resources.ResourceManager;
import java.awt.Point;
import java.awt.event.ActionEvent;

public class Player extends GameEntity implements Stats{

	private int points = 0;

	private int steps;
	private int lifes = 3;
	private final int playerXVelocity = 10;

	private MoveCommand currMoveCommand = MoveCommand.NONE;
	private boolean movingLeft = false;
	private boolean movingRight = false;

	private MoveState currMoveState = MoveState.NORMAL;

	public final ModelAction moveJUMP = new ModelAction(PLGameModel.ACTIONKEY_PLAYER_JUMP) {
		@Override
		public void actionPerformed(ActionEvent e) {
			currMoveCommand = MoveCommand.JUMP;
		}
	};

	public final ModelAction moveSTARTCROUCH = new ModelAction(PLGameModel.ACTIONKEY_PLAYER_STARTCROUCH) {

		@Override
		public void actionPerformed(ActionEvent e) {
			currMoveCommand = MoveCommand.START_CROUCH;
		}
	};
	public final ModelAction moveENDCROUCH = new ModelAction(PLGameModel.ACTIONKEY_PLAYER_STOPCROUCH) {

		@Override
		public void actionPerformed(ActionEvent e) {
			currMoveCommand = MoveCommand.END_CROUCH;
		}
	};
	public final ModelAction moveLEFT = new ModelAction(PLGameModel.ACTIONKEY_PLAYER_LEFT) {

		@Override
		public void actionPerformed(ActionEvent e) {
			movingLeft = true;
			movingRight = false;

		}
	};

	public final ModelAction moveRIGHT = new ModelAction(PLGameModel.ACTIONKEY_PLAYER_RIGHT) {

		@Override
		public void actionPerformed(ActionEvent e) {
			movingLeft = false;
			movingRight = true;

		}
	};

	public final ModelAction moveStopLEFT = new ModelAction(PLGameModel.ACTIONKEY_PLAYER_STOPLEFT) {

		@Override
		public void actionPerformed(ActionEvent e) {
			movingLeft = false;

		}
	};

	public final ModelAction moveStopRIGHT = new ModelAction(PLGameModel.ACTIONKEY_PLAYER_STOPRIGHT) {

		@Override
		public void actionPerformed(ActionEvent e) {
			movingRight = false;
		}
	};

	public static final Distance JUMPVELOCITY = new Distance(0, -22);

	public static final int PLAYER_W = PLGameModel.LHPIXEL_WIDTH * 2;
	public static final int PLAYER_H_NORMAL = PLGameModel.LHPIXEL_HEIGHT * 2;
	public static final int PLAYER_H_CROUCH = PLGameModel.LHPIXEL_HEIGHT * 1;

	public Player(String resKey, Point pos) {
		this(resKey, pos.x, pos.y);
	}

	public Player(String resKey, int x, int y) {
		super(resKey, x, y, PLAYER_W, PLAYER_H_NORMAL);
	}

	private static int counter = 0;

	@Override
	public void tick(long timestamp) {
		if (counter++ % 100 == 0)
			System.out.println("Player bei " + getX());

		if (!isAlive()) {
			System.out.println("NON ALIVE ENTITY TICKED!");
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
				setResKey(getResourceKey() + "_C");
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

				setResKey(getResourceKey().replace("_C", ""));
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

		if (movingLeft)
			setVelocity(-playerXVelocity, getVelocity().y);
		else if (movingRight)
			setVelocity(playerXVelocity, getVelocity().y);
		else
			setVelocity(0, getVelocity().y);

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

	public int getSteps() {
		return steps;
	}

	public int getLifes() {
		return lifes;
	}

}
