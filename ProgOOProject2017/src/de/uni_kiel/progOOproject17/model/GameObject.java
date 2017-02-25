/**
 * 
 */
package de.uni_kiel.progOOproject17.model;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.util.ArrayList;

import de.uni_kiel.progOOproject17.view.abs.Viewable;

/**
 * @author Yannik Eikmeier
 * @since 23.02.2017
 *
 */
public abstract class GameObject extends GameComponent implements Collidable, Destroyable{

	// maybe a LinkedList? cuz removing in easy?
    public static final ArrayList<GameObject> OBJECTS = new ArrayList<>();
    

    
    public GameObject(int x, int y, int w, int h) {
	super(x, y, w, h);
	OBJECTS.add(this);
    }

    @Override
    public boolean collides(GameObject obj) {
	return this.getBoundingRect().intersects(obj.getBoundingRect());
    }

    @Override
	public boolean willCollide(GameObject obj, int dx, int dy) {
	Rectangle rect = this.getBoundingRect();
	rect.translate(dx, dy);
	return rect.intersects(obj.getBoundingRect());

    }

    @Override
    public Dimension getCollisionDistance(GameObject obj, int max_dx, int max_dy) {

	if (max_dx == 0 && max_dy == 0)
	    return new Dimension(0, 0);

	if (collides(obj))
	    return new Dimension(0, 0);

	if (!willCollide(obj, max_dx, max_dy))
	    return new Dimension(max_dx, max_dy);

	int xSign = (int) Math.signum(max_dx);
	int ySign = (int) Math.signum(max_dy);

	double bestProp = ((double) Math.abs(max_dx)) / Math.abs(max_dy);

	// sonst:

	Dimension currbestDist = new Dimension(0, 0);
	double currProp = 1;

	for (int dx = Math.abs(max_dx); dx >= 0; dx--) {
	    for (int dy = Math.abs(max_dy); dy >= 0; dy--) {

		if (!willCollide(obj, dx * xSign, dy * ySign)) {
		    // found a possible Distance!

		    if (dx + dy > currbestDist.width + currbestDist.height
			    || dx + dy == currbestDist.width + currbestDist.height
				    && Math.abs(bestProp - ((double) dx) / dy) < Math.abs(bestProp - currProp)) {
			// and it is better than the all the last ones

			currbestDist.setSize(dx, dy);
			currProp = ((double) dx) / dy;
		    }

		}

	    }
	}

	return new Dimension(currbestDist.width * xSign, currbestDist.height * ySign);
    }

    public Dimension getCollisionDistance(ArrayList<GameObject> gObjects, int max_dx, int max_dy) {

	if (max_dx == 0 && max_dy == 0) {
	    return new Dimension(0, 0);	    
	}
	
	ArrayList<GameObject> collObjts = new ArrayList<>();

	synchronized (gObjects) {
	    for (GameObject comp : gObjects) {
		
		if (collides(comp)) {
		    return new Dimension(0, 0);
		}
		
		if (willCollide(comp, max_dx, max_dy)) {
		    collObjts.add(comp);
		}
	    }
	}

	if (collObjts.isEmpty()) {
	    return new Dimension(max_dx, max_dy);
	}

	// sonst:

	int xSign = (int) Math.signum(max_dx);
	int ySign = (int) Math.signum(max_dy);

	double bestProp = ((double) Math.abs(max_dx)) / Math.abs(max_dy);

	Dimension currbestDist = new Dimension(0, 0);
	double currProp = 1;

	for (int dx = Math.abs(max_dx); dx >= 0; dx--) {
	    nextPos: for (int dy = Math.abs(max_dy); dy >= 0; dy--) {

		// für jede mögliche position:

		for (GameObject comp : collObjts) {
		    if (willCollide(comp, dx * xSign, dy * ySign))
			continue nextPos; // wenn collision mit nur einem
					  // anderen object -> nächtse pos

		}

		// sonst eine mögliche position gefunden!

		if (dx + dy > currbestDist.width + currbestDist.height
			|| dx + dy == currbestDist.width + currbestDist.height
				&& Math.abs(bestProp - ((double) dx) / dy) < Math.abs(bestProp - currProp)) {
		    // and it is better than the all the last ones

		    currbestDist.setSize(dx, dy);
		    currProp = ((double) dx) / dy;
		}

	    }
	}
	
	return new Dimension(currbestDist.width * xSign, currbestDist.height * ySign);

    }
    
    @Override
    public ArrayList<GameObject> getCollObjects(ArrayList<GameObject> gObjs, int dx, int dy) {
    	
    	ArrayList<GameObject> collObjts = new ArrayList<>();

    	synchronized (gObjs) {
    	    for (GameObject comp : gObjs) {
    		
    		if (collides(comp)) {
    		    collObjts.add(comp);
    		}
    		
    		if (willCollide(comp, dx, dy)) {
    		    collObjts.add(comp);
    		}
    	    }
    	}
    	
    	return collObjts;
    	
    }
    
    
}
