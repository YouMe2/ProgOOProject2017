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

    public boolean collides(GameObject obj);
    
    public boolean willCollide(GameObject obj, int dx, int dy);
    
    public Dimension getCollisionDistance(GameObject obj, int max_dx, int max_dy);
    
    public Dimension getCollisionDistance(ArrayList<GameObject> gObjects, int max_dx, int max_dy);
    
    public ArrayList<GameObject> getCollObjects(ArrayList<GameObject> gObjs, int dx, int dy);
    
}
