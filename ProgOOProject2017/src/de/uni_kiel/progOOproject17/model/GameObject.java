/**
 *
 */
package de.uni_kiel.progOOproject17.model;

import de.uni_kiel.progOOproject17.view.abs.Viewable;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Yannik Eikmeier
 * @since 23.02.2017
 */
public abstract class GameObject extends GameComponent
		implements Collidable, Destroyable, Deadly, Viewable {

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
	public Rectangle getRect() {
		return getBoundingRect();
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

	@Override
	public boolean collides(GameObject obj) {
		if (obj == this)
			return false;
		return getBoundingRect().intersects(obj.getBoundingRect());
	}

	@Override
	public boolean willCollide(GameObject obj, Dimension dist) {
		if (obj == this)
			return false;
		Rectangle rect = getBoundingRect();
		rect.translate(dist.width, dist.height);
		return rect.intersects(obj.getBoundingRect());
	}

	@Override
	public boolean willCollide(List<GameObject> gObjts, Dimension dist) {

		synchronized (gObjts) {
			for (GameObject gameObject : gObjts)
				if (willCollide(gameObject, dist))
					return true;
		}

		return false;
	}

	@Override
	public Dimension getCollisionDistance(GameObject obj, Dimension maxDist) {

		if (maxDist.width == 0 && maxDist.height == 0)
			return new Dimension(0, 0);

		if (collides(obj))
			return new Dimension(0, 0);

		// deckt den fall das obj == this gilt ab
		if (!willCollide(obj, maxDist))
			return new Dimension(maxDist);

		int xSign = (int) Math.signum(maxDist.width);
		int ySign = (int) Math.signum(maxDist.height);

		int absWidth = Math.abs(maxDist.width);
		int absHeight = Math.abs(maxDist.height);

		double bestProp = (double) absWidth / absHeight;

		// sonst:

		Dimension currBestDist = new Dimension(0, 0);
		double currProp = 1;

		for (int dx = absWidth; dx >= 0; dx--)
			for (int dy = absHeight; dy >= 0; dy--) {
				Dimension dist = new Dimension(dx * xSign, dy * ySign);
				if (!willCollide(obj, dist))
					if (dx + dy > currBestDist.width + currBestDist.height || dx
							+ dy == currBestDist.width + currBestDist.height
							&& Math.abs(bestProp - (double) dx / dy) < Math
									.abs(bestProp - currProp)) {
						// and it is better than the all the last ones
						currBestDist.setSize(dx, dy);
						currProp = (double) dx / dy;
					}
			}

		return new Dimension(currBestDist.width * xSign,
				currBestDist.height * ySign);
	}

	@Override
	public Dimension getCollisionDistance(List<GameObject> gObjects,
			Dimension maxDist) {

		if (maxDist.width == 0 && maxDist.height == 0)
			return new Dimension(0, 0);

		ArrayList<GameObject> collObjts = new ArrayList<>();

		synchronized (gObjects) {
			for (GameObject comp : gObjects) {

				if (collides(comp))
					return new Dimension(0, 0);

				if (willCollide(comp, maxDist))
					collObjts.add(comp);
			}
		}

		if (collObjts.isEmpty())
			return new Dimension(maxDist);

		// sonst:

		int xSign = (int) Math.signum(maxDist.width);
		int ySign = (int) Math.signum(maxDist.height);

		int absWidth = Math.abs(maxDist.width);
		int absHeight = Math.abs(maxDist.height);

		double bestProp = (double) absWidth / absHeight;

		Dimension currBestDist = new Dimension(0, 0);
		double currProp = 1;

		for (int dx = absWidth; dx >= 0; dx--)
			nextPos: for (int dy = absHeight; dy >= 0; dy--) {

				// für jede mögliche position:

				for (GameObject comp : collObjts) {
					// wenn collision mit nur einem anderen object -> nächtse pos
					Dimension dist = new Dimension(dx * xSign, dy * ySign);
					if (willCollide(comp, dist))
						continue nextPos;
				}

				// sonst eine mögliche position gefunden!

				int length = dx + dy;
				double prop = (double) dx / dy;
				int currBestDistLength = currBestDist.width
						+ currBestDist.height;

				if (length > currBestDistLength || length == currBestDistLength
						&& Math.abs(bestProp - prop) < Math
								.abs(bestProp - currProp)) {
					// and it is better than the all the last ones

					currBestDist.setSize(dx, dy);
					currProp = prop;
				}

			}

		return new Dimension(currBestDist.width * xSign,
				currBestDist.height * ySign);

	}

	@Override
	public ArrayList<GameObject> getCollObjects(List<GameObject> gObjs,
			Dimension dist) {

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

	@Override
	public void destroy() {
		alive = false;
		OBJECTS.remove(this);
		COMPONENTS.remove(this);

	}

	@Override
	public boolean isAlive() {
		return alive;
	}

}
