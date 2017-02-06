package de.uni_kiel.progOOproject17.mvctest;

import java.awt.Color;
import java.awt.GraphicsConfiguration;
import java.awt.HeadlessException;

import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

import de.uni_kiel.progOOproject17.mvctest.mvc.InputView;
import de.uni_kiel.progOOproject17.mvctest.mvc.MappedInputView;
import de.uni_kiel.progOOproject17.mvctest.mvc.OutputView;

public class IOView extends JFrame implements OutputView, InputView {

    private MappedInputView in;

    private JPanel contentPane;

    public IOView(String title) {
	this.setTitle(title);
	in = new MappedInputView();
	contentPane = new JPanel();

	in.initMaps(contentPane.getInputMap(JPanel.WHEN_IN_FOCUSED_WINDOW), contentPane.getActionMap());
	contentPane.setBackground(Color.WHITE);

	this.setContentPane(contentPane);
	this.setSize(400, 300);
	this.setVisible(true);
	this.setLocationRelativeTo(null);
    }

    @Override
    public void updateView() {
	contentPane.setBackground(new Color((int) (Math.random() * 256 * 256 * 256)));
    }

    @Override
    public void addAction(String actionKey, Action action) {
	in.addAction(actionKey, action);

    }

    @Override
    public void setEnabeled(boolean enabeled) {
	in.setEnabeled(enabeled);

    }

    @Override
    public void setEnabeled(String actionKey, boolean enabeled) {
	in.setEnabeled(actionKey, enabeled);
	
    }

    @Override
    public void addActionMap(ActionMap actionMap) {
	in.addActionMap(actionMap);
	
    }

}
