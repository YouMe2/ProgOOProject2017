package de.uni_kiel.progOOproject17.view;

import de.uni_kiel.progOOproject17.model.PLBaseModel;
import de.uni_kiel.progOOproject17.resources.ResourceManager;
import de.uni_kiel.progOOproject17.view.abs.FramedIOView;
import de.uni_kiel.progOOproject17.view.abs.Viewable;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Arrays;

public class PLDektopView extends FramedIOView {

	private BufferedImage img;
	private ResourceManager res = ResourceManager.getInstance();

	public PLDektopView(String title) {
		super(title, PLBaseModel.GAME_WIDTH, PLBaseModel.GAME_HEIGHT, false);
		img = new BufferedImage(PLBaseModel.GAME_WIDTH, PLBaseModel.GAME_HEIGHT, BufferedImage.TYPE_3BYTE_BGR);
		res = ResourceManager.getInstance();
		// setResizable(false);
	}

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
		Graphics gr2 = centerPane.getGraphics();
		gr2.drawImage(img, 0, 0, PLBaseModel.GAME_WIDTH, PLBaseModel.GAME_HEIGHT, null);
		gr2.dispose();

	}

}
