/**
 *
 */
package de.uni_kiel.progOOproject17.view;

import de.uni_kiel.progOOproject17.model.PLGameModel;
import de.uni_kiel.progOOproject17.view.abs.FramedIOView;
import de.uni_kiel.progOOproject17.view.abs.Viewable;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

/**
 * @author Yannik Eikmeier
 * @since 23.02.2017
 */
public class PLDektopView extends FramedIOView {

	private BufferedImage img;

	/**
	 * @param title
	 * @param w
	 * @param h
	 */
	public PLDektopView(String title) {
		super(title, PLGameModel.GAME_WIDTH, PLGameModel.GAME_HEIGHT, true);
		img = new BufferedImage(PLGameModel.GAME_WIDTH, PLGameModel.GAME_HEIGHT,
				BufferedImage.TYPE_3BYTE_BGR);

		// maybe add a button to toggel the lhView too?

	}

	@Override
	public void render(Viewable viewable) {

		Graphics gr = img.getGraphics();
		viewable.render(gr);
		gr.dispose();
		gr = centerPane.getGraphics();
		gr.drawImage(img, 0, 0, PLGameModel.GAME_WIDTH, PLGameModel.GAME_HEIGHT,
				null);
		gr.dispose();

	}

}
