package de.uni_kiel.progOOproject17.mvctest.mvc;

import java.awt.event.ActionEvent;
import java.util.ArrayList;

import javax.swing.AbstractAction;

public abstract class AbstractController {

    public final OutputView standardOut;
    private ArrayList<OutputView> outs = new ArrayList<>();
    public final InputView standardIn;
    private ArrayList<InputView> ins = new ArrayList<>(); // nicht unebdingt notwending
    private AbstractDataModel model;

    public AbstractController(OutputView out, InputView in, AbstractDataModel model) {
	this.standardOut = out;
	addOutputView(standardOut);
	this.standardIn = in;
	addInputView(standardIn);
	this.model = model;
    }

    public void addOutputView(OutputView out) {
	outs.add(out);
    }

    public void addInputView(InputView in) {
	ins.add(in);
    }

    public void setEnabledAll(boolean enabeled) {
	for (InputView in : ins) {
	    in.setEnabeled(enabeled);
	}

    }
    
    public void setEnabledAll(String actionKey, boolean enabeled) {
   	for (InputView in : ins) {
   	    in.setEnabeled(actionKey, enabeled);   	}

       }
    
    
    public void updateAllViews(AbstractDataModel m) {
	for (OutputView out : outs) {
	    out.updateView(m);
	}
    }

    // GETTERS & SETTERS


    public OutputView getOutputView(int i) {
	return outs.get(i);
    }

    public InputView getInputView(int i) {
	return ins.get(i);
    }

    public AbstractDataModel getModel() {
	return model;
    }

    public void setModel(AbstractDataModel model) {
	this.model = model;
    }
}
