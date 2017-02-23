package de.uni_kiel.progOOproject17.tests.mvc.pong;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import de.uni_kiel.progOOproject17.tests.mvc.abst.m.TickedDataModel;
import de.uni_kiel.progOOproject17.tests.mvc.abst.m.TickedDataModelContainer;
import de.uni_kiel.progOOproject17.tests.mvc.abst.v.Viewable;

public class PongField extends TickedDataModel {

    public int h;
    public int w;
    public int points1 = 0;
    public int points2 = 0;
    
    private final Viewable view = new Viewable() {
	    
	    @Override
	    public void render(Graphics gr) {
		
		gr.setColor(Color.GREEN);
		gr.fillRect(0, 0, w, h);
		
		gr.setColor(Color.BLACK);
		gr.drawLine(w/2, 0, w/2, h);
		
		gr.setFont(new Font(null, Font.BOLD, 20));
		gr.drawString(" " + points1, w/2 - 70, 60);
		gr.drawString(" " + points2, w/2 + 45, 60);
	    }
	};
    
    public PongField(int w, int h) {
	this.h = h;
	this.w = w;
    }

    
    @Override
    public void tick(long timestamp) {
	// nothing to tick here... its just a field/background
    }       

    
    @Override
    public Viewable getViewable() {
	
	return view;
    }

}
