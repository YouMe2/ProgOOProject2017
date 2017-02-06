package de.uni_kiel.progOOproject17.mvctest;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;

import de.uni_kiel.progOOproject17.mvctest.mvc.AbstractController;
import de.uni_kiel.progOOproject17.mvctest.mvc.AbstractModel;
import de.uni_kiel.progOOproject17.mvctest.mvc.InputView;
import de.uni_kiel.progOOproject17.mvctest.mvc.OutputView;

public class Controller extends AbstractController {

    private ActionMap actions;
    
    public static void main(String[] args) {

	IOView v = new IOView("Test");
	Model m = new Model();
	new Controller(v, v, m);

    }

    public Controller(OutputView out, InputView in, AbstractModel model) {
	super(out, in, model);

	actions = new ActionMap();
	actions.put("pressed A", new AbstractAction() {

	    @Override
	    public void actionPerformed(ActionEvent e) {
		updateAllViews();
		in.setEnabeled("pressed A", false);
	    }
	});

	actions.put("released A", new AbstractAction() {

	    @Override
	    public void actionPerformed(ActionEvent e) {
		in.setEnabeled("pressed A", true);
	    }
	});

	actions.put("pressed N", new AbstractAction() {

	    @Override
	    public void actionPerformed(ActionEvent e) {
		addOutputView(new OView("More O Tests"));

	    }
	});

	actions.put("ctrl pressed N", new AbstractAction() {

	    @Override
	    public void actionPerformed(ActionEvent e) {
		addNewIOView();
	    }
	});

	standardIn.addActionMap(actions);

    }

    public void addNewIOView() {
	IOView ioV = new IOView("More IO Tests");
	ioV.addActionMap(actions);
	addOutputView(ioV);
	addInputView(ioV);// nur der vollständigkeit halber
    }

}
