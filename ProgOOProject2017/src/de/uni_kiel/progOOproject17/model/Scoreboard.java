package de.uni_kiel.progOOproject17.model;

import static de.uni_kiel.progOOproject17.model.PLBaseModel.GAME_WIDTH;
import static de.uni_kiel.progOOproject17.model.PLBaseModel.LHPIXEL_HEIGHT;
import static de.uni_kiel.progOOproject17.model.PLBaseModel.LHPIXEL_WIDTH;

import de.uni_kiel.progOOproject17.model.abs.GameCompound;
import de.uni_kiel.progOOproject17.view.abs.Viewable;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * This class represents a {@link GameCompound} that acts as a Scoreboard. The
 * {@link Scoreboard} can display the three values given by the {@link Stats}
 * interface.
 *
 */
public class Scoreboard extends GameCompound {

	/**
	 * The stats this {@link Scoreboard} shows.
	 */
	private final Stats stats;

	/**
	 * The {@link Viewable} that make up the display for the progress
	 */
	private SimpleViewable progressDisplay;

	/**
	 * The {@link Viewable}s that make up the display for the points
	 */
	private SimpleViewable[] pointsDisplay;
	private String pointsKey = "point";

	/**
	 * The {@link Viewable}s that make up the display for the lifes
	 */
	private SimpleViewable[] lifesDisplay;
	private String lifesKey = "life";

	/**
	 * Constructs a new {@link Scoreboard} which when being ticked will display the {@link Stats} in stats.
	 * 
	 * @param stats the {@link Stats} to display
	 */
	public Scoreboard(Stats stats) {
		super(0, 0, GAME_WIDTH, LHPIXEL_HEIGHT * 3);
		this.stats = stats;

		pointsDisplay = new SimpleViewable[GAME_WIDTH / (LHPIXEL_WIDTH * 3)];
		lifesDisplay = new SimpleViewable[GAME_WIDTH / (LHPIXEL_WIDTH * 3)];

		progressDisplay = new SimpleViewable("stepbar", 0, Math.round(LHPIXEL_HEIGHT * 0.1f), 0,
				Math.round(LHPIXEL_HEIGHT * 0.8f), Viewable.SB_LAYER);

		for (int i = 0; i < pointsDisplay.length; i++)
			pointsDisplay[i] = new SimpleViewable(null, LHPIXEL_WIDTH * (1 + 3 * i), LHPIXEL_HEIGHT * 2,
					LHPIXEL_WIDTH * 2, LHPIXEL_HEIGHT, Viewable.SB_LAYER);

		for (int i = 0; i < lifesDisplay.length; i++)
			lifesDisplay[i] = new SimpleViewable(null, LHPIXEL_WIDTH * (1 + 3 * i), LHPIXEL_HEIGHT, LHPIXEL_WIDTH * 2,
					LHPIXEL_HEIGHT, Viewable.SB_LAYER);

	}

	/* (non-Javadoc)
	 * @see de.uni_kiel.progOOproject17.model.abs.Ticked#tick(long)
	 */
	@Override
	public void tick(long timestamp) {

		progressDisplay.setSize((int) (stats.getProgress() * PLBaseModel.GAME_WIDTH), progressDisplay.getHeight());

		int p = stats.getPoints();
		if (p > pointsDisplay.length)
			p = pointsDisplay.length;
		for (int i = 0; i < p; i++)
			pointsDisplay[i].setKeyText(pointsKey);
		for (int i = p; i < pointsDisplay.length; i++)
			pointsDisplay[i].setKeyText(null);

		int l = stats.getLifes();
		if (l > lifesDisplay.length)
			l = lifesDisplay.length;

		for (int i = 0; i < l; i++)
			lifesDisplay[i].setKeyText(lifesKey);
		for (int i = l; i < lifesDisplay.length; i++)
			lifesDisplay[i].setKeyText(null);

	}

	/* (non-Javadoc)
	 * @see de.uni_kiel.progOOproject17.model.abs.GameCompound#getViewables()
	 */
	@Override
	public Viewable[] getViewables() {

		ArrayList<Viewable> views = new ArrayList<>();
		views.add(progressDisplay);
		views.addAll(Arrays.asList(pointsDisplay));
		views.addAll(Arrays.asList(lifesDisplay));

		return views.toArray(new Viewable[views.size()]);
	}

}
