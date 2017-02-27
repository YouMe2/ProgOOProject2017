/**
 *
 */
package de.uni_kiel.progOOproject17.view;

import de.uni_kiel.progOOproject17.lighthouse.LighthouseNetwork;
import de.uni_kiel.progOOproject17.model.PLGameModel;
import de.uni_kiel.progOOproject17.view.abs.FramedIOView;
import de.uni_kiel.progOOproject17.view.abs.Viewable;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Arrays;

/**
 * @author Yannik Eikmeier
 * @since 23.02.2017
 */
public class PLLighthouseView extends FramedIOView {

	private BufferedImage img;

	private LighthouseNetwork lhNetwork;
	private Resources res = Resources.getInstance();

	/**
	 * @param title
	 * @param w
	 *            w of the img
	 * @param h
	 *            h of the img
	 * @param host
	 * @param port
	 */
	public PLLighthouseView(String title) {
		super(title, PLGameModel.GAME_WIDTH, PLGameModel.GAME_HEIGHT, false);

		img = new BufferedImage(PLGameModel.GAME_WIDTH, PLGameModel.GAME_HEIGHT,
				BufferedImage.TYPE_3BYTE_BGR);

		// host? port?
		lhNetwork = new LighthouseNetwork();

		// build frame here..

		// connect btn? in menubar?
		try {
			lhNetwork.connect();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// allways pack after building the frame!
		pack();
	}

	
	
	@Override
	public void render(Viewable[] viewables) {

		final Graphics gr = img.getGraphics();
		
		for (int i = 0; i < Viewable.MAXLAYER; i++) {
			final int layer = i;
			Arrays.stream(viewables).parallel().filter(v -> v.getLayer() == layer).forEach(v -> {
				
				Rectangle rect = v.getRect();
				gr.drawImage(res.get(v.getResourceKey() + "-low"), rect.x, rect.y, rect.width, rect.height, null);
				
			});
			
		}

		gr.dispose();
		
		Image i = img.getScaledInstance(PLGameModel.LH_WIDTH,
				PLGameModel.LH_HEIGHT, Image.SCALE_AREA_AVERAGING);
		
		Graphics gr2 = centerPane.getGraphics();
		gr2.drawImage(i, 0, 0, PLGameModel.GAME_WIDTH, PLGameModel.GAME_HEIGHT, null);
		gr2.dispose();

	}

}
