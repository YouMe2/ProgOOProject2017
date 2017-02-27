/**
 * 
 */
package de.uni_kiel.progOOproject17.model;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import de.uni_kiel.progOOproject17.view.abs.ImageViewable;
import de.uni_kiel.progOOproject17.view.abs.Viewable;

/**
 * @author Yannik Eikmeier
 * @since 23.02.2017
 *
 */
public abstract class GameObject extends GameComponent implements Collidable, Destroyable, Deadly {

	public static final LinkedList<GameObject> OBJECTS = new LinkedList<>();

	private boolean alive = true;
	
	private Viewable view;
	
	public GameObject(int x, int y, int w, int h) {
		super(x, y, w, h);
		OBJECTS.add(this);
	}
	
	public void setNewImageView(Image hi, Image low){
		view = new ImageViewable(hi, low, this.getBoundingRect());
		
	}

	@Override
	public boolean collides(GameObject obj) {
		if (obj == this)
			return false;
		return this.getBoundingRect().intersects(obj.getBoundingRect());
	}

	@Override
	public boolean willCollide(GameObject obj, int dx, int dy) {
		if (obj == this)
			return false;
		Rectangle rect = this.getBoundingRect();
		rect.translate(dx, dy);
		return rect.intersects(obj.getBoundingRect());

	}

	public boolean willCollide(List<GameObject> gObjts, int dx, int dy) {

		synchronized (gObjts) {
			for (GameObject gameObject : gObjts) {

				if (willCollide(gameObject, dx, dy))
					return true;
			}
		}

		return false;
	}

	@Override
	public Dimension getCollisionDistance(GameObject obj, int max_dx, int max_dy) {

		
		
		if (max_dx == 0 && max_dy == 0)
			return new Dimension(0, 0);

		if (collides(obj))
			return new Dimension(0, 0);

		if (!willCollide(obj, max_dx, max_dy)) //deckt den fall das obj == this gilt ab
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

	public Dimension getCollisionDistance(List<GameObject> gObjects, int max_dx, int max_dy) {

		if (max_dx == 0 && max_dy == 0) {
			return new Dimension(0, 0);
		}

		ArrayList<GameObject> collObjts = new ArrayList<>();

		synchronized (gObjects) {
			for (GameObject comp : gObjects) {

				if (collides(comp)) {
					return new Dimension(0, 0);
				}

				if (willCollide(comp, max_dx, max_dy)) { //deckt den fall das obj == this ab.
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
	public ArrayList<GameObject> getCollObjects(List<GameObject> gObjs, int dx, int dy) {

		ArrayList<GameObject> collObjts = new ArrayList<>();

		synchronized (gObjs) {
			for (GameObject obj : gObjs) {

				if (collides(obj)) {
					collObjts.add(obj);
				}

				if (willCollide(obj, dx, dy)) { //deckt den fall obj == this ab
					collObjts.add(obj);
				}
			}
		}

		return collObjts;

	}
	
	@Override
	public void destroy() {
		alive = false;
		OBJECTS.remove(this);
		
	}
	
	@Override
	public boolean isAlive() {
		return alive;
	}
	
	@Override
	public Viewable getViewable() {
		return view;
	}

}
