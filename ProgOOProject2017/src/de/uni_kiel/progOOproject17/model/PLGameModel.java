package de.uni_kiel.progOOproject17.model;

import de.uni_kiel.progOOproject17.model.abs.TickedBaseModel;
import de.uni_kiel.progOOproject17.view.abs.Viewable;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Arrays;

public class PLGameModel extends TickedBaseModel {

	public static final int LH_WIDTH = 28;
	public static final int LH_HEIGHT = 14;

	public static final int LHPIXEL_WIDTH = 15;
	public static final int LHPIXEL_HEIGHT = 30;

	public static final int GAME_WIDTH = LH_WIDTH * LHPIXEL_WIDTH; // = 420
	public static final int GAME_HEIGHT = LH_HEIGHT * LHPIXEL_HEIGHT; // = 490

	public static final String ACTIONKEY_PLAYER_STARTCROUCH = "start crouching";
	public static final String ACTIONKEY_PLAYER_STOPCROUCH = "stop crouching";
	public static final String ACTIONKEY_PLAYER_JUMP = "jump";
	public static final String ACTIONKEY_PLAYER_LEFT = "left";
	public static final String ACTIONKEY_PLAYER_RIGHT = "right";
	public static final String ACTIONKEY_PLAYER_STOPLEFT = "stopleft";
	public static final String ACTIONKEY_PLAYER_STOPRIGHT = "stopright";

	// scoreboard
	private final Scoreboard scoreboard;

	// gameScreen
	private final GameScreen gamescreen;

	public PLGameModel() {

		gamescreen = new GameScreen(GAME_WIDTH, GAME_HEIGHT);
		scoreboard = new Scoreboard(gamescreen.getPlayerStats());

	}

	public static Point lhToGame(float x, float y) {
		return new Point(Math.round(x * LHPIXEL_WIDTH), Math.round(y * LHPIXEL_HEIGHT));
	}

	public static Rectangle lhToGam(float x, float y, float w, float h) {
		return new Rectangle(Math.round(x * LHPIXEL_WIDTH), Math.round(y * LHPIXEL_HEIGHT),
				Math.round(w * LHPIXEL_WIDTH), Math.round(h * LHPIXEL_HEIGHT));
	}

	@Override
	public Viewable[] getViewables() {

		ArrayList<Viewable> views = new ArrayList<>();

		views.addAll(Arrays.asList(scoreboard.getViewables()));
		views.addAll(Arrays.asList(gamescreen.getViewables()));

		return views.toArray(new Viewable[views.size()]);
	}

	@Override
	public void tick(long timestamp) {

		scoreboard.tick(timestamp);

		gamescreen.tick(timestamp);

	}

}
