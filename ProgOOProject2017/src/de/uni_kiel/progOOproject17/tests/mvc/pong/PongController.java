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

    public PongController(PongView view, PongModel model) {
	super(view, view, model);


	standardIn.addKeyAction("pressed SPACE", new AbstractAction() {

	    @Override
	    public void actionPerformed(ActionEvent e) {

		PongController.this.stop();
		standardIn.setEnabeled("pressed SPACE", false);

	    }
	});

	standardIn.addKeyAction("released SPACE", new AbstractAction() {

	    @Override
	    public void actionPerformed(ActionEvent e) {

		PongController.this.start();
		standardIn.setEnabeled("pressed SPACE", true);

	    }
	});

	standardIn.addKeyAction("pressed F", new AbstractAction() {

	    @Override
	    public void actionPerformed(ActionEvent e) {

		PongController.this
			.addOutputView(new PongView("Output-Test-Frame", getModel().field.w, getModel().field.h));

	    }
	});

	standardIn.addKeyAction("pressed W", new AbstractAction() {

	    @Override
	    public void actionPerformed(ActionEvent e) {
		standardIn.setEnabeled("released W", true);
		standardIn.setEnabeled("released S", false);
		getModel().field.bar1.moving = Bar.UP; // TODO also these parts
						       // of getting the change
						       // in to the model is NOT
						       // beautiful yet...

	    }
	});

	standardIn.addKeyAction("released W", new AbstractAction() {

	    @Override
	    public void actionPerformed(ActionEvent e) {

		getModel().field.bar1.moving = Bar.NONE;

	    }
	});

	standardIn.addKeyAction("pressed S", new AbstractAction() {

	    @Override
	    public void actionPerformed(ActionEvent e) {
		standardIn.setEnabeled("released S", true);
		standardIn.setEnabeled("released W", false);
		getModel().field.bar1.moving = Bar.DOWN;

	    }
	});

	standardIn.addKeyAction("released S", new AbstractAction() {

	    @Override
	    public void actionPerformed(ActionEvent e) {

		getModel().field.bar1.moving = Bar.NONE;

	    }
	});

    }

    @Override
    public PongModel getModel() {
	return (PongModel) model;
    }

}
