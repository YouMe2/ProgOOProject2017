package de.uni_kiel.progOOproject17.tests.mvc.pong;

import de.uni_kiel.progOOproject17.tests.mvc.abst.m.TickedDataModelContainer;
import de.uni_kiel.progOOproject17.tests.mvc.abst.v.Viewable;
import de.uni_kiel.progOOproject17.tests.mvc.abst.v.ViewableData;

public class PongModel extends TickedDataModelContainer {

    public PongField field;

    private long barBotLatency = 180;// ms
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
		field.bar2.moving = Bar.NONE;
	}

	super.tick(timestamp);
    }

    @Override
    public Viewable getViewable() {
	
	ViewableData vD = new ViewableData();
	
	//layer1:
	vD.add(field.getViewable());
	
	//layer2:
	ViewableData layer2 = new ViewableData();
	layer2.add(field.ball.getViewable());
	layer2.add(field.bar1.getViewable());
	layer2.add(field.bar2.getViewable());
	vD.add(layer2);
	
	
	
	return vD;
    }

}
