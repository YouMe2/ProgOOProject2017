package de.uni_kiel.progOOproject17.tests.mvc.abst;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.JFrame;
import javax.swing.JPanel;

public abstract class FramedIOView extends JFrame implements InputView, OutputView {


    private MappedInputView in;

    protected JPanel centerPane;

    private AbstractDataModel model;
    
    public FramedIOView(String title, int w, int h, boolean resizeable){
	super(title);
	
	in = new MappedInputView();
	centerPane = new JPanel(true);
	centerPane.setPreferredSize(new Dimension(w, h));
	in.initMaps(centerPane.getInputMap(JPanel.WHEN_IN_FOCUSED_WINDOW), centerPane.getActionMap());
	
	
	this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
	this.setLayout(new BorderLayout()); //sets the layout of the window
	this.add(centerPane, BorderLayout.CENTER);  //Adds the game (which is a canvas) to the center of the screen.
	
	this.setResizable(resizeable);
	this.pack();
	this.setVisible(true);
	this.setLocationRelativeTo(null);

	
	
    }

    
    public AbstractDataModel getDataModel() {
	return model;
    }

    @Override
    public void addKeyAction(String key, Action action) {
	in.addKeyAction(key, action);
    }

    @Override
    public void addKeyActionMap(ActionMap actionMap) {
	in.addKeyActionMap(actionMap);

    }

    @Override
    public void setEnabeled(boolean enabeled) {
	in.setEnabeled(enabeled);

    }

    @Override
    public void setEnabeled(String key, boolean enabeled) {
	in.setEnabeled(key, enabeled);

    }

}
