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

	public boolean willCollide(GameObject obj, int dx, int dy);

	public boolean willCollide(List<GameObject> gObjts, int dx, int dy);

	public Dimension getCollisionDistance(GameObject obj, int max_dx,
			int max_dy);

	public Dimension getCollisionDistance(List<GameObject> gObjects, int max_dx,
			int max_dy);

	public ArrayList<GameObject> getCollObjects(List<GameObject> gObjs, int dx,
			int dy);

}
