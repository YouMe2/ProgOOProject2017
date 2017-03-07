package de.uni_kiel.progOOproject17.view;

import de.uni_kiel.progOOproject17.lighthouse.LighthouseNetwork;
import de.uni_kiel.progOOproject17.lighthouse.LighthouseUtil;
import de.uni_kiel.progOOproject17.model.PLBaseModel;
import de.uni_kiel.progOOproject17.resources.ResourceManager;
import de.uni_kiel.progOOproject17.view.abs.FramedIOView;
import de.uni_kiel.progOOproject17.view.abs.Viewable;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Arrays;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JMenuBar;

public class PLLighthouseView extends FramedIOView {

	private BufferedImage img;

	private LighthouseNetwork lhNetwork;
	private ResourceManager res = ResourceManager.getInstance();

	private boolean connected;
	public final AbstractAction connect = new AbstractAction("Connect") {
		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				lhNetwork.connect();
				connected = true;
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	};

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
		super(title, PLBaseModel.GAME_WIDTH, PLBaseModel.GAME_HEIGHT, true);

		img = new BufferedImage(PLBaseModel.GAME_WIDTH, PLBaseModel.GAME_HEIGHT, BufferedImage.TYPE_3BYTE_BGR);

		lhNetwork = new LighthouseNetwork();

		connect.actionPerformed(null);

		JMenuBar menuBar = new JMenuBar();
		JButton button = new JButton(connect);
		menuBar.add(button);
		setJMenuBar(menuBar);

		pack();
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
					 gr.drawImage(res.getImage(key+ "-low"), rect.x, rect.y, rect.width, rect.height, null);
//					gr.drawImage(res.getImage(key), rect.x, rect.y, rect.width, rect.height, null);
			});

		}

		gr.dispose();

		Image i = img.getScaledInstance(PLBaseModel.LH_WIDTH, PLBaseModel.LH_HEIGHT, Image.SCALE_AREA_AVERAGING);
		Graphics gr2;
		if (connected) {

			BufferedImage buffI = new BufferedImage(i.getWidth(null), i.getHeight(null), BufferedImage.TYPE_3BYTE_BGR);

			gr2 = buffI.getGraphics();
			gr2.drawImage(i, 0, 0, null);
			gr2.dispose();

			byte[] data = LighthouseUtil.imageToByteArray(buffI);
			try {
				lhNetwork.send(data);
			} catch (IOException e) {
				connected = false;

			}
		}

		gr2 = centerPane.getGraphics();
		gr2.drawImage(i, 0, 0, PLBaseModel.GAME_WIDTH, PLBaseModel.GAME_HEIGHT, null);
		gr2.dispose();

	}

}
