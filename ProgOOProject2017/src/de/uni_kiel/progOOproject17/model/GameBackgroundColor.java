/**
 * 
 */
package de.uni_kiel.progOOproject17.model;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;

import de.uni_kiel.progOOproject17.view.abs.Viewable;

/**
 * @author Yannik Eikmeier
 * @since 23.02.2017
 *
 */
public class GameBackgroundColor extends GameComponent {

    private final Color color;

    private final Viewable view;

    public GameBackgroundColor() {
	this(Color.BLACK);
    }

    public GameBackgroundColor(Color c) {
	this(0, 0, c);
    }

    public GameBackgroundColor(int x, int y, Color c) {
	this(x, y, SCGameModel.GAME_WIDTH - x, SCGameModel.GAME_HEIGHT - y, c);
    }

    public GameBackgroundColor(int x, int y, int w, int h, Color c) {
	super(x, y, w, h);
	color = c;
	view = new Viewable() {

	    @Override
	    public void render(Graphics gr) {

		gr.setColor(color);
		gr.fillRect(getX(), getY(), getWidth(), getHeight());
		
		gr.setColor(Color.BLACK);
		gr.drawRect(getX(), getY(), getWidth(), getHeight());
		
	    }

	    @Override
	    public void renderLOW(Graphics gr) {
		gr.setColor(color);
		gr.fillRect(getX(), getY(), getWidth(), getHeight());


	    }
	};

    }
    
    
    @Override
    public void tick(long timestamp) {
	// TODO could be animated :D?
    }

    @Override
    public Viewable getViewable() {
	return view;
    }

}
