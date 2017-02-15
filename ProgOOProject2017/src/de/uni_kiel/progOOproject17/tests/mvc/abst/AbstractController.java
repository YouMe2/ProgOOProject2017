package de.uni_kiel.progOOproject17.tests.mvc.abst;

import java.util.ArrayList;

public abstract class AbstractController {

    public final OutputView standardOut;
    private ArrayList<OutputView> outs = new ArrayList<>();

    public final InputView standardIn;
    private ArrayList<InputView> ins = new ArrayList<>(); // nicht unebdingt
							  // notwending

    private AbstractDataModel model;

    public AbstractController(OutputView out, InputView in, AbstractDataModel model) {
	this.standardOut = out;
	this.standardIn = in;
	this.model = model;
	addOutputView(standardOut);
	addInputView(standardIn);
    }

    public void addOutputView(OutputView out) {
	outs.add(out);
	// out.setDataModel(model);
    }

    public void addInputView(InputView in) {
	ins.add(in);
    }

    public void setEnabledAll(boolean enabeled) {
	synchronized (ins) {

	    for (InputView in : ins) {
		in.setEnabeled(enabeled);
	    }
	}

    }

    public void setEnabledAll(String actionKey, boolean enabeled) {
	synchronized (ins) {

	    for (InputView in : ins) {
		in.setEnabeled(actionKey, enabeled);
	    }
	}

    }

    public void rederAllViews() {
	synchronized (outs) {
	    for (OutputView out : outs) {
		out.render(model);
	    }
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

}
