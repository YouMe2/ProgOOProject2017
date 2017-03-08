package de.uni_kiel.progOOproject17.model;

import de.uni_kiel.progOOproject17.controller.PLController;
import de.uni_kiel.progOOproject17.model.abs.Screen;
import de.uni_kiel.progOOproject17.model.abs.TickedBaseModel;
import de.uni_kiel.progOOproject17.view.abs.OutputView;
import de.uni_kiel.progOOproject17.view.abs.Viewable;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.AbstractAction;
import javax.swing.Action;

/**
 * This class represents the very base of the model structure of this MVC
 * structure. This class is the 2nd most powerful after the {@link PLController}
 * and the most powerful in the model branch. It controls all the
 * {@link Screen}s and redirects the input actions to the one which is currently
 * active ({@link #currentScreen}).
 * 
 * This also is the first class where it gets some what specific in terms of
 * this being a game made to be viewable on the Lighthouse!
 * 
 */
public class PLBaseModel extends TickedBaseModel {

	/**
	 * The width of the Lighthouse in pixels
	 */
	public static final int LH_WIDTH = 28;
	/**
	 * The height of the Lighthouse in pixels
	 */
	public static final int LH_HEIGHT = 14;

	/**
	 * The scaling factor from LightHouse pixels width to in game width
	 */
	public static final int LHPIXEL_WIDTH = 16;
	/**
	 * The scaling factor from LightHouse pixels height to in game height
	 */
	public static final int LHPIXEL_HEIGHT = 32;

	/**
	 * The width of the game logic
	 */
	public static final int GAME_WIDTH = LH_WIDTH * LHPIXEL_WIDTH; // = 420
	/**
	 * The heigt of the game logic
	 */
	public static final int GAME_HEIGHT = LH_HEIGHT * LHPIXEL_HEIGHT; // = 490

	/**
	 * The {@link Action} which starts a new {@link GameScreen}.
	 */
	public AbstractAction newGame = new AbstractAction() {

		/**
		 * 
		 */
		private static final long serialVersionUID = -42763394711236754L;

		@Override
		public void actionPerformed(ActionEvent e) {

			setCurrentScreen(new GameScreen(GAME_WIDTH, GAME_HEIGHT, pauseGame, endGame));

		}
	};

	/**
	 * The {@link Action} which pauses an active {@link GameScreen} and displays
	 * the {@link PauseMenu} as an overlay to the paused {@link GameScreen}
	 */
	public AbstractAction pauseGame = new AbstractAction() {

		/**
		 * 
		 */
		private static final long serialVersionUID = -5887420044511791287L;

		@Override
		public void actionPerformed(ActionEvent e) {
			pausedScreen = currentScreen;
			setCurrentScreen(new PauseMenu(GAME_WIDTH, GAME_HEIGHT, resumeGame, exitGame));

		}
	};

	/**
	 * The {@link Action} which resumes a previously paused Game
	 */
	public AbstractAction resumeGame = new AbstractAction() {

		/**
		 * 
		 */
		private static final long serialVersionUID = -8495282146520468156L;

		@Override
		public void actionPerformed(ActionEvent e) {

			setCurrentScreen(pausedScreen);
			pausedScreen = null;

		}
	};

	/**
	 * The {@link Action} which displays the {@link EndScreen} after a game has
	 * ended.
	 */
	public AbstractAction endGame = new AbstractAction() {

		/**
		 * 
		 */
		private static final long serialVersionUID = -8224197960021665769L;

		@Override
		public void actionPerformed(ActionEvent e) {
			setCurrentScreen(new EndScreen(GAME_WIDTH, GAME_HEIGHT, ((GameScreen) currentScreen).getPlayerStats(),
					newGame, exitGame));

		}
	};

	/**
	 * The {@link Action} which exits the whole game.
	 */
	public AbstractAction exitGame = new AbstractAction() {

		/**
		 * 
		 */
		private static final long serialVersionUID = 7904181445578765754L;

		@Override
		public void actionPerformed(ActionEvent e) {

			System.exit(0);

		}
	};

	/**
	 * stores the paused {@link Screen} if there is one
	 */
	private Screen pausedScreen;
	/**
	 * the currently active {@link Screen}
	 */
	private Screen currentScreen;

	/**
	 * Constructs a new {@link PLBaseModel} which starts the
	 * {@link StartMenu}.
	 */
	public PLBaseModel() {
		setCurrentScreen(new StartMenu(GAME_WIDTH, GAME_HEIGHT, newGame, exitGame));
	}

	/**
	 * Converts the given Coords from Lighthouse scaling to ingame scaling
	 * 
	 * @param x
	 *            the x coord in LHPixles
	 * @param y
	 *            the y coord in LHPixels
	 * @return the {@link Point} in ingame Coords
	 */
	public static Point lhToGame(float x, float y) {
		return new Point(Math.round(x * LHPIXEL_WIDTH), Math.round(y * LHPIXEL_HEIGHT));
	}

	/**
	 * Converts the given bounds from Lighthouse scaling to ingame scaling
	 * 
	 * @param x
	 *            the x coord on the lH
	 * @param y
	 *            the y coord on the lH
	 * @param w
	 *            the width on the lH
	 * @param h
	 *            the height on the lH
	 * @return the bounds in ingame sclaing
	 */
	public static Rectangle lhToGam(float x, float y, float w, float h) {
		return new Rectangle(Math.round(x * LHPIXEL_WIDTH), Math.round(y * LHPIXEL_HEIGHT),
				Math.round(w * LHPIXEL_WIDTH), Math.round(h * LHPIXEL_HEIGHT));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.uni_kiel.progOOproject17.model.abs.TickedBaseModel#getViewables()
	 */
	public Viewable[] getViewables() {

		ArrayList<Viewable> views = new ArrayList<>();

		if (pausedScreen != null)
			views.addAll(Arrays.asList(pausedScreen.getViewables()));
		views.addAll(Arrays.asList(currentScreen.getViewables()));

		return views.toArray(new Viewable[views.size()]);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.uni_kiel.progOOproject17.model.abs.TickedBaseModel#tick(long)
	 */
	@Override
	public void tick(long timestamp) {

		currentScreen.tick(timestamp);

	}

	/**
	 * Sets the cuurently active {@link Screen} to s.
	 * 
	 * @see PLBaseModel#currentScreen
	 * 
	 * @param s
	 *            the new {@link Screen}
	 */
	private void setCurrentScreen(Screen s) {
		currentScreen = s;
	}

	/**
	 * Returns a allways up-to-date {@link Action} that performes the
	 * {@link Action} corresponding to the key.
	 * 
	 * @param key
	 *            the {@link InputActionKey} which action will be returned
	 * @return the action for the key
	 */
	public Action getAction(InputActionKey key) {

		/*
		 * Returns only a wrapper action which on being called then will get the
		 * corresponding action and call it. This is done so that the returned
		 * action will allways be up to date and never as to be refreshed.
		 */
		return new AbstractAction() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 7564159631540256039L;

			@Override
			public void actionPerformed(ActionEvent e) {
				Action a = currentScreen.getAction(key);
				if (a != null)
					a.actionPerformed(e);

			}
		};
	}

}
