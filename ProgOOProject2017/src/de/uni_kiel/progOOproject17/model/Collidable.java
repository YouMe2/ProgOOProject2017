/**
 * 
 */
package de.uni_kiel.progOOproject17.model;

import java.awt.Dimension;
import java.awt.Point;
import java.util.ArrayList;

/**
 * @author Yannik Eikmeier
 * @since 23.02.2017
 *
 */
public interface Collidable {

    public boolean collides(GameComponent obj);
    
    public boolean willCollide(GameComponent obj, int dx, int dy);
    
    public Dimension getCollisionDistance(GameComponent obj, int dx, int dy);
    
    public Dimension getCollisionDistance(ArrayList<GameObject> gObjs, int dx, int dy);
    
}
