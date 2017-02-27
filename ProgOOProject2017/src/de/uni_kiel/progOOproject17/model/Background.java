/**
 *
 */
package de.uni_kiel.progOOproject17.model;

import de.uni_kiel.progOOproject17.view.abs.Viewable;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

/**
 * @author Yannik Eikmeier
 * @since 23.02.2017
 */
public class Background extends GameComponent {

	private final Image img;

	private final Viewable view;

	public Background(int x, int y, int w, int h, Image i) {
		this(x, y, w, h, i, Color.BLACK);
	}

	/**
	 * @param x
	 * @param y
	 * @param w
	 * @param h
	 * @param i
	 */
	public Background(int x, int y, int w, int h, Image i, Color c) {
		super(x, y, w, h);
		img = i;

		view = new Viewable() {

			@Override
			public void renderLOW(Graphics gr) {
				gr.setColor(c);
				gr.fillRect(getX(), getY(), getWidth(), getHeight());
			}

			@Override
			public void render(Graphics gr) {
				if (img == null) {
					renderLOW(gr);
					return;
				}
				gr.drawImage(img, getX(), getY(), getWidth(), getHeight(),
						null);
			}
		};

	}

	@Override
	public void tick(long timestamp) {
		// TODO could be animated? xD

	}

	@Override
	public Viewable getViewable() {
		return view;
	}

}
