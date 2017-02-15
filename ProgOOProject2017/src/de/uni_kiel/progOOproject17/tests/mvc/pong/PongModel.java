package de.uni_kiel.progOOproject17.tests.mvc.pong;

import de.uni_kiel.progOOproject17.tests.mvc.abst.TickedDataModel;

public class PongModel extends TickedDataModel {

    public PongField field;
    public Bar bar1;
    public Bar bar2;
    public Ball ball;

    private long botlatency = 180;//ms
    private long lastTickTime = 0;
    
    public PongModel(int maxW, int maxH) {
	field = new PongField(maxW, maxH);
	bar1 = new Bar(0, maxH / 2, 10, 50, field);
	bar2 = new Bar(maxW - 10, maxH / 2, 10, 50, field);

	ball = new Ball(6, field, bar1, bar2);
    }

    @Override
    public void tick(long timestamp) {

	ball.tick();
	bar1.tick();

	
	
	if (timestamp > lastTickTime + botlatency || timestamp < lastTickTime) {
	    lastTickTime = timestamp;
	    
	    if (ball.y > bar2.y + bar2.h / 2)
		bar2.moving = Bar.DOWN;
	    else if (ball.y < bar2.y + bar2.h / 2)
		bar2.moving = Bar.UP;
	    else
		bar2.moving = bar1.NONE;
	}
	bar2.tick();

    }
    
    

}
