package de.uni_kiel.progOOproject17.model;

import static de.uni_kiel.progOOproject17.model.PLGameModel.GAME_WIDTH;
import static de.uni_kiel.progOOproject17.model.PLGameModel.LHPIXEL_HEIGHT;
import static de.uni_kiel.progOOproject17.model.PLGameModel.LHPIXEL_WIDTH;

import java.util.ArrayList;
import java.util.Arrays;

import de.uni_kiel.progOOproject17.model.abs.Environment;
import de.uni_kiel.progOOproject17.model.abs.GameCompound;
import de.uni_kiel.progOOproject17.view.abs.Viewable;

public class Scoreboard extends GameCompound {

	private final Player player;

	private ImageViewable stepsDisplay;

	private ImageViewable[] pointsDisplay = new ImageViewable[4];
	private String pointsKey = "point";

	private ImageViewable[] lifesDisplay = new ImageViewable[4];
	private String lifesKey = "life";


	public Scoreboard(Player player) {
		super(0, 0, GAME_WIDTH, LHPIXEL_HEIGHT * 2);
		this.player = player;


		stepsDisplay = new ImageViewable("stepbar", 0, Math.round(LHPIXEL_HEIGHT * 0.1f), 0, Math.round(LHPIXEL_HEIGHT * 0.8f), Viewable.SB_LAYER);

		for (int i = 0; i < pointsDisplay.length; i++) {
			pointsDisplay[i] = new ImageViewable(null, LHPIXEL_WIDTH * (16 + 3 * i), LHPIXEL_HEIGHT, LHPIXEL_WIDTH * 2, LHPIXEL_HEIGHT, Viewable.SB_LAYER);

		}

		for (int i = 0; i < lifesDisplay.length; i++) {
			lifesDisplay[i] = new ImageViewable(null, LHPIXEL_WIDTH * (1 + 3 * i), LHPIXEL_HEIGHT, LHPIXEL_WIDTH * 2, LHPIXEL_HEIGHT, Viewable.SB_LAYER);

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
