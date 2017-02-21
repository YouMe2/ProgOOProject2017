package de.uni_kiel.progOOproject17.tests.mvc.pong;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import de.uni_kiel.progOOproject17.tests.mvc.abst.m.TickedDataModelContainer;
import de.uni_kiel.progOOproject17.tests.mvc.abst.v.Viewable;

public class PongField extends TickedDataModelContainer {

    public int h;
    public int w;
   
    public Bar bar1;
    public Bar bar2;
    public Ball ball;
  
    public int points1 = 0;
    public int points2 = 0;
    
    public PongField(int w, int h) {
	this.h = h;
	this.w = w;
	
	bar1 = new Bar(0, h / 2, 10, 50, this);
	bar2 = new Bar(w - 10, h / 2, 10, 50, this);
	ball = new Ball(6, this, bar1, bar2);

	subData.add(ball);
	subData.add(bar1);
	subData.add(bar2);
    }


    
    @Override
    public Viewable getViewable() {
	
	return new Viewable() {
	    
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
    }       

}
