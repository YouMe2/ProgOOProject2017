package de.uni_kiel.progOOproject17.mvctest.mvc;

import java.awt.event.ActionEvent;
import java.util.ArrayList;

import javax.swing.AbstractAction;

public abstract class AbstractController {

    private OutputView standardOut;
    private ArrayList<OutputView> outs = new ArrayList<>();
    private InputView standardIn;
    private ArrayList<InputView> ins = new ArrayList<>();
    private AbstractModel model;

    public AbstractController(OutputView out, InputView in, AbstractModel model) {
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

    public void updateStandardView() {
	standardOut.updateView();
    }

    public void updateView(int i) {
	outs.get(i).updateView();
    }

    public void updateAllViews() {
	for (OutputView out : outs) {
	    out.updateView();
	}
    }

    // GETTERS & SETTERS

    public OutputView getStandardOut() {
	return standardOut;
    }

    public OutputView getOutputView(int i) {
	return outs.get(i);
    }

    public InputView getStandardIn() {
	return standardIn;
    }

    public InputView getInputView(int i) {
	return ins.get(i);
    }

    public AbstractModel getModel() {
	return model;
    }

    public void setModel(AbstractModel model) {
	this.model = model;
    }
}
