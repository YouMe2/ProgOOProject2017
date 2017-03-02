package de.uni_kiel.progOOproject17.model;

import de.uni_kiel.progOOproject17.model.abs.GameCompound;
import de.uni_kiel.progOOproject17.view.abs.FramedIOView;
import de.uni_kiel.progOOproject17.view.abs.Viewable;

import static de.uni_kiel.progOOproject17.model.PLGameModel.*;

import java.util.ArrayList;
import java.util.Arrays;

public class Scoreboard extends GameCompound {

	private final Player player;

	private Background stepsDisplay;
	
	private Background[] pointsDisplay = new Background[4];
	private String pointsKey = "point";
	
	private Background[] lifesDisplay = new Background[4];
	private String lifesKey = "life";
	
	

	public Scoreboard(Player player) {
		super(0, 0, GAME_WIDTH, LHPIXEL_HEIGHT * 2);
		this.player = player;

		stepsDisplay = new Background("stepbar", 0, Math.round(LHPIXEL_HEIGHT * 0.1f), 0, Math.round(LHPIXEL_HEIGHT * 0.8f));
		stepsDisplay.setLayer(Viewable.SB_LAYER);
		
		for (int i = 0; i < pointsDisplay.length; i++) {
			pointsDisplay[i] = new Background(null, LHPIXEL_WIDTH * (13+3*i), LHPIXEL_HEIGHT, LHPIXEL_WIDTH*2, LHPIXEL_HEIGHT);
			pointsDisplay[i].setLayer(Viewable.SB_LAYER);
			
		}
		
		for (int i = 0; i < lifesDisplay.length; i++) {
			lifesDisplay[i] = new Background(null, LHPIXEL_WIDTH * (0+3*i), LHPIXEL_HEIGHT, LHPIXEL_WIDTH*2, LHPIXEL_HEIGHT);
			lifesDisplay[i].setLayer(Viewable.SB_LAYER);
			
		}

	}

	@Override
	public void tick(long timestamp) {

		stepsDisplay.setSize(player.getSteps(), stepsDisplay.getHeight());

		int p = player.getPoints();
		
		for (int i = 0; i < p; i++) {
			pointsDisplay[i].setResKey(pointsKey);
		}
		for (int i = p; i < pointsDisplay.length; i++) {
			pointsDisplay[i].setResKey(null);
		}
		
		
		int l = player.getLifes();
		for (int i = 0; i < l; i++) {
			lifesDisplay[i].setResKey(lifesKey);
		}
		for (int i = l; i < lifesDisplay.length; i++) {
			lifesDisplay[i].setResKey(null);
		}
		
	}

	@Override
	public Viewable[] getViewables() {

		ArrayList<Viewable> views = new ArrayList<>();
		views.add(stepsDisplay);
		views.addAll(Arrays.asList(pointsDisplay));
		views.addAll(Arrays.asList(lifesDisplay));
		
		return views.toArray(new Viewable[views.size()]);
	}

}
