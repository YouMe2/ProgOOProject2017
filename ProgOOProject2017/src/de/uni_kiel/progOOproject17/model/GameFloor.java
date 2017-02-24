/**
 * 
 */
package de.uni_kiel.progOOproject17.model;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;

import de.uni_kiel.progOOproject17.view.abs.Viewable;

/**
 * @author Yannik Eikmeier
 * @since 23.02.2017
 *
 */
public class GameFloor extends GameComponent {

    public static final ArrayList<GameFloor> FLOORS = new ArrayList<>();

    private Color color;
    private Image img;

    private final GameBackgroundImage bgImg;
    private final Viewable view;

    public GameFloor(int x, int y, int w, int h, Color c) {
	this(x, y, w, h, c, null);
    }

    public GameFloor(int x, int y, int w, int h, Color c, Image i) {
	super(x, y, w, h);
	FLOORS.add(this);
	color = c;
	img = i;

	bgImg = new GameBackgroundImage(x, y, w, h, i, c);

	view = bgImg.getViewable();

    }

    @Override
    public void tick(long timestamp) {
	// TODO animated?

    }

    @Override
    public Viewable getViewable() {
	return view;
    }

}
