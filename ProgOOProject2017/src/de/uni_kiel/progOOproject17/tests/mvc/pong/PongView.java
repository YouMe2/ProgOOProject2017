package de.uni_kiel.progOOproject17.tests.mvc.pong;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import de.uni_kiel.progOOproject17.tests.mvc.abst.v.FramedIOView;
import de.uni_kiel.progOOproject17.tests.mvc.abst.v.Viewable;

public class PongView extends FramedIOView {

    private BufferedImage img;

    public PongView(String title, int w, int h) {
	super(title, w, h, true);
	img = new BufferedImage(w, h, BufferedImage.TYPE_3BYTE_BGR);
    }

   
    @Override
    public void render(Viewable viewable) {
	
	Graphics gr = img.getGraphics();
	viewable.render(gr);
	gr.dispose();
	gr = centerPane.getGraphics();
	gr.drawImage(img, 0, 0,centerPane.getWidth(), centerPane.getHeight(), null);
	gr.dispose();
	
	
    }

}
