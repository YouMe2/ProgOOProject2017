package de.uni_kiel.progOOproject17.view;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Arrays;

import de.uni_kiel.progOOproject17.model.PLBaseModel;
import de.uni_kiel.progOOproject17.resources.ResourceManager;
import de.uni_kiel.progOOproject17.view.abs.FramedIOView;
import de.uni_kiel.progOOproject17.view.abs.InputView;
import de.uni_kiel.progOOproject17.view.abs.OutputView;
import de.uni_kiel.progOOproject17.view.abs.Viewable;

/**
 * This class represents the desktop-only-part of the view of this MVC structure.
 * It combines an {@link InputView} and an {@link OutputView}.
 * 
 */
public class PLDektopView extends FramedIOView {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2350129042863402099L;
	private BufferedImage img;
	private ResourceManager res = ResourceManager.getInstance();

	/**
	 * Constructs a new {@link PLDektopView}.
	 * 
	 * @param title the title
	 */
	public PLDektopView(String title) {
		super(title, PLBaseModel.GAME_WIDTH, PLBaseModel.GAME_HEIGHT, false);
		img = new BufferedImage(PLBaseModel.GAME_WIDTH, PLBaseModel.GAME_HEIGHT, BufferedImage.TYPE_3BYTE_BGR);
		res = ResourceManager.getInstance();
		// setResizable(false);
	}

	/* (non-Javadoc)
	 * @see de.uni_kiel.progOOproject17.view.abs.OutputView#render(de.uni_kiel.progOOproject17.view.abs.Viewable[])
	 */
	@Override
	public void render(Viewable[] viewables) {

		final Graphics gr = img.getGraphics();
		gr.clearRect(0, 0, PLBaseModel.GAME_WIDTH, PLBaseModel.GAME_HEIGHT);

		for (int i = 0; i < Viewable.LAYERsSIZE; i++) {
			final int layer = i;
			Arrays.stream(viewables).parallel().filter(v -> v.getLayer() == layer).forEach(v -> {

				Rectangle rect = v.getViewRect();
				String key = v.getResourceKey();
				if (key != null)
					gr.drawImage(res.getImage(key), rect.x, rect.y, rect.width, rect.height, null);

			});

		}

		gr.dispose();
		Graphics gr2 = contentPane.getGraphics();
		gr2.drawImage(img, 0, 0, PLBaseModel.GAME_WIDTH, PLBaseModel.GAME_HEIGHT, null);
		gr2.dispose();

	}

}
