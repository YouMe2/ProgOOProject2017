package de.uni_kiel.progOOproject17.model;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

import de.uni_kiel.progOOproject17.view.abs.Viewable;

public class GameParticle extends GameComponent {

	
	private final Viewable view;
	
	private final Image[] imgs;
	private final Color[] colors;
	
	private int counter = 0;
	private final int dtime;
	private long lasttime = 0;
	
	
	
	public GameParticle(int x, int y, int w, int h, Image[] imgs, Color[] colors, int dtime) {
		super(x, y, w, h);
		
		assert imgs.length == colors.length; // needs an exception i guess
		
		this.imgs = imgs;
		this.colors = colors;
		this.dtime = dtime;
		
		view = new Viewable() {
			
			@Override
			public void renderLOW(Graphics gr) {
				
				gr.setColor(colors[counter]);
				gr.fillRect(getX(), getY(), getWidth(), getHeight());
			}
			
			@Override
			public void render(Graphics gr) {
				gr.drawImage(imgs[counter], getX(), getY(), getWidth(), getHeight(), null);
				
			}
		};
		
	}

	@Override
	public void tick(long timestamp) {
		if( lasttime == 0) {
			lasttime = timestamp;
		}
		else if (timestamp - lasttime > dtime) {
			lasttime = timestamp;
			counter++;
		}
		
		
		
	}

	@Override
	public Viewable getViewable() {
		return view;
	}

}
