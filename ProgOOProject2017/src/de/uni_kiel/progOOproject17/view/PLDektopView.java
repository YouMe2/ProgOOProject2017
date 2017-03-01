/**
 *
 */
package de.uni_kiel.progOOproject17.view;

import de.uni_kiel.progOOproject17.model.PLGameModel;
import de.uni_kiel.progOOproject17.resources.ResourceManager;
import de.uni_kiel.progOOproject17.view.abs.FramedIOView;
import de.uni_kiel.progOOproject17.view.abs.Viewable;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Arrays;

/**
 * @author Yannik Eikmeier
 * @since 23.02.2017
 */
public class PLDektopView extends FramedIOView {

	private BufferedImage img;
	private ResourceManager res = ResourceManager.getInstance();
	
	/**
	 * @param title
	 * @param w
	 * @param h
	 */
	public PLDektopView(String title) {
		super(title, PLGameModel.GAME_WIDTH, PLGameModel.GAME_HEIGHT, true);
		img = new BufferedImage(PLGameModel.GAME_WIDTH, PLGameModel.GAME_HEIGHT, BufferedImage.TYPE_3BYTE_BGR);
		res = ResourceManager.getInstance();
		// maybe add a button to toggel the lhView too?

	}

	@Override
	public void render(Viewable[] viewables) {

		final Graphics gr = img.getGraphics();
		
		for (int i = 0; i < Viewable.MAXLAYER; i++) {
			final int layer = i;
			Arrays.stream(viewables).parallel().filter(v -> v.getLayer() == layer).forEach(v -> {
				
				Rectangle rect = v.getViewRect();
				gr.drawImage(res.getImage(v.getResourceKey()), rect.x, rect.y, rect.width, rect.height, null);
				
			});
			
		}

		gr.dispose();
		Graphics gr2 = centerPane.getGraphics();
		gr2.drawImage(img, 0, 0, PLGameModel.GAME_WIDTH, PLGameModel.GAME_HEIGHT, null);
		gr2.dispose();

	}

}
