/**
 * 
 */
package de.uni_kiel.progOOproject17.model;

import java.awt.Dimension;
import java.awt.Point;
import java.lang.reflect.GenericArrayType;
import java.util.ArrayList;

import de.uni_kiel.progOOproject17.view.abs.Viewable;

/**
 * @author Yannik Eikmeier
 * @since 24.02.2017
 *
 */
public abstract class GameEntity extends GameObject implements Gravitational{

    public static final ArrayList<GameEntity> ENTITIES = new ArrayList<>();
    
    
    private int xVelocity;
    private int yVelocity;
    
    
    
    /**
     * @param x
     * @param y
     * @param w
     * @param h
     */
    public GameEntity(int x, int y, int w, int h) {
	super(x, y, w, h);
	ENTITIES.add(this);
    }
    
    @Override
    public void applyGravity() {
	
	Dimension dis = getCollisionDistance(OBJECTS, xVelocity, GRAVITY_ACCELERATION + yVelocity);
	translate(dis.width, dis.height);
	
    }

    

}
