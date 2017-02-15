package de.uni_kiel.progOOproject17.tests.mvc.pong;

import de.uni_kiel.progOOproject17.tests.mvc.abst.TickedDataModel;

public class PongModel extends TickedDataModel {

    
    
    public PongField field;

    private long barBotLatency = 180;//ms
    private long lastTickTime = 0;
    
    public PongModel(int maxW, int maxH) {
	field = new PongField(maxW, maxH);		
	subData.add(field);	
    }

    @Override
    public void tick(long timestamp) {

	if (timestamp > lastTickTime + barBotLatency || timestamp < lastTickTime) {
	    lastTickTime = timestamp;
	    
	    if (field.ball.y > field.bar2.y + field.bar2.h / 2)
		field.bar2.moving = Bar.DOWN;
	    else if (field.ball.y < field.bar2.y + field.bar2.h / 2)
		field.bar2.moving = Bar.UP;
	    else
		field.bar2.moving = field.bar1.NONE;
	}

	super.tick(timestamp);
    }
    
    

}
