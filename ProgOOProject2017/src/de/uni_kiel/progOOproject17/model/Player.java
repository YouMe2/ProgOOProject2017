package de.uni_kiel.progOOproject17.model;

import de.uni_kiel.progOOproject17.model.abs.ModelAction;
import de.uni_kiel.progOOproject17.view.abs.Viewable;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import javax.swing.AbstractAction;

public class Player extends GameEntity {

	private int points = 0;
	private int steps;
	private int lifes = 1;

	private MoveCommand currMoveCommand = MoveCommand.NONE;

//	public final AbstractAction moveJUMP = new AbstractAction("playerJUMP") {
	public final ModelAction moveJUMP = new ModelAction("playerJUMP") {

		@Override
		public void actionPerformed(ActionEvent e) {
			currMoveCommand = MoveCommand.JUMP;
		}
	};
//	public final AbstractAction moveSTARTCROUCH = new AbstractAction("playerSTRATCROUCH") {
	public final ModelAction moveSTARTCROUCH = new ModelAction("playerSTRATCROUCH") {

		@Override
		public void actionPerformed(ActionEvent e) {
			currMoveCommand = MoveCommand.START_CROUCH;
		}
	};
//	public final AbstractAction moveENDCROUCH = new AbstractAction("playerENDCROUCH") {
	public final ModelAction moveENDCROUCH = new ModelAction("playerENDCROUCH") {

		@Override
		public void actionPerformed(ActionEvent e) {
			currMoveCommand = MoveCommand.END_CROUCH;
		}
	};

	public static final int JUMPVELOCITY = 12;

	private static final int PLAYER_W = PLGameModel.LHPIXEL_WIDTH * 2;
	private static final int PLAYER_H_NORMAL = PLGameModel.LHPIXEL_HEIGHT * 2;
	private static final int PLAYER_H_CROUCH = PLGameModel.LHPIXEL_HEIGHT * 1;

	public Player(String resKey, int x, int y) {
		super(resKey, x, y, PLAYER_W, PLAYER_H_NORMAL);

	}

	@Override
	public void tick(long timestamp) {
		// nur wenn der player noch "lebt"
		if (!isAlive()){
			System.out.println("sollte nicht passieren");
			return;
		}

		// movement die erste
		switch (currMoveCommand) {
			case START_CROUCH:

				setSize(PLAYER_W, PLAYER_H_CROUCH);

				currMoveCommand = MoveCommand.NONE;

				break;
			case END_CROUCH:

				translate(0, PLAYER_H_CROUCH - PLAYER_H_NORMAL);
				setSize(PLAYER_W, PLAYER_H_NORMAL);
				currMoveCommand = MoveCommand.NONE;

				break;

			case JUMP:

				// TODO PLAYER JUMP

				if (willCollide(OBJECTS, 0, 1)) { // wenn was drunter ist dann
													 // jump
					getVelocity().height += JUMPVELOCITY;
					currMoveCommand = MoveCommand.NONE;

				}

				break;
			case NONE:
				break;
			default:
				currMoveCommand = MoveCommand.NONE;
				break;
		}

		// gravity
		applyGravity();

		Dimension dis = getCollisionDistance(OBJECTS, getVelocity().width,
				getVelocity().height);

		// collision
		if (!dis.equals(getVelocity())) {
			// es gabe ne collision
			ArrayList<GameObject> colls = getCollObjects(OBJECTS,
					getVelocity().width, getVelocity().height);

			for (GameObject obj : colls)
				if (obj.isDeadly())
					if (damage(1))
						obj.addKill();

		}

		// movement
		this.translate(dis);

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
