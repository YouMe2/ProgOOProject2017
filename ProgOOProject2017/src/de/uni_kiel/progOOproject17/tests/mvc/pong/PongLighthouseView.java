package de.uni_kiel.progOOproject17.tests.mvc.pong;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.swing.JButton;

import de.uni_kiel.progOOproject17.tests.mvc.abst.v.FramedIOView;
import de.uni_kiel.progOOproject17.tests.mvc.abst.v.Viewable;

public class PongLighthouseView extends FramedIOView {

    private BufferedImage img;

    private static final int lhW = 29;
    private static final int lhH = 14;
    
    private static final int wScale = 15;
    private static final int hScale = 35 ;
    
    
    public PongLighthouseView(String title, int w, int h) {
	super(title, lhW*wScale, lhH*hScale, false);
	img = new BufferedImage(w, h, BufferedImage.TYPE_3BYTE_BGR);	
	
    }

   
    @Override
    public void render(Viewable viewable) {
	
	Graphics gr = img.getGraphics();
	viewable.render(gr);
	gr.dispose();
	
	Image i = img.getScaledInstance(lhW, lhH, BufferedImage.SCALE_AREA_AVERAGING);
	
	gr = centerPane.getGraphics();
	gr.drawImage(i, 0, 0, lhW*wScale, lhH*hScale, null);
	gr.dispose();
	
	
    }
    
    
}
