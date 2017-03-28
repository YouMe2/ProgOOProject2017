/**
 * 
 */
package de.uni_kiel.progOOproject17.model.abs;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.AbstractAction;
import javax.swing.Action;

import de.uni_kiel.progOOproject17.model.InputActionKey;
import de.uni_kiel.progOOproject17.view.abs.Viewable;

/**
 * @author Yannik Eikmeier
 * @since 28.03.2017
 *
 */
public abstract class ScreenedBaseModel extends TickedBaseModel {

	
	private Screen pausedScreen;
	private Screen activeScreeen;
	private boolean showPausedScreen;
	
	

	/**
	 * 
	 */
	public ScreenedBaseModel() {
		
		
		
	}

	
	/* (non-Javadoc)
	 * @see de.uni_kiel.progOOproject17.model.abs.TickedBaseModel#tick(long)
	 */
	public void tick(long timestamp) {
		activeScreeen.tick(timestamp);
	}

	/* (non-Javadoc)
	 * @see de.uni_kiel.progOOproject17.model.abs.TickedBaseModel#getViewables()
	 */
	@Override
	public Viewable[] getViewables() {
		
		if (showPausedScreen) {
			ArrayList<Viewable> views = new ArrayList<>();
			
			if (pausedScreen != null)
				views.addAll(Arrays.asList(pausedScreen.getViewables()));
			views.addAll(Arrays.asList(activeScreeen.getViewables()));
			
			return views.toArray(new Viewable[views.size()]);
		}
		return activeScreeen.getViewables();		
	}
	
	/**
	 * Sets the currently active {@link Screen} to s.
	 * And pauses the last {@link Screen}.
	 * 
	 * @see ScreenedBaseModel#activeScreeen
	 * 
	 * @param s
	 *            the new {@link Screen}
	 */
	public void setActiveScreen(Screen s) {
		pausedScreen = activeScreeen;
		activeScreeen = s;
	}
	
	/**
	 * @return the activeScreeen
	 */
	public Screen getActiveScreeen() {
		return activeScreeen;
	}
	
	/**
	 * Resumes the paused Screen and pauses the active {@link Screen}.
	 * 
	 */
	public void resumeScreen() {
		Screen helper = activeScreeen;
		activeScreeen = pausedScreen;
		pausedScreen = helper;
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


			@Override
			public void actionPerformed(ActionEvent e) {
				Action a = activeScreeen.getAction(key);
				if (a != null)
					a.actionPerformed(e);

			}
		};
	}

	/**
	 * @return
	 */
	public boolean isShowPausedScreen() {
		return showPausedScreen;
	}
	
	/**
	 * @param showPausedScreen
	 */
	public void setShowPausedScreen(boolean showPausedScreen) {
		this.showPausedScreen = showPausedScreen;
	}
	
	
}
