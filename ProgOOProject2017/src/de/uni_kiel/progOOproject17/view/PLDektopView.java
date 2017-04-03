package de.uni_kiel.progOOproject17.view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Arrays;

import de.uni_kiel.progOOproject17.model.PLBaseModel;
import de.uni_kiel.progOOproject17.model.ViewablesList;
import de.uni_kiel.progOOproject17.model.abs.Hitbox;
import de.uni_kiel.progOOproject17.resources.ResourceManager;
import de.uni_kiel.progOOproject17.view.abs.FramedIOView;
import de.uni_kiel.progOOproject17.view.abs.InputView;
import de.uni_kiel.progOOproject17.view.abs.OutputView;
import de.uni_kiel.progOOproject17.view.abs.Viewable;
import de.uni_kiel.progOOproject17.view.abs.Viewable.Key;

/**
 * This class represents the desktop-only-part of the view of this MVC
 * structure. It combines an {@link InputView} and an {@link OutputView}.
 * 
 */
public class PLDektopView extends FramedIOView {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2350129042863402099L;
	private BufferedImage img;
	private ResourceManager res = ResourceManager.getInstance();

	private boolean resDebuggRender = false;
	private boolean hitboxDebugRender = false;

	/**
	 * Constructs a new {@link PLDektopView}.
	 * 
	 * @param title
	 *            the title
	 */
	public PLDektopView(String title) {
		super(title, PLBaseModel.GAME_WIDTH, PLBaseModel.GAME_HEIGHT, false);
		img = new BufferedImage(PLBaseModel.GAME_WIDTH, PLBaseModel.GAME_HEIGHT, BufferedImage.TYPE_3BYTE_BGR);
		res = ResourceManager.getInstance();
		// setResizable(false);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.uni_kiel.progOOproject17.view.abs.OutputView#render(de.uni_kiel.
	 * progOOproject17.view.abs.Viewable[])
	 */
	@Override
	public void render(Viewable[] viewables) {

		final Graphics gr = img.getGraphics();
		gr.clearRect(0, 0, PLBaseModel.GAME_WIDTH, PLBaseModel.GAME_HEIGHT);

		for (int i = 0; i < Viewable.LAYERsSIZE; i++) {
			final int layer = i;
			Arrays.stream(viewables).filter(v -> v.getLayer() == layer).forEach(v -> {

				render(v, gr);

			});

		}

		gr.dispose();
		Graphics gr2 = contentPane.getGraphics();
		gr2.drawImage(img, 0, 0, PLBaseModel.GAME_WIDTH, PLBaseModel.GAME_HEIGHT, null);
		gr2.dispose();

	}

	/**
	 * @param viewable
	 */
	private void render(Viewable v, Graphics gr) {


		Rectangle rect = v.getViewRect();
		Key key = v.getContentKey();
		String keyText = key != null ? key.getText() : "null";
		if (keyText == null)
			keyText = "null";
		if (keyText.equals(ViewablesList.LISTKEY_TEXT) && key instanceof ViewablesList) {
			ViewablesList list = (ViewablesList) key;
			
			for (Viewable viewable : list) {
				render(viewable, gr);
				
			}
			
			
		}
		else if (keyText.startsWith(Viewable.DEBUGKEY_PREFIX)) {				
			if (hitboxDebugRender) {
				//hitbox debugging
				gr.setColor(Color.CYAN);
				String key2 = keyText.replaceFirst(Viewable.DEBUGKEY_PREFIX, "");
				if (key2.equals(Hitbox.CIRCLE_KEY)) {
					gr.drawOval(rect.x, rect.y, rect.width, rect.height);
				}
				if (key2.equals(Hitbox.LINE_KEY)) {
//					gr.drawLine(rect.x, rect.y, rect.x + rect.width, rect.y + rect.height);
					gr.drawLine(rect.x, rect.y, rect.width, rect.height);
				}

			}	
		}
		else {				
			if (resDebuggRender) {
				//resource debug mode render
				gr.setColor(Color.WHITE);
				gr.drawRect(rect.x, rect.y, rect.width, rect.height);
				gr.drawString(keyText, rect.x + 2, rect.y + 16);
			} else if (!keyText.equals("null")) {
				//standard render
				gr.drawImage(res.getImage(keyText), rect.x, rect.y, rect.width, rect.height, null);
			}
			
		}
	}

	int colorCounter = 0;
	Color colors[] = new Color[] { Color.WHITE, Color.CYAN, Color.YELLOW, Color.GREEN, Color.PINK, Color.MAGENTA,
			Color.ORANGE, Color.ORANGE };

	/**
	 * @return
	 */
	private Color nextColor() {

		colorCounter = (colorCounter + 1) % colors.length;

		return colors[colorCounter];
	}

	/**
	 * @param resDebuggRender
	 *            the resDebuggRender to set
	 */
	public void toggelResDebuggRender() {
		resDebuggRender = !resDebuggRender;
	}

	/**
	 * @param hitboxDebugRender
	 *            the hitboxDebugRender to set
	 */
	public void toggelHitboxDebugRender() {
		hitboxDebugRender = !hitboxDebugRender;
	}

}
