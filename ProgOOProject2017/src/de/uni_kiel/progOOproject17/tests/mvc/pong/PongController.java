package de.uni_kiel.progOOproject17.tests.mvc.pong;

import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.AbstractAction;

import de.uni_kiel.progOOproject17.tests.mvc.abst.TickedController;
import de.uni_kiel.progOOproject17.tests.mvc.abst.TickedDataModel;

/**
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
	m = (PongModel)model;
	
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
	
	standardIn.addKeyAction("pressed W", new AbstractAction() {
	    
	    @Override
	    public void actionPerformed(ActionEvent e) {
		
		m.bar1.moving = Bar.UP;
		
	    }
	});
	
	standardIn.addKeyAction("released W", new AbstractAction() {
	    
	    @Override
	    public void actionPerformed(ActionEvent e) {
		
		m.bar1.moving = Bar.NONE;
		
	    }
	});
	
	standardIn.addKeyAction("pressed S", new AbstractAction() {
	    
	    @Override
	    public void actionPerformed(ActionEvent e) {
		
		m.bar1.moving = Bar.DOWN;
		
	    }
	});
	
	standardIn.addKeyAction("released S", new AbstractAction() {
	    
	    @Override
	    public void actionPerformed(ActionEvent e) {
		
		m.bar1.moving = Bar.NONE;
		
	    }
	});

    }

}
