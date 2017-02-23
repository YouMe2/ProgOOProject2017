/**
 * 
 */
package de.uni_kiel.progOOproject17.tests.mvc.pong;

import de.uni_kiel.progOOproject17.tests.mvc.abst.m.TickedDataModel;
import de.uni_kiel.progOOproject17.tests.mvc.abst.v.Viewable;

/**
 * @author Yannik Eikmeier
 * @since 22.02.2017
 *
 */
public class PongBarBot extends TickedDataModel{

    private Ball ball;
    private Bar bar;
    private long barBotLatency = 180;// ms
    private long lastTickTime = 0;
    
    /**
     * 
     */
    public PongBarBot(Ball ball, Bar bar) {
	this.ball = ball;
	this.bar = bar;
    }
    
    @Override
    public void tick(long timestamp) {
	
	if (timestamp > lastTickTime + barBotLatency || timestamp < lastTickTime) {
	    lastTickTime = timestamp;

	    if (ball.y > bar.y + bar.h / 2)
		bar.moveDOWN.actionPerformed(null);
	    else if (ball.y < bar.y + bar.h / 2)
		bar.moveUP.actionPerformed(null);
	    else
		bar.moveNONE.actionPerformed(null);
	}
    }


    @Override
    public Viewable getViewable() {
	return null;
    }

    
    
}
