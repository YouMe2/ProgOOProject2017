package de.uni_kiel.progOOproject17.tests.mvc.abst.c;

import de.uni_kiel.progOOproject17.tests.mvc.abst.m.Ticked;
import de.uni_kiel.progOOproject17.tests.mvc.abst.m.TickedDataModelContainer;
import de.uni_kiel.progOOproject17.tests.mvc.abst.v.InputView;
import de.uni_kiel.progOOproject17.tests.mvc.abst.v.OutputView;

public abstract class TickedController extends AbstractController implements Runnable, Ticked{

    private Thread thread;
    private boolean runnig = false;
    private int ticklength = 20;//ms
    private long gametime = 0;
    
    public TickedController(OutputView out, InputView in, TickedDataModelContainer model, int ticklength) {
	super(out, in, model);
	this.ticklength = ticklength;
    }
    
    public TickedController(OutputView out, InputView in, TickedDataModelContainer model) {
	super(out, in, model);
    }

    @Override
    public void run() {

	// time allways in ms!
	
	
	long oldTime = System.currentTimeMillis();
	long timeAccumulator = 0;

	
	
	while (runnig) {

	    long newTime = System.currentTimeMillis();
	    long frameTime = newTime - oldTime;
	    oldTime = newTime;

	    timeAccumulator += frameTime; // this is how much time the game
					  // has to calculate ticks for

	    if (timeAccumulator >= ticklength) {

		while (timeAccumulator >= ticklength) {
//		    System.out.println("tick");
		    tick(gametime);
		    timeAccumulator -= ticklength;
		    gametime += ticklength;
		}
//		System.out.println("render");
		renderAllViews();
	    }

	}
    }

    public void start() {
	if (thread == null) {
	    thread = new Thread(this);
	    runnig = true;
	    gametime = 0;
	    thread.start();
	}
    }

    public void stop() {
	runnig = false;
	thread = null;
    }
    
    public void tick(long timestamp) {
	getModel().tick(timestamp);
    }
    
    @Override
    public abstract TickedDataModelContainer getModel();

}
