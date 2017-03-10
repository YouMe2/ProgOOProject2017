package de.uni_kiel.progOOproject17.view;

import de.uni_kiel.progOOproject17.lighthouse.LighthouseNetwork;
import de.uni_kiel.progOOproject17.lighthouse.LighthouseUtil;
import de.uni_kiel.progOOproject17.model.PLBaseModel;
import de.uni_kiel.progOOproject17.resources.GameProperties;
import de.uni_kiel.progOOproject17.resources.ResourceManager;
import de.uni_kiel.progOOproject17.view.abs.FramedIOView;
import de.uni_kiel.progOOproject17.view.abs.InputView;
import de.uni_kiel.progOOproject17.view.abs.OutputView;
import de.uni_kiel.progOOproject17.view.abs.Viewable;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Arrays;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JMenuBar;

/**
 * This class represents the lighthouse-connection-part of the view of this MVC structure.
 * It combines an {@link InputView} and an {@link OutputView}.
 * 
 */
public class PLLighthouseView extends FramedIOView {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6700588951586487858L;

	private BufferedImage img;

	private LighthouseNetwork lhNetwork;
	private ResourceManager res = ResourceManager.getInstance();
	private String lowAppendix = GameProperties.getInstance().getProperty("lowResKeyAppendix");

	private boolean connected;
	/**
	 * An {@link Action} that when executed tries to connect the {@link #lhNetwork} to the lighthouse server.
	 */
	public final AbstractAction connect = new AbstractAction("Connect") {
		/**
		 * 
		 */
		private static final long serialVersionUID = -3562882691277162294L;

		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				lhNetwork.connect();
				connected = true;
			} catch (IOException ex) {
				ex.printStackTrace();
//				System.err.println("Connection failed!");
				connected = false;
			}
		}
	};



	
	/**
	 * Constructs a new {@link PLLighthouseView}.
	 * 
	 * @param title the title
	 */
	public PLLighthouseView(String title) {
		super(title, PLBaseModel.GAME_WIDTH, PLBaseModel.GAME_HEIGHT, true);

		img = new BufferedImage(PLBaseModel.GAME_WIDTH, PLBaseModel.GAME_HEIGHT, BufferedImage.TYPE_3BYTE_BGR);

		lhNetwork = new LighthouseNetwork("3", "3MW9-A1LL-L1D8-QQX4");

		connect.actionPerformed(null);

		JMenuBar menuBar = new JMenuBar();
		JButton button = new JButton(connect);
		menuBar.add(button);
		setJMenuBar(menuBar);

		pack();
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
					 gr.drawImage(res.getImage(key+ lowAppendix), rect.x, rect.y, rect.width, rect.height, null);
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

		gr2 = contentPane.getGraphics();
		gr2.drawImage(i, 0, 0, PLBaseModel.GAME_WIDTH, PLBaseModel.GAME_HEIGHT, null);
		gr2.dispose();

	}

}
