package de.uni_kiel.progOOproject17.tests.mvc.pong;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import de.uni_kiel.progOOproject17.tests.mvc.abst.m.ModelAction;
import de.uni_kiel.progOOproject17.tests.mvc.abst.m.TickedDataModel;
import de.uni_kiel.progOOproject17.tests.mvc.abst.v.Viewable;

public class Bar extends TickedDataModel {

    public int x;
    public int y;

    public int w;
    public int h;

    private PongField f;

    private int speed = 3;

    // public final ModelAction moveUP = new ModelAction("move bar UP") {
    public final AbstractAction moveUP = new AbstractAction("move bar UP") {
	@Override
	public void actionPerformed(ActionEvent e) {
	    moving = UP;
	}
    };

    // public final ModelAction moveDOWN = new ModelAction("move bar DOWN") {
    public final AbstractAction moveDOWN = new ModelAction("move bar DOWN") {

	@Override
	public void actionPerformed(ActionEvent e) {
	    moving = DOWN;
	}
    };

    // public final ModelAction moveNONE = new ModelAction("move bar NONE") {
    public final AbstractAction moveNONE = new ModelAction("move bar NONE") {

	@Override
	public void actionPerformed(ActionEvent e) {
	    moving = NONE;
	}
    };

    private final Viewable view = new Viewable() {

	@Override
	public void render(Graphics gr) {

	    gr.setColor(Color.BLACK);
	    gr.fillRect(x, y, w, h);

	}
    };

    public static final int UP = -1;
    public int moving = NONE;
    public static final int DOWN = 1;
    public static final int NONE = 0;

    public Bar(int x, int y, int w, int h, PongField f) {
	this.x = x;
	this.y = y;
	this.w = w;
	this.h = h;
	this.f = f;

    }

    public void tick(long timestamp) {
	if (moving == NONE)
	    return;
	if (y + h + (moving * speed) < f.h && y + (moving * speed) > 0) {

	    y += (moving * speed);

	}
    }

    @Override
    public Viewable getViewable() {
	return view;
    }

}
