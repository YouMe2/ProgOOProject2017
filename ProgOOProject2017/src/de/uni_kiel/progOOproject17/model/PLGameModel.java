package de.uni_kiel.progOOproject17.model;

import de.uni_kiel.progOOproject17.model.abs.TickedBaseModel;
import de.uni_kiel.progOOproject17.view.abs.Viewable;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.AbstractAction;
import javax.swing.Action;

public class PLGameModel extends TickedBaseModel {

	public static final int LH_WIDTH = 28;
	public static final int LH_HEIGHT = 14;

	public static final int LHPIXEL_WIDTH = 32;
	public static final int LHPIXEL_HEIGHT = 64;

	public static final int GAME_WIDTH = LH_WIDTH * LHPIXEL_WIDTH; // = 420
	public static final int GAME_HEIGHT = LH_HEIGHT * LHPIXEL_HEIGHT; // = 490

	public AbstractAction newGame = new AbstractAction() {

		@Override
		public void actionPerformed(ActionEvent e) {

			setCurrentScreen(new GameScreen(GAME_WIDTH, GAME_HEIGHT, pauseGame, endGame));

		}
	};

	public AbstractAction pauseGame = new AbstractAction() {

		@Override
		public void actionPerformed(ActionEvent e) {
			System.out.println("paused");
			pausedScreen = currentScreen;
			setCurrentScreen(new PauseScreen(GAME_WIDTH, GAME_HEIGHT, resumeGame, exitGame));
		}
	};

	public AbstractAction resumeGame = new AbstractAction() {

		@Override
		public void actionPerformed(ActionEvent e) {

			setCurrentScreen(pausedScreen);
			pausedScreen = null;

		}
	};

	public AbstractAction endGame = new AbstractAction() {

		@Override
		public void actionPerformed(ActionEvent e) {
			setCurrentScreen(new EndScreen(GAME_WIDTH, GAME_HEIGHT, ((GameScreen) currentScreen).getPlayerStats(),
					newGame, exitGame));

		}
	};

	public AbstractAction exitGame = new AbstractAction() {

		@Override
		public void actionPerformed(ActionEvent e) {

			System.exit(0);

		}
	};

	private Screen pausedScreen;
	private Screen currentScreen;

	public PLGameModel() {
		setCurrentScreen(new StartScreen(GAME_WIDTH, GAME_HEIGHT, newGame, exitGame));

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

		if (pausedScreen != null)
			views.addAll(Arrays.asList(pausedScreen.getViewables()));
		views.addAll(Arrays.asList(currentScreen.getViewables()));

		return views.toArray(new Viewable[views.size()]);
	}

	@Override
	public void tick(long timestamp) {

		currentScreen.tick(timestamp);

	}

	private void setCurrentScreen(Screen s) {
		currentScreen = s;

	}

	public Action getAction(InputActionKeys key) {

		return new AbstractAction() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Action a = currentScreen.getAction(key);
				if (a != null)
					a.actionPerformed(e);

			}
		};
	}

}
