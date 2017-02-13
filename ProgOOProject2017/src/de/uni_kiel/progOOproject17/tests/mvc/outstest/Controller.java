package de.uni_kiel.progOOproject17.tests.mvc.outstest;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;

import de.uni_kiel.progOOproject17.tests.mvc.abst.AbstractController;
import de.uni_kiel.progOOproject17.tests.mvc.abst.AbstractDataModel;
import de.uni_kiel.progOOproject17.tests.mvc.abst.InputView;
import de.uni_kiel.progOOproject17.tests.mvc.abst.OutputView;

public class Controller extends AbstractController {

    private ActionMap actions;
    
    public static void main(String[] args) {

	IOView v = new IOView("Test");
	Model m = new Model();
	new Controller(v, m);

    }

    public Controller(IOView view, Model model) {
	super(view, view, model);

	actions = new ActionMap();
	actions.put("pressed A", new AbstractAction() {

	    @Override
	    public void actionPerformed(ActionEvent e) {
		model.changeColor();
		rederAllViews();
		setEnabledAll("pressed A", false);
	    }
	});

	actions.put("released A", new AbstractAction() {

	    @Override
	    public void actionPerformed(ActionEvent e) {
		
		setEnabledAll("pressed A", true);
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

	standardIn.addKeyActionMap(actions);

    }

    public void addNewIOView() {
	IOView ioV = new IOView("More IO Tests");
	ioV.addKeyActionMap(actions);
	addOutputView(ioV);
	addInputView(ioV);// nur der vollständigkeit halber
    }

}
