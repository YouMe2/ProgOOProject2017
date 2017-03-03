package de.uni_kiel.progOOproject17.view.abs;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.HashMap;
import java.util.Set;

import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;

import de.uni_kiel.progOOproject17.model.abs.AbstractBaseModel;

public abstract class FramedIOView extends JFrame implements InputView, OutputView {

	private MappedKeyInputView in;

	protected JPanel centerPane;

	private AbstractBaseModel model;

	private HashMap<String, JButton> buttons = new HashMap<>();

	public FramedIOView(String title, int w, int h, boolean resizeable) {
		super(title);

		in = new MappedKeyInputView();
		centerPane = new JPanel(true);
		centerPane.setPreferredSize(new Dimension(w, h));
		in.initMaps(centerPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW), centerPane.getActionMap());

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		// this.add(centerPane, BorderLayout.CENTER);
		setContentPane(centerPane);

		setResizable(resizeable);
		setVisible(true);
		pack();
		setLocationRelativeTo(null);
	}

	public void addJButton(JButton b, JComponent comp, Object constraints) {
		if (b == null || comp == null)
			return;
		comp.add(b, constraints);
		buttons.put(b.getName(), b);
		assert buttons.containsKey("TEST") : "button not added??";

	}

	@Override
	public void addAction(String key, Action action) {

		if (buttons.get(key) != null)
			buttons.get(key).addActionListener(action);
		// .setAction(action);
		else
			in.addAction(key, action);
	}

	/*
	 * currently only for key events!
	 */
	@Override
	public void addActionMap(ActionMap actionMap) {
		in.addActionMap(actionMap);

	}

	@Override
	public void setEnabeled(boolean enabeled) {

		Set<String> keys = buttons.keySet();
		for (String key : keys)
			buttons.get(key).setEnabled(false);
		in.setEnabeled(enabeled);
	}

	@Override
	public void setEnabeled(String key, boolean enabeled) {

		if (buttons.get(key) != null)
			buttons.get(key).setEnabled(enabeled);
		else
			in.setEnabeled(key, enabeled);
	}

}
