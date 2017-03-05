package de.uni_kiel.progOOproject17.controller.abs;

import de.uni_kiel.progOOproject17.model.abs.AbstractBaseModel;
import de.uni_kiel.progOOproject17.view.abs.InputView;
import de.uni_kiel.progOOproject17.view.abs.OutputView;
import de.uni_kiel.progOOproject17.view.abs.Viewable;
import java.util.Vector;

/**
 * This class provides the functionality of the most abstract controller an the
 * MVC structure, built on {@link InputView}, {@link OutputView},
 * {@link Viewable} and {@link AbstractBaseModel}.
 *
 *
 */
public abstract class AbstractController {

	/**
	 * The standard {@link OutputView} of this controller.
	 */
	protected final OutputView standardOut;
	private Vector<OutputView> outs = new Vector<>();

	/**
	 * The standard {@link InputView} of this controller.
	 */
	protected final InputView standardIn;
	private Vector<InputView> ins = new Vector<>();

	/**
	 * The {@link AbstractBaseModel} of this controller.
	 */
	protected final AbstractBaseModel model;

	/**
	 * Constructs a new {@link AbstractController} with out set as the
	 * {@link #standardOut}, in set as {@link #standardIn} and model set as
	 * {@link #model}.
	 *
	 * @param out
	 *            the {@link #standardOut}
	 * @param in
	 *            the {@link #standardIn}
	 * @param model
	 *            the {@link #model}
	 */
	public AbstractController(OutputView out, InputView in, AbstractBaseModel model) {
		standardOut = out;
		standardIn = in;
		this.model = model;
		addOutputView(standardOut);
		addInputView(standardIn);
	}

	/**
	 * Adds a new {@link OutputView} to this controller.
	 *
	 * @param out
	 *            the new {@link OutputView}
	 */
	public void addOutputView(OutputView out) {
		outs.add(out);
	}

	/**
	 * Adds a new {@link InputView} to this controller.
	 *
	 * @param in
	 */
	public void addInputView(InputView in) {
		ins.add(in);
	}

	/**
	 * Enables or disables all actions in all {@link InputView}s.
	 *
	 * @param enabeled
	 */
	public void setEnabledAll(boolean enabeled) {
		synchronized (ins) {

			for (InputView in : ins)
				in.setEnabeled(enabeled);
		}

	}

	/**
	 * Enabeles or disables the action specifyed by the actionkey in all
	 * {@link InputView}s.
	 *
	 * @param actionKey
	 *            the key specifying the action.
	 * @param enabeled
	 */
	public void setEnabledAll(String actionKey, boolean enabeled) {
		synchronized (ins) {

			for (InputView in : ins)
				in.setEnabeled(actionKey, enabeled);
		}

	}

	/**
	 * Renders all {@link InputView}s.
	 *
	 */
	public void renderAllViews() {
		synchronized (outs) {

			for (OutputView out : outs)
				out.render(model.getViewables());
		}

	}

	// GETTERS & SETTERS

	/**
	 * Returns the {@link AbstractBaseModel} of this controller.
	 *
	 * @return the model
	 */
	public abstract AbstractBaseModel getModel();

	/**
	 * Returns the specific {@link OutputView} with the index i.
	 *
	 * @param i
	 *            the index
	 * @return the {@link OutputView} at position i
	 */
	public OutputView getOutputView(int i) {
		return outs.get(i);
	}

	/**
	 * Returns the specific {@link InputView} with the index i.
	 *
	 * @param i
	 *            the index
	 * @return the {@link InputView} at position i
	 */
	public InputView getInputView(int i) {
		return ins.get(i);
	}

}
