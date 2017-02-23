/**
 * 
 */
package de.uni_kiel.progOOproject17.view;

import java.awt.image.BufferedImage;
import java.io.IOException;

import de.uni_kiel.progOOproject17.lighthouse.LighthouseNetwork;
import de.uni_kiel.progOOproject17.view.abs.FramedIOView;
import de.uni_kiel.progOOproject17.view.abs.Viewable;

/**
 * @author Yannik Eikmeier
 * @since 23.02.2017
 *
 */
public class SCLighthouseView extends FramedIOView {


    private BufferedImage img;

    private static final int lhW = 28;
    private static final int lhH = 14;
    
    private static final int wScale = 15;
    private static final int hScale = 35 ;
    
    
    private LighthouseNetwork lhNetwork;
    
    
    
    /**
     * @param title
     * @param w w of the img
     * @param h h of the img
     * @param host
     * @param port
     */
    public SCLighthouseView(String title, int w, int h) {
	super(title, lhW*wScale, lhH*hScale, false);
	
	img = new BufferedImage(w, h, BufferedImage.TYPE_3BYTE_BGR);
	
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
	// TODO redering and sending
	
    }

}
