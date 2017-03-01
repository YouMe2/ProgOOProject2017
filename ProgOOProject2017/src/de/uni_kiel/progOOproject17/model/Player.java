package de.uni_kiel.progOOproject17.model;

import static de.uni_kiel.progOOproject17.model.MoveState.CROUCHING;
import static de.uni_kiel.progOOproject17.model.MoveState.JUMPING;
import static de.uni_kiel.progOOproject17.model.MoveState.NONE;

import java.awt.event.ActionEvent;
import java.util.ArrayList;

import de.uni_kiel.progOOproject17.model.abs.ModelAction;

public class Player extends GameEntity {

    private int points = 0;
    private int steps;
    private int lifes = 1;

    private MoveCommand currMoveCommand = MoveCommand.NONE;
    private MoveState currMoveState = MoveState.NONE;

    // public final AbstractAction moveJUMP = new AbstractAction("playerJUMP") {
    public final ModelAction moveJUMP = new ModelAction(PLGameModel.ACTIONKEY_PLAYER_JUMP) {

	@Override
	public void actionPerformed(ActionEvent e) {
	    currMoveCommand = MoveCommand.JUMP;
	}
    };
    // public final AbstractAction moveSTARTCROUCH = new
    // AbstractAction("playerSTRATCROUCH") {
    public final ModelAction moveSTARTCROUCH = new ModelAction(PLGameModel.ACTIONKEY_PLAYER_STARTCROUCH) {

	@Override
	public void actionPerformed(ActionEvent e) {
	    currMoveCommand = MoveCommand.START_CROUCH;
	}
    };
    // public final AbstractAction moveENDCROUCH = new
    // AbstractAction("playerENDCROUCH") {
    public final ModelAction moveENDCROUCH = new ModelAction(PLGameModel.ACTIONKEY_PLAYER_STOPCROUCH) {

	@Override
	public void actionPerformed(ActionEvent e) {
	    currMoveCommand = MoveCommand.END_CROUCH;
	}
    };

    public static final Distance JUMPVELOCITY = new Distance(0, -18);

    private static final int PLAYER_W = PLGameModel.LHPIXEL_WIDTH * 2;
    private static final int PLAYER_H_NORMAL = PLGameModel.LHPIXEL_HEIGHT * 2;
    private static final int PLAYER_H_CROUCH = PLGameModel.LHPIXEL_HEIGHT * 1;

    public Player(String resKey, int x, int y) {
	super(resKey, x, y, PLAYER_W, PLAYER_H_NORMAL);

    }

    @Override
    public void tick(long timestamp) {
	// nur wenn der player noch "lebt"
	if (!isAlive()) {
	    System.out.println("sollte nicht passieren");
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
		currMoveState = NONE;
		if (willCollide(OBJECTS, new Distance(0, -PLAYER_H_CROUCH + PLAYER_H_NORMAL))) {
		    translate(0, PLAYER_H_CROUCH - PLAYER_H_NORMAL);
		}
		setSize(PLAYER_W, PLAYER_H_NORMAL);
	    }

	    break;

	case JUMP:

	    // TODO PLAYER JUMP

	    System.out.println("jumping");

	    if (currMoveState != MoveState.JUMPING) {
		currMoveState = MoveState.JUMPING;
		// if (isOnGround()) {

		addVelocity(JUMPVELOCITY);

		// }
	    }

	    break;
	}
	currMoveCommand = MoveCommand.NONE;

	// gravity
	applyGravity();

	Distance distPerTick = getVelocity();
	Distance collDist = getCollisionDistance(OBJECTS, distPerTick);

	// collision
	// TODO nicht andersherum? Die müssen doch gleich sein --nope wenn sie
	// gleich sind gabe es keine collisionen
	if (!collDist.equals(distPerTick)) {
	    // es gabe ne collision, also:
	    ArrayList<GameObject> colls = getCollObjects(OBJECTS, distPerTick);

	    for (GameObject obj : colls) {
		if (obj == this && obj.isDeadly()) {
		    if (this.damage(1)) // DAMAGE MODEIFICLATOIN?
			obj.addKill();

		}

	    }

	}

	// movement

	this.doMovement(collDist);

	if (currMoveState == JUMPING && getVelocity().y > 0)
	    currMoveState = NONE;

	// points
	// points++;
	steps++;
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

}
