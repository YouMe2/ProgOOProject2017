/**
 *
 */
package de.uni_kiel.progOOproject17.model;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Yannik Eikmeier
 * @since 23.02.2017
 */
public interface Collidable {

	public boolean collides(GameObject obj);

	public boolean willCollide(GameObject obj, Dimension dist);

	public boolean willCollide(List<GameObject> gObjts, Dimension dist);

	public Dimension getCollisionDistance(GameObject obj, Dimension maxDist);

	public Dimension getCollisionDistance(List<GameObject> gObjects,
			Dimension maxDist);

	public ArrayList<GameObject> getCollObjects(List<GameObject> gObjs,
			Dimension dist);

}
