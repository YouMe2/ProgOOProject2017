package de.uni_kiel.progOOproject17.tests.mvc.pong;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

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
	
	JMenuBar mb = new JMenuBar();
	this.setJMenuBar(mb);
	
	JButton testB = new JButton("TEST");
	testB.setName("TEST");
	addJButton(testB, mb, null);
	pack();
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
