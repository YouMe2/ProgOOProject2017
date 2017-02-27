package de.uni_kiel.progOOproject17.tests.mvc.abst.c;

import java.util.ArrayList;
import java.util.Vector;

import de.uni_kiel.progOOproject17.tests.mvc.abst.m.AbstractDataModel;
import de.uni_kiel.progOOproject17.tests.mvc.abst.v.InputView;
import de.uni_kiel.progOOproject17.tests.mvc.abst.v.OutputView;

public abstract class AbstractController {

	public final OutputView standardOut;
	private Vector<OutputView> outs = new Vector<>();

	public final InputView standardIn;
	private Vector<InputView> ins = new Vector<>(); // nicht unbedingt
	// notwending

	protected final AbstractDataModel model;

	public AbstractController(OutputView out, InputView in, AbstractDataModel model) {
		this.standardOut = out;
		this.standardIn = in;
		this.model = model;
		addOutputView(standardOut);
		addInputView(standardIn);
	}

	public void addOutputView(OutputView out) {
		outs.add(out);
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

	public void renderAllViews() {
		synchronized (outs) {

			for (OutputView out : outs) {
				out.render(model.getViewable());
			}
		}

	}

	// GETTERS & SETTERS

	public abstract AbstractDataModel getModel();

	public OutputView getOutputView(int i) {
		return outs.get(i);
	}

	public InputView getInputView(int i) {
		return ins.get(i);
	}

}
