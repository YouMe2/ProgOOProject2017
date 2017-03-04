/**
 * 
 */
package de.uni_kiel.progOOproject17.model;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.AbstractAction;
import javax.swing.Action;

import de.uni_kiel.progOOproject17.view.abs.Viewable;

/**
 * @author Yannik Eikmeier
 * @since 04.03.2017
 *
 */
public abstract class MenuScreen extends Screen {

	private ImageViewable background;

	private final ImageViewable selectionCursor;
	
	private final ImageViewable[] entries;
	
	private final ArrayList<Viewable> views = new ArrayList<>();
	
	private int selction = 0;
	
	
	public static final int ENTRY_WIDTH = PLGameModel.LHPIXEL_WIDTH * 8;
	public static final int ENTRY_HEIGHT = PLGameModel.LHPIXEL_HEIGHT*4;
	
	public static final int CURSOR_WIDTH = PLGameModel.LHPIXEL_WIDTH * 6;
	public static final int CURSOR_HEIGHT = PLGameModel.LHPIXEL_HEIGHT*4;
	
	
	/**
	 * @param w
	 * @param h
	 */
	public MenuScreen(int w, int h, String[] resKeys, Action[] actions) {
		super(w, h);
		
		
		entries = new ImageViewable[resKeys.length];
		for (int i = 0; i < actions.length; i++) {
			entries[i] = new ImageViewable(resKeys[i], new Rectangle((w-ENTRY_WIDTH)/2, 4*PLGameModel.LHPIXEL_HEIGHT + i*(ENTRY_HEIGHT+PLGameModel.LHPIXEL_HEIGHT), ENTRY_WIDTH, ENTRY_HEIGHT), Viewable.ENTITY_LAYER);
		}
		
		selectionCursor = new ImageViewable("pointer", PLGameModel.LHPIXEL_WIDTH*2, 4*PLGameModel.LHPIXEL_HEIGHT + selction*(ENTRY_HEIGHT+PLGameModel.LHPIXEL_HEIGHT), CURSOR_WIDTH, CURSOR_HEIGHT, Viewable.PARTICLE_LAYER);
		
		putAction(InputActionKeys.P_UP, new AbstractAction() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				selction = (selction-1+entries.length)%entries.length;
				
			}
		});
		putAction(InputActionKeys.P_DOWN, new AbstractAction() {
			
			@Override
			public void actionPerformed(ActionEvent e) {

				selction = (selction+1)%entries.length;
				
			}
		});
		putAction(InputActionKeys.P_SELECT, new AbstractAction() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				actions[selction].actionPerformed(e);
				
			}
		});
	}

	
	@Override
	public void tick(long timestamp) {
		
		selectionCursor.setLocation(PLGameModel.LHPIXEL_WIDTH*2, 4*PLGameModel.LHPIXEL_HEIGHT + selction*(ENTRY_HEIGHT+PLGameModel.LHPIXEL_HEIGHT));
		
	}

	
	
	@Override
	public Viewable[] getViewables() {
		ArrayList<Viewable> views = new ArrayList<>();

		if(background != null)
			views.add(background);
		views.addAll(Arrays.asList(entries));
		views.add(selectionCursor);
		views.addAll(this.views);
		
		return views.toArray(new Viewable[views.size()]);
	}
	
	public void setBackground(String resKey) {
		this.background = new ImageViewable(resKey, 0, 0, getWidth(), getHeight(), Viewable.BG_LAYER);
	}
	
	public void addViewable(Viewable v) {
		views.add(v);
	}
	

}
