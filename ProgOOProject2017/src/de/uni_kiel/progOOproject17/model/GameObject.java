/**
 *
 */
package de.uni_kiel.progOOproject17.model;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

import de.uni_kiel.progOOproject17.view.abs.Viewable;

/**
 * @author Yannik Eikmeier
 * @since 23.02.2017
 */
public abstract class GameObject extends GameComponent implements Collidable, Destroyable, Deadly, Viewable {

	public static final LinkedList<GameObject> OBJECTS = new LinkedList<>();

	private boolean alive = true;

	private String resKey;
	private int layer;

	public GameObject(String resKey, int x, int y, int w, int h) {
		super(x, y, w, h);
		OBJECTS.add(this);
		setResKey(resKey);
		setLayer(0);

	}

	@Override
	public String getResourceKey() {
		return resKey;
	}

	@Override
	public Rectangle getViewRect() {
		return new Rectangle(getBoundingRect());
	}

	@Override
	public int getLayer() {
		return layer;
	}

	public void setResKey(String resKey) {
		this.resKey = resKey;
	}

	public void setLayer(int layer) {
		this.layer = layer;
	}

	/**
	 * safe
	 */
	@Override
	public boolean collides(GameObject obj) {
		if (obj == this)
			return false;

		return getBoundingRect().intersects(obj.getBoundingRect());
	}

	@Override
	public boolean contacts(GameObject obj) {
		if (obj == this)
			return false;

		Rectangle rect = getBoundingRect();
		rect.grow(1, 1);
		return rect.intersects(obj.getBoundingRect());
	}

	/**
	 * safe ...i guess will no longer make changes dist
	 */
	@Override
	public boolean willCollide(GameObject obj, Distance dist) {
		if (obj == this)
			return false;

		// added this sort of clone
		Rectangle rect = getBoundingRect();
		rect.translate(dist.x, dist.y);
		return rect.intersects(obj.getBoundingRect());
	}

	/**
	 * safe and better will no longer make changes dist
	 */
	@Override
	public boolean willCollide(List<GameObject> gObjts, Distance dist) {

		Rectangle rect = new Rectangle(getBoundingRect());
		rect.translate(dist.x, dist.y);

		synchronized (gObjts) {
			for (GameObject obj : gObjts) {
				if (obj == this)
					return false;

				if (rect.intersects(obj.getBoundingRect()))
					return true;
			}
		}

		return false;
	}

	/**
	 * fixed ... i hope so
	 */
	@Override
	public Distance getCollisionDistance(GameObject obj, Distance maxDist) {

		if (maxDist.x == 0 && maxDist.y == 0)
			return maxDist;

		if (collides(obj))
			return new Distance();

		// deckt den fall, dass (obj == this) gilt ab
		if (!willCollide(obj, maxDist))
			return new Distance(maxDist);

		// sonst:

		Distance signD = maxDist.getSignDistance();
		Distance absDist = maxDist.getAbsDistance();
		Distance currBestDist = new Distance(0, 0);

		for (int dx = absDist.x; dx >= 0; dx--)
			for (int dy = absDist.y; dy >= 0; dy--) {

				// für jede Distance im max bereich:

				Distance dist = new Distance(dx, dy);
				dist.multiply(signD);

				if (!willCollide(obj, dist) && dist.getSqLenghth() > currBestDist.getSqLenghth()) {
					// möglich und besser? dann:
					currBestDist = dist;
				}
			}

		return currBestDist;
	}

	/**
	 * safe not tested tho
	 * 
	 */
	@Override
	public Distance getCollisionDistance(List<GameObject> gObjts, Distance maxDist) {

		if (maxDist.x == 0 && maxDist.y == 0)
			return maxDist;

		ArrayList<GameObject> collObjts = new ArrayList<>();

		synchronized (gObjts) {
			for (GameObject obj : gObjts) {

				if (collides(obj))
					return new Distance(); // (0 ,0)

				if (willCollide(obj, maxDist))
					collObjts.add(obj);
			}
		}

		if (collObjts.isEmpty())
			return new Distance(maxDist);

		// sonst:

		Distance signD = maxDist.getSignDistance();
		Distance absDist = maxDist.getAbsDistance();
		Distance currBestDist = new Distance(0, 0);

		for (int dx = absDist.x; dx >= 0; dx--)
			nextPos: for (int dy = absDist.y; dy >= 0; dy--) {

				// für jede mögliche position:
				Distance dist = new Distance(dx, dy);
				dist.multiply(signD);

				for (GameObject obj : collObjts) {
					// wenn collision mit nur einem anderen object -> nächtse
					// pos
					if (willCollide(obj, dist))
						continue nextPos;
				}

				// sonst: eine mögliche Distance gefunden!

				if (dist.getSqLenghth() > currBestDist.getSqLenghth()) {
					// und sie ist besser!
					currBestDist = dist;
				}

			}

		return currBestDist;

	}

	@Override
	public ArrayList<GameObject> getCollObjects(List<GameObject> gObjs, Distance dist) {

		ArrayList<GameObject> collObjts = new ArrayList<>();

		synchronized (gObjs) {
			for (GameObject obj : gObjs) {

				if (collides(obj))
					collObjts.add(obj);

				if (willCollide(obj, dist))
					collObjts.add(obj);
			}
		}

		return collObjts;

	}

	public void forEachCollision(List<GameObject> gObjs, Distance dist, Consumer<GameObject> cons) {
		synchronized (gObjs) {
			for (GameObject obj : gObjs) {

				if (collides(obj) || willCollide(obj, dist))
					cons.accept(obj);
			}
		}
	}

	public void forEachContact(List<GameObject> gObjs, Consumer<GameObject> cons) {
		synchronized (gObjs) {
			for (GameObject obj : gObjs) {

				if (contacts(obj))
					cons.accept(obj);
			}
		}
	}

	@Override
	public void destroy() {
		if(isAlive()) {
			System.out.println("DESTROYED: " + this.resKey);
			
			alive = false;
			OBJECTS.remove(this);
			COMPONENTS.remove(this);
			
		}

	}
	
	@Override
	public void activate() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isAlive() {
		return alive;
	}

}
