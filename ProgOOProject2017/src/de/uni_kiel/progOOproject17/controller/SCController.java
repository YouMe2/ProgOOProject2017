/**
 * 
 */
package de.uni_kiel.progOOproject17.controller;

import de.uni_kiel.progOOproject17.controller.abs.TickedController;
import de.uni_kiel.progOOproject17.model.SCDataModel;
import de.uni_kiel.progOOproject17.view.SCDektopView;
import de.uni_kiel.progOOproject17.view.SCLighthouseView;

/**
 * @author Yannik Eikmeier
 * @since 23.02.2017
 *
 */
public class SCController extends TickedController{

    private SCLighthouseView lhView;
    private SCDataModel myModel;
    
    /**
     * @param out
     * @param in
     * @param model
     */
    public SCController(SCDektopView view, SCDataModel model) {
	super(view, view, model);
	toggelLighthouseView(); //turn on the Light house view
	
	
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
    public SCDataModel getModel() {
	if (myModel == null)
	    myModel = (SCDataModel) model;
	return myModel;
    }

}
