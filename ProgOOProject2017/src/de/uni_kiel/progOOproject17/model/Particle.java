package de.uni_kiel.progOOproject17.model;

import de.uni_kiel.progOOproject17.view.abs.Viewable;
import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;

public class Particle extends GameComponent implements Destroyable {

	public static final ArrayList<Particle> PARTICLES = new ArrayList<>();

	private final Viewable view;

	private final Image[] imgsHI;
	private final Image[] imgsLOW;

	private int counter = 0;
	private final int dtime;
	private long lasttime = 0;

	private boolean alive = true;

	public Particle(int x, int y, int w, int h, Image[] his, Image[] lows,
			int dtime) {
		super(x, y, w, h);
		PARTICLES.add(this);

		imgsHI = his;
		imgsLOW = lows;

		this.dtime = dtime;

		view = new Viewable() {

			@Override
			public void renderLOW(Graphics gr) {

				gr.drawImage(imgsLOW[counter], getX(), getY(), getWidth(),
						getHeight(), null);
			}

			@Override
			public void render(Graphics gr) {

				gr.drawImage(imgsHI[counter], getX(), getY(), getWidth(),
						getHeight(), null);

			}
		};

	}

	@Override
	public void tick(long timestamp) {
		// init
		if (lasttime == 0)
			lasttime = timestamp;

		// next
		if (timestamp - lasttime > dtime && counter < imgsHI.length - 1) {
			lasttime = timestamp;
			counter++;

		}
		// end
		else if (counter == imgsHI.length - 1)
			alive = false;

	}

	@Override
	public Viewable getViewable() {
		if (isAlive())
			return view;
		return null;
	}

	@Override
	public boolean isAlive() {
		return alive;
	}

	@Override
	public void destroy() {
		alive = false;
		PARTICLES.remove(this);
		
	}

}
