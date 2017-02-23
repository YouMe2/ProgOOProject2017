/**
 * 
 */
package de.uni_kiel.progOOproject17.controller;

import de.uni_kiel.progOOproject17.controller.abs.TickedController;
import de.uni_kiel.progOOproject17.model.GameModel;
import de.uni_kiel.progOOproject17.view.SCDektopView;
import de.uni_kiel.progOOproject17.view.SCLighthouseView;

/**
 * @author Yannik Eikmeier
 * @since 23.02.2017
 *
 */
public class SCController extends TickedController{

    private SCLighthouseView lhView;
    private GameModel myModel;
    
 
    /**
     * @param view
     * @param model
     */
    public SCController(SCDektopView view, GameModel model) {
	super(view, view, model);
	toggelLighthouseView(); //turn on the Light house view
	
	
	//maybe add a button to toggel the lhView too?
	
	//init the actions for the standartIn (view)
	
	
	
	
	
    }
    
    private void toggelLighthouseView() {
	if( lhView == null) {
	    lhView =  new SCLighthouseView("LighthouseView", 450, 300);
	    this.addOutputView(lhView);
	    this.addInputView(lhView);
	    
	    //add Actions to lhView here
	    
	   
	}
	else
	    lhView.setVisible(!lhView.isVisible());		
    }

    @Override
    public GameModel getModel() {
	if (myModel == null)
	    myModel = (GameModel) model;
	return myModel;
    }

}
