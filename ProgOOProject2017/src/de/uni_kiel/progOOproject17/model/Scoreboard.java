package de.uni_kiel.progOOproject17.model;

import static de.uni_kiel.progOOproject17.model.PLGameModel.GAME_WIDTH;
import static de.uni_kiel.progOOproject17.model.PLGameModel.LHPIXEL_HEIGHT;
import static de.uni_kiel.progOOproject17.model.PLGameModel.LHPIXEL_WIDTH;

import de.uni_kiel.progOOproject17.model.abs.GameCompound;
import de.uni_kiel.progOOproject17.view.abs.Viewable;
import java.util.ArrayList;
import java.util.Arrays;

public class Scoreboard extends GameCompound {

	private final Stats stats;

	private ImageViewable stepsDisplay;

	private ImageViewable[] pointsDisplay = new ImageViewable[4];
	private String pointsKey = "point";

	private ImageViewable[] lifesDisplay = new ImageViewable[4];
	private String lifesKey = "life";

	public Scoreboard(Stats stats) {
		super(0, 0, GAME_WIDTH, LHPIXEL_HEIGHT * 2);
		this.stats = stats;

		stepsDisplay = new ImageViewable("stepbar", 0, Math.round(LHPIXEL_HEIGHT * 0.1f), 0,
				Math.round(LHPIXEL_HEIGHT * 0.8f), Viewable.SB_LAYER);

		for (int i = 0; i < pointsDisplay.length; i++) {
			pointsDisplay[i] = new ImageViewable(null, LHPIXEL_WIDTH * (16 + 3 * i), LHPIXEL_HEIGHT, LHPIXEL_WIDTH * 2,
					LHPIXEL_HEIGHT, Viewable.SB_LAYER);

		}

		for (int i = 0; i < lifesDisplay.length; i++) {
			lifesDisplay[i] = new ImageViewable(null, LHPIXEL_WIDTH * (1 + 3 * i), LHPIXEL_HEIGHT, LHPIXEL_WIDTH * 2,
					LHPIXEL_HEIGHT, Viewable.SB_LAYER);

		}

	}

	@Override
	public void tick(long timestamp) {

		stepsDisplay.setSize((int) (stats.getProgress() * PLGameModel.GAME_WIDTH), stepsDisplay.getHeight());

		int p = stats.getPoints();
		if (p > pointsDisplay.length) {
			p = pointsDisplay.length;
		}
		for (int i = 0; i < p; i++) {
			pointsDisplay[i].setResKey(pointsKey);
		}
		for (int i = p; i < pointsDisplay.length; i++) {
			pointsDisplay[i].setResKey(null);
		}

		int l = stats.getLifes();
		if (l > lifesDisplay.length) {
			l = lifesDisplay.length;
		}

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
