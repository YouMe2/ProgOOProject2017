package de.uni_kiel.progOOproject17.tests.mvc.pong;

import de.uni_kiel.progOOproject17.tests.mvc.abst.TickedDataModel;

public class Bar extends TickedDataModel {

    public int x;
    public int y;
    
    public int w;
    public int h;
    
    private PongField f;
    
    private int speed = 3;
    
    public int moving = NONE;
    
    
    
    public static final int UP = -1;
    public static final int DOWN = 1;
    public static final int NONE = 0;
        
    public Bar(int x, int y, int w, int h, PongField f) {
	this.x = x;
	this.y = y;
	this.w = w;
	this.h = h;
	this.f = f;
    }
    
    public void tick(long timestamp){
	if(moving == NONE)
	    return;
	if(y + h + (moving * speed) < f.h && y + (moving * speed) > 0) {
	    
	    y += (moving * speed);
	    
	}
    }

}
