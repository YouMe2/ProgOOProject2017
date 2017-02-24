/**
 * 
 */
package de.uni_kiel.progOOproject17.view;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;

import de.uni_kiel.progOOproject17.lighthouse.LighthouseNetwork;
import de.uni_kiel.progOOproject17.model.SCGameModel;
import de.uni_kiel.progOOproject17.view.abs.FramedIOView;
import de.uni_kiel.progOOproject17.view.abs.Viewable;

/**
 * @author Yannik Eikmeier
 * @since 23.02.2017
 *
 */
public class SCLighthouseView extends FramedIOView {


    private BufferedImage img;
    
    
    private LighthouseNetwork lhNetwork;
    
    
    
    /**
     * @param title
     * @param w w of the img
     * @param h h of the img
     * @param host
     * @param port
     */
    public SCLighthouseView(String title) {
	super(title, SCGameModel.GAME_WIDTH, SCGameModel.GAME_HEIGHT, false);
	
	img = new BufferedImage(SCGameModel.GAME_WIDTH, SCGameModel.GAME_HEIGHT, BufferedImage.TYPE_3BYTE_BGR);
	
	//host? port?
	lhNetwork = new LighthouseNetwork("host??", 000000);
	
	//build frame here..
	
	
	// connect btn? in menubar?
	try {
	    lhNetwork.connect();
	} catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	
	//allways pack after building the frame!
	pack();
    }

    /* (non-Javadoc)
     * @see de.uni_kiel.progOOproject17.view.abs.OutputView#render(de.uni_kiel.progOOproject17.view.abs.Viewable)
     */
    @Override
    public void render(Viewable viewable) {
	Graphics gr = img.getGraphics();
	
	//SPECIAL RENDERING HERE!!
	viewable.renderLOW(gr);
	
	gr.dispose();
	
	Image i = img.getScaledInstance(SCGameModel.LH_WIDTH, SCGameModel.LH_HEIGHT, BufferedImage.SCALE_AREA_AVERAGING);
	
	//TODO send data here -> util Method needed!
//	lhNetwork.send(data);
	
	gr = centerPane.getGraphics();
	gr.drawImage(i, 0, 0, SCGameModel.GAME_WIDTH, SCGameModel.GAME_HEIGHT, null);
	gr.dispose();

	
    }

}
