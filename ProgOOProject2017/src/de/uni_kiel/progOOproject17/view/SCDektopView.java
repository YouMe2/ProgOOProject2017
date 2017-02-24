/**
 * 
 */
package de.uni_kiel.progOOproject17.view;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import de.uni_kiel.progOOproject17.model.SCGameModel;
import de.uni_kiel.progOOproject17.view.abs.FramedIOView;
import de.uni_kiel.progOOproject17.view.abs.Viewable;

/**
 * @author Yannik Eikmeier
 * @since 23.02.2017
 *
 */
public class SCDektopView extends FramedIOView {

    private BufferedImage img;

   
    /**
     * @param title
     * @param w
     * @param h
     */
    public SCDektopView(String title) {
	super(title, SCGameModel.GAME_WIDTH, SCGameModel.GAME_HEIGHT, true);
	img = new BufferedImage(SCGameModel.GAME_WIDTH, SCGameModel.GAME_HEIGHT, BufferedImage.TYPE_3BYTE_BGR);
	
	//maybe add a button to toggel the lhView too?
	
	
	
	
    }

    @Override
    public void render(Viewable viewable) {

	Graphics gr = img.getGraphics();
	viewable.render(gr);
	gr.dispose();
	gr = centerPane.getGraphics();
	gr.drawImage(img, 0, 0, SCGameModel.GAME_WIDTH, SCGameModel.GAME_HEIGHT, null);
	gr.dispose();

    }

}
