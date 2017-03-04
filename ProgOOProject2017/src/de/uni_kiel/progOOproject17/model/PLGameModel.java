package de.uni_kiel.progOOproject17.model;

import de.uni_kiel.progOOproject17.model.abs.TickedBaseModel;
import de.uni_kiel.progOOproject17.view.abs.Viewable;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import javax.swing.AbstractAction;
import javax.swing.Action;

public class PLGameModel extends TickedBaseModel implements Actionable{

	public static final int LH_WIDTH = 28;
	public static final int LH_HEIGHT = 14;

	public static final int LHPIXEL_WIDTH = 15;
	public static final int LHPIXEL_HEIGHT = 30;

	public static final int GAME_WIDTH = LH_WIDTH * LHPIXEL_WIDTH; // = 420
	public static final int GAME_HEIGHT = LH_HEIGHT * LHPIXEL_HEIGHT; // = 490

	private final HashMap<InputActionKeys, Action> actions = new HashMap<>();
	
	
	public AbstractAction newGame = new AbstractAction() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			
		}
	};
	
	public AbstractAction pauseGame = new AbstractAction() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			
		}
	};
	
	public AbstractAction resumeGame = new AbstractAction() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			
		}
	};
	
	public AbstractAction endGame = new AbstractAction() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			
		}
	};
	
	public AbstractAction exitGame = new AbstractAction() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			
		}
	};
	
	
	private Screen currentScreen;
	

	public PLGameModel() {

		// TODO THE SCREENS
		
		
		
		currentScreen = new GameScreen(GAME_WIDTH, GAME_HEIGHT, pauseGame);
		copyAllActions(currentScreen);

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

		views.addAll(Arrays.asList(currentScreen.getViewables()));

		return views.toArray(new Viewable[views.size()]);
	}

	@Override
	public void tick(long timestamp) {

		currentScreen.tick(timestamp);

	}


	
	public void putAction(InputActionKeys iA, Action action) {
		actions.put(iA, action);
	}

	 
	@Override
	public Action getAction(InputActionKeys iA) {
		return actions.get(iA);
	}
	
	
	@Override
	public void copyAllActions(Actionable a) {

		actions.clear();
		for (InputActionKeys key : InputActionKeys.values()) {
			putAction(key, a.getAction(key));
		}
	}
	
	
	
	
	

}
