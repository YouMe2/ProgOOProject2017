/**
 *
 */
package de.uni_kiel.progOOproject17.model;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * @author Yannik Eikmeier
 * @since 23.02.2017
 */
public interface Collidable {

    public boolean collides(GameObject obj);

    public boolean willCollide(GameObject obj, Distance dist);

    public boolean willCollide(List<GameObject> gObjts, Distance dist);

    public Distance getCollisionDistance(GameObject obj, Distance maxDist);

    public Distance getCollisionDistance(List<GameObject> gObjts, Distance maxDist);
    
    public ArrayList<GameObject> getCollObjects(List<GameObject> gObjs, Distance dist);

    public boolean contacts(GameObject obj);
    
    public void forEachCollision(List<GameObject> gObjs, Distance dist, Consumer<GameObject> cons);
    
    public void forEachContact(List<GameObject> gObjs, Consumer<GameObject> cons);
}
