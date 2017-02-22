package de.uni_kiel.progOOproject17.tests.mvc.pong;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import de.uni_kiel.progOOproject17.tests.mvc.abst.c.TickedController;
import de.uni_kiel.progOOproject17.tests.mvc.abst.m.TickedDataModel;
import de.uni_kiel.progOOproject17.tests.mvc.abst.v.FramedIOView;

/**
 * This is a sort of quick and dirty Pong Game based on the AbstarctMVC Classes:
 * {@link FramedIOView}, {@link TickedController} and {@link TickedDataModel}.
 * 
 * @author Yannik Eikmeier
 *
 */
public class PongController extends TickedController {

    public static void main(String[] args) {

	PongView v = new PongView("MiniPong - YaAlex", 450, 300);
	PongModel m = new PongModel(450, 300);
	new PongController(v, m).start();

    }

    private PongModel myModel;

    private PongLighthouseView lhView;
    
    public PongController(PongView view, PongModel model) {
	super(view, view, model /*, 18*/ );

//	setEnableTpsFpsPrint(true);
	
	standardIn.addAction("pressed SPACE", new AbstractAction() {

	    @Override
	    public void actionPerformed(ActionEvent e) {

		PongController.this.stop();
		standardIn.setEnabeled("pressed SPACE", false);

	    }
	});

	standardIn.addAction("released SPACE", new AbstractAction() {

	    @Override
	    public void actionPerformed(ActionEvent e) {

		PongController.this.start();
		standardIn.setEnabeled("pressed SPACE", true);

	    }
	});

	standardIn.addAction("pressed F", new AbstractAction() {

	    @Override
	    public void actionPerformed(ActionEvent e) {

		toggelLighthouseView();
		

	    }

	   
	});

	standardIn.addAction("pressed W", new AbstractAction() {

	    @Override
	    public void actionPerformed(ActionEvent e) {
		standardIn.setEnabeled("released W", true);
		standardIn.setEnabeled("released S", false);

		getModel().moveUP.actionPerformed(e);

	    }
	});

	standardIn.addAction("released W", new AbstractAction() {

	    @Override
	    public void actionPerformed(ActionEvent e) {

		getModel().moveNONE.actionPerformed(e);

	    }
	});

	standardIn.addAction("pressed S", new AbstractAction() {

	    @Override
	    public void actionPerformed(ActionEvent e) {
		standardIn.setEnabeled("released S", true);
		standardIn.setEnabeled("released W", false);

		getModel().moveDOWN.actionPerformed(e);
	    }
	});

	standardIn.addAction("released S", new AbstractAction() {

	    @Override
	    public void actionPerformed(ActionEvent e) {

		getModel().moveNONE.actionPerformed(e);

	    }
	});

	
	
    }
    
    private void toggelLighthouseView() {
	if( lhView == null) {
	    lhView =  new PongLighthouseView("LighthouseView", 450, 300);
	    this.addOutputView(lhView);
	    this.addInputView(lhView);
	    lhView.addAction("TEST", new AbstractAction("TEST") {
		    
		    @Override
		    public void actionPerformed(ActionEvent e) {
			System.out.println("test");
			
		    }
		});
	    
	}
	else
	    lhView.setVisible(!lhView.isVisible());		
    }

    @Override
    public PongModel getModel() {
	if (myModel == null)
	    myModel = (PongModel) model;
	return myModel;
    }

}
