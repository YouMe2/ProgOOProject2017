package de.uni_kiel.progOOproject17.model;

import de.uni_kiel.progOOproject17.model.abs.DestroyListener;
import de.uni_kiel.progOOproject17.model.abs.Distance;
import de.uni_kiel.progOOproject17.model.abs.Environment;
import de.uni_kiel.progOOproject17.model.abs.GameEntity;
import de.uni_kiel.progOOproject17.model.abs.GameObject;
import de.uni_kiel.progOOproject17.model.abs.ModelAction;
import de.uni_kiel.progOOproject17.model.abs.MoveCommand;
import de.uni_kiel.progOOproject17.model.abs.MoveState;

import static de.uni_kiel.progOOproject17.model.abs.MoveState.CROUCHING;
import static de.uni_kiel.progOOproject17.model.abs.MoveState.JUMPING;
import static de.uni_kiel.progOOproject17.model.abs.MoveState.NORMAL;

import java.awt.Point;
import java.awt.event.ActionEvent;

public class Player extends GameEntity {

	private int points = 0;

	private int steps;
	private int lifes = 1;

	private MoveCommand currMoveCommand = MoveCommand.NONE;
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

	public static final Distance JUMPVELOCITY = new Distance(0, -22);

	public static final int PLAYER_W = PLGameModel.LHPIXEL_WIDTH * 2;
	public static final int PLAYER_H_NORMAL = PLGameModel.LHPIXEL_HEIGHT * 2;
	public static final int PLAYER_H_CROUCH = PLGameModel.LHPIXEL_HEIGHT * 1;

	
	public Player(String resKey, Point pos, Environment environment, DestroyListener destroyListener) {
		this(resKey, pos.x, pos.y, environment,destroyListener);
	}
	
	public Player(String resKey, int x, int y, Environment environment, DestroyListener destroyListener) {
		super(resKey, x, y, PLAYER_W, PLAYER_H_NORMAL, environment,destroyListener);
	}

	@Override
	public void tick(long timestamp) {
		
		if (!isAlive()) {
			System.out.println("NON ALIVE ENTITY TICKED!");
			return;
		}

		// movement die erste
		switch (currMoveCommand) {

		case NONE:
			break;
		case START_CROUCH:
			System.out.println("started chrouching!");
			if (currMoveState != CROUCHING) {
				currMoveState = CROUCHING;
				// CHROUCH
				setSize(PLAYER_W, PLAYER_H_CROUCH);
			}

			break;
		case END_CROUCH:

			System.out.println("stopped crouching");

			if (currMoveState == CROUCHING) {
				currMoveState = NORMAL;
				if (environment.willCollide(this, new Distance(0, -PLAYER_H_CROUCH + PLAYER_H_NORMAL)))
					translate(0, PLAYER_H_CROUCH - PLAYER_H_NORMAL);
				setSize(PLAYER_W, PLAYER_H_NORMAL);
			}

			break;

		case JUMP:

			// TODO PLAYER JUMP

			System.out.println("jumping "+environment.isOnGround(this));
			
			if (currMoveState != MoveState.JUMPING) {
				currMoveState = MoveState.JUMPING;
				if (environment.isOnGround(this))
					addVelocity(JUMPVELOCITY);
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

		if (obj.isDeadly() && damage(1)){
			obj.addKill();
			obj.destroy();
		}
			

	}

	public boolean damage(int dmg) {

		if (!isAlive())
			return false;

		lifes -= dmg;

		if (lifes <= 0) {
			destroy();
			return true;
		}

		return false;
	}

	@Override
	public boolean isDeadly() {
		return false;
	}

	@Override
	public void addKill() {
		// nothing here
	}

	public int getPoints() {
		return points;
	}
	
	public void addStep() {
		steps++;
		if(steps > PLGameModel.GAME_WIDTH) {
			//TODO PARTICLE HERE
			
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
