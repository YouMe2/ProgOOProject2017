package de.uni_kiel.progOOproject17.tests.mvc.pong;

import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.AbstractAction;

import de.uni_kiel.progOOproject17.tests.mvc.abst.FramedIOView;
import de.uni_kiel.progOOproject17.tests.mvc.abst.TickedController;
import de.uni_kiel.progOOproject17.tests.mvc.abst.TickedDataModel;

/**
 * This is a sort of quick and dirty Pong Game based on the AbstarctMVC Classes:
 * {@link FramedIOView}, {@link TickedController} and {@link TickedDataModel}.
 * 
 * @author Yannik Eikmeier
 *
 */
public class PongController extends TickedController {

    private PongController c;
    private PongModel m;

    public static void main(String[] args) {

	PongView v = new PongView("MiniPong - YaAlex", 450, 300);
	PongModel m = new PongModel(450, 300);
	new PongController(v, m).start();
	

    }

    public PongController(PongView view, TickedDataModel model) {
	super(view, view, model);
	c = this;
	m = (PongModel) model;

	view.addWindowListener(new WindowAdapter() {

	    @Override
	    public void windowClosing(WindowEvent e) {

		c.stop();
		System.exit(0);
		super.windowClosing(e);
	    }

	});

	standardIn.addKeyAction("pressed SPACE", new AbstractAction() {

	    @Override
	    public void actionPerformed(ActionEvent e) {

		c.stop();
		standardIn.setEnabeled("pressed SPACE", false);

	    }
	});

	standardIn.addKeyAction("released SPACE", new AbstractAction() {

	    @Override
	    public void actionPerformed(ActionEvent e) {

		c.start();
		standardIn.setEnabeled("pressed SPACE", true);

	    }
	});
	
	standardIn.addKeyAction("pressed F", new AbstractAction() {

	    @Override
	    public void actionPerformed(ActionEvent e) {

		c.addOutputView(new PongView("test", m.field.w, m.field.h));

	    }
	});

	standardIn.addKeyAction("pressed W", new AbstractAction() {

	    @Override
	    public void actionPerformed(ActionEvent e) {
		standardIn.setEnabeled("released W", true);
		standardIn.setEnabeled("released S", false);
		m.field.bar1.moving = Bar.UP;	//TODO also these parts of getting the change in to the model is NOT beautiful yet... 

	    }
	});

	standardIn.addKeyAction("released W", new AbstractAction() {

	    @Override
	    public void actionPerformed(ActionEvent e) {

		m.field.bar1.moving = Bar.NONE;

	    }
	});

	standardIn.addKeyAction("pressed S", new AbstractAction() {

	    @Override
	    public void actionPerformed(ActionEvent e) {
		standardIn.setEnabeled("released S", true);
		standardIn.setEnabeled("released W", false);
		m.field.bar1.moving = Bar.DOWN;

	    }
	});

	standardIn.addKeyAction("released S", new AbstractAction() {

	    @Override
	    public void actionPerformed(ActionEvent e) {

		m.field.bar1.moving = Bar.NONE;

	    }
	});

    }

}
