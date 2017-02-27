package de.uni_kiel.progOOproject17.model;

import de.uni_kiel.progOOproject17.view.abs.Viewable;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.util.ArrayList;

public class Block extends GameEntity {

	private boolean deadly = false;
	private int killcounter = 0;

	private boolean alive = true;

	private final Image imgLOW;
	private final Image imgHI;

	private final Viewable view;

	public Block(int x, int y, int w, int h, Image hi, Image low) {
		super(x, y, w, h);
		imgHI = hi;
		imgLOW = low;

		view = new Viewable() {

			@Override
			public void renderLOW(Graphics gr) {
				gr.drawImage(imgLOW, getX(), getY(), getWidth(), getHeight(),
						null);

			}

			@Override
			public void render(Graphics gr) {
				gr.drawImage(imgHI, getX(), getY(), getWidth(), getHeight(),
						null);

			}
		};
	}

	@Override
	public boolean isAlive() {
		return alive;
	}

	@Override
	public void tick(long timestamp) {

		if (!isAlive())
			return;

		// gravity ??
		applyGravity();

		Dimension dis = getCollisionDistance(OBJECTS, getVelocity().width,
				getVelocity().height);

		// collision
		if (!dis.equals(getVelocity()) && isDeadly()) {
			// es gabe ne collision
			ArrayList<GameObject> colls = getCollObjects(OBJECTS,
					getVelocity().width, getVelocity().height);

			for (GameObject obj : colls)
				if (obj instanceof Player) {
					Player player = (Player) obj;
					if (player.damage(1))
						addKill();

				}

		}

		// movement
		this.translate(dis);

		if (!getBoundingRect().intersects(new Rectangle(0, 0,
				PLGameModel.GAME_WIDTH, PLGameModel.GAME_HEIGHT)))
			// out of game area!!
			alive = false;

	}

	@Override
	public Viewable getViewable() {
		return view;
	}

	@Override
	public boolean isDeadly() {
		return deadly;
	}

	@Override
	public void addKill() {
		killcounter++;

	}

	public void setDeadly(boolean deadly) {
		this.deadly = deadly;
	}

}
