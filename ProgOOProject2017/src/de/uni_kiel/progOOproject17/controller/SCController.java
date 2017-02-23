/**
 * 
 */
package de.uni_kiel.progOOproject17.controller;

import de.uni_kiel.progOOproject17.controller.abs.TickedController;
import de.uni_kiel.progOOproject17.model.abs.TickedDataModel;
import de.uni_kiel.progOOproject17.view.abs.InputView;
import de.uni_kiel.progOOproject17.view.abs.OutputView;

/**
 * @author Yannik Eikmeier
 * @since 23.02.2017
 *
 */
public class SCController extends TickedController{

    /**
     * @param out
     * @param in
     * @param model
     */
    public SCController(OutputView out, InputView in, TickedDataModel model) {
	super(out, in, model);
	// TODO Auto-generated constructor stub
    }

    /* (non-Javadoc)
     * @see de.uni_kiel.progOOproject17.controller.abs.TickedController#getModel()
     */
    @Override
    public TickedDataModel getModel() {
	// TODO Auto-generated method stub
	return null;
    }

}
