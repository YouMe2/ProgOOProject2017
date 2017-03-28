package de.uni_kiel.progOOproject17.model;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.AbstractAction;
import javax.swing.Action;

import de.uni_kiel.progOOproject17.model.abs.Screen;
import de.uni_kiel.progOOproject17.resources.ResourceManager;
import de.uni_kiel.progOOproject17.view.abs.Viewable;

/**
 * This class serves as a abstract implementation of a {@link Screen} forming a
 * simple menu.
 * 
 */
public abstract class MenuScreen extends Screen {

	private SimpleViewable background;

	private final SimpleViewable selectionCursor;

	private final SimpleViewable[] entries;

	private final ArrayList<Viewable> externalViews = new ArrayList<>();

	private int selction = 0;

	public static final int ENTRY_WIDTH = PLBaseModel.LHPIXEL_WIDTH * 6;
	public static final int ENTRY_HEIGHT = PLBaseModel.LHPIXEL_HEIGHT * 3;

	public static final int CURSOR_WIDTH = PLBaseModel.LHPIXEL_WIDTH * 6;
	public static final int CURSOR_HEIGHT = PLBaseModel.LHPIXEL_HEIGHT * 3;

	/**
	 * Constructs a new {@link MenuScreen} with entries specified by the resKeys
	 * and actions.
	 * 
	 * @param w
	 *            the width
	 * @param h
	 *            the height
	 * @param resKeys
	 *            the resource keys for the entries
	 * @param actions
	 *            the {@link Action}s for the entries
	 */
	public MenuScreen(int w, int h, String[] resKeys, Action[] actions) {
		super(w, h);

		entries = new SimpleViewable[resKeys.length];

		for (int i = 0; i < actions.length; i++) {
			entries[i] = new SimpleViewable(resKeys[i], (getWidth() - ENTRY_WIDTH) / 2,
					4 * PLBaseModel.LHPIXEL_HEIGHT + i * (ENTRY_HEIGHT + PLBaseModel.LHPIXEL_HEIGHT), ENTRY_WIDTH,
					ENTRY_HEIGHT, Viewable.MENU_LAYER);
		}

		selectionCursor = new SimpleViewable("selection", (getWidth() - ENTRY_WIDTH) / 2,
				4 * PLBaseModel.LHPIXEL_HEIGHT + selction * (ENTRY_HEIGHT + PLBaseModel.LHPIXEL_HEIGHT), CURSOR_WIDTH,
				CURSOR_HEIGHT, Viewable.MENU2_LAYER);

		putAction(InputActionKey.UP_P, new AbstractAction() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 6301899874617795350L;

			@Override
			public void actionPerformed(ActionEvent e) {
				selction = (selction - 1 + entries.length) % entries.length;

			}
		});
		putAction(InputActionKey.DOWN_P, new AbstractAction() {

			/**
			 * 
			 */
			private static final long serialVersionUID = -6205517745963454529L;

			@Override
			public void actionPerformed(ActionEvent e) {

				selction = (selction + 1) % entries.length;

			}
		});
		putAction(InputActionKey.SELECT_P, new AbstractAction() {

			/**
			 * 
			 */
			private static final long serialVersionUID = -6503123616124181745L;

			@Override
			public void actionPerformed(ActionEvent e) {

				ResourceManager.getInstance().getSound("pickup").play();

				actions[selction].actionPerformed(e);

			}
		});
	}

	/* (non-Javadoc)
	 * @see de.uni_kiel.progOOproject17.model.abs.Ticked#tick(long)
	 */
	@Override
	public void tick(long timestamp) {

		selectionCursor.setLocation(PLBaseModel.LHPIXEL_WIDTH * 2,
				4 * PLBaseModel.LHPIXEL_HEIGHT + selction * (ENTRY_HEIGHT + PLBaseModel.LHPIXEL_HEIGHT));

		selectionCursor.setLocation((getWidth() - ENTRY_WIDTH) / 2,
				4 * PLBaseModel.LHPIXEL_HEIGHT + selction * (ENTRY_HEIGHT + PLBaseModel.LHPIXEL_HEIGHT));

	}

	/* (non-Javadoc)
	 * @see de.uni_kiel.progOOproject17.model.abs.GameCompound#getViewables()
	 */
	@Override
	public Viewable[] getViewables() {
		ArrayList<Viewable> views = new ArrayList<>();

		if (background != null)
			views.add(background);
		views.addAll(Arrays.asList(entries));
		views.add(selectionCursor);
		views.addAll(this.externalViews);

		return views.toArray(new Viewable[views.size()]);
	}

	/**
	 * Sets a background.
	 * 
	 * @param resKey the resource key of the bg
	 */
	public void setBackground(String resKey) {
		background = new SimpleViewable(resKey, 0, 0, getWidth(), getHeight(), Viewable.BG_LAYER);
	}

	/**
	 * Adds a {@link Viewable} to the {@link MenuScreen}.
	 * 
	 * @param v the {@link Viewable} to be added
	 */
	public void addViewable(Viewable v) {
		externalViews.add(v);
	}

}
