package de.uni_kiel.progOOproject17.model;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

import javax.swing.AbstractAction;

import de.uni_kiel.progOOproject17.view.abs.Viewable;

public class Player extends GameEntity {

	private int points = 0;
	private int lifes = 1;
	private boolean alive = false;
	
	private final Image imgLOW;
	private final Image imgHI;
	
	private final Viewable view;
	
	private MoveCommand currMoveCommand = MoveCommand.NONE;
	
	public final AbstractAction moveJUMP = new AbstractAction() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			currMoveCommand = MoveCommand.JUMP;
		}
	};
	public final AbstractAction moveCROUCH = new AbstractAction() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			currMoveCommand = MoveCommand.CROUCH;
		}
	};
	
	
	public Player(int x, int y, int w, int h, Image hi, Image low) {
		super(x, y, w, h);
		
		this.imgHI = hi;
		this.imgLOW = low;
		this.view = new Viewable() {
			
			@Override
			public void renderLOW(Graphics gr) {
				gr.drawImage(imgLOW, getX(), getY(),  getWidth(), getHeight(), null);
				
			}
			
			@Override
			public void render(Graphics gr) {
				gr.drawImage(imgHI, getX(), getY(),  getWidth(), getHeight(), null);
				
			}
		};
		
	}

	@Override
	public boolean isAlive() {
		return alive;
	}

	@Override
	public void tick(long timestamp) {
		
		
		//gravity
		applyGravity();
		
		Dimension dis = getCollisionDistance(OBJECTS, getVelocity().width, getVelocity().height);
		
		//collision
		if(!dis.equals(getVelocity())) {
			//es gabe ne collision
			ArrayList<GameObject> colls = getCollObjects(OBJECTS, getVelocity().width, getVelocity().height);
			
			for (GameObject obj : colls) {
				if(obj.isDeadly()) {
					this.kill();
					obj.addKill();
				}
				
			}
			
		
		}
		
		//movement
		this.translate(dis.width, dis.height);
		
		//points
		//TODO
		
		//alive
		//TODO
	}

	public void kill(){
		//kills this Player
		// will be called by an enemy when it will collide with this or when this will collide with an enemy!
		
	}
	
	@Override
	public Viewable getViewable() {
		//even if !isAlive()??
		return view;
	}

	@Override
	public boolean isDeadly() {
		return false;
	}

	@Override
	public void addKill() {
		//nothing here
	}

}
