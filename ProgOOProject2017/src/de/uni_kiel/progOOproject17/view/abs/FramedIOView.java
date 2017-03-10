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

/**
 * This class provides a {@link JFrame} which holds an {@link InputView} in as
 * well as a {@link MappedKeyInput} and a {@link OutputView}. This class
 * builds a basic Frame which can easily be modified by the subclass.
 *
 *
 *
 */
public abstract class FramedIOView extends JFrame implements InputView, OutputView {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2996189965279903424L;

	private MappedKeyInput in;

	/**
	 * The {@link JPanel} being the contentPane of this frame.
	 */
	protected JPanel contentPane;

	private HashMap<String, JButton> buttons = new HashMap<>();

	/**
	 * Constructs a new {@link FramedIOView}.
	 * 
	 * @param title
	 *            the title
	 * @param w
	 *            the width of the centerPane
	 * @param h
	 *            the height of the centerPane
	 * @param resizeable
	 *            whether the frame should be resizeable
	 */
	public FramedIOView(String title, int w, int h, boolean resizeable) {
		super(title);

		in = new MappedKeyInput();
		contentPane = new JPanel(true);
		contentPane.setPreferredSize(new Dimension(w, h));
		in.initMaps(contentPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW), contentPane.getActionMap());

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		setContentPane(contentPane);

		setResizable(resizeable);
		setVisible(true);
		pack();
		setLocationRelativeTo(null);
	}

	/**
	 * Adds a {@link JButton} b to the {@link JComponent} comp with the given
	 * constraints. And makes this button available for the internal
	 * {@link Action} handling.
	 * 
	 * @param b
	 *            the {@link JButton}
	 * @param comp
	 *            the {@link JComponent}
	 * @param constraints
	 *            the constraints object
	 */
	public void addJButton(JButton b, JComponent comp, Object constraints) {
		if (b == null || comp == null)
			return;
		comp.add(b, constraints);
		buttons.put(b.getName(), b);

	}

	/**
	 * Adds the action to the Input view, so that it will be performed when an
	 * event occurs that need to be specified by the String key. For example a
	 * keystroke or the name of a button.
	 * 
	 * @see de.uni_kiel.progOOproject17.view.abs.InputView#addAction(java.lang.String,
	 *      javax.swing.Action)
	 */
	@Override
	public void addAction(String key, Action action) {

		if (buttons.get(key) != null)
			buttons.get(key).addActionListener(action);
		else
			in.addAction(key, action);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.uni_kiel.progOOproject17.view.abs.InputView#addActionMap(javax.swing.
	 * ActionMap)
	 */
	@Override
	public void addActionMap(ActionMap actionMap) {
		in.addActionMap(actionMap);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.uni_kiel.progOOproject17.view.abs.InputView#setEnabeled(boolean)
	 */
	@Override
	public void setEnabeled(boolean enabeled) {

		Set<String> keys = buttons.keySet();
		for (String key : keys)
			buttons.get(key).setEnabled(false);
		in.setEnabeled(enabeled);
	}

	/* (non-Javadoc)
	 * @see de.uni_kiel.progOOproject17.view.abs.InputView#setEnabeled(java.lang.String, boolean)
	 */
	@Override
	public void setEnabeled(String key, boolean enabeled) {

		if (buttons.get(key) != null)
			buttons.get(key).setEnabled(enabeled);
		else
			in.setEnabeled(key, enabeled);
	}

}
