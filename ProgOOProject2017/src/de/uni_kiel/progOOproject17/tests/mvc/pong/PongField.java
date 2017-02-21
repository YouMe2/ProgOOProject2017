package de.uni_kiel.progOOproject17.tests.mvc.pong;

import de.uni_kiel.progOOproject17.tests.mvc.abst.m.TickedDataModelContainer;

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

}
