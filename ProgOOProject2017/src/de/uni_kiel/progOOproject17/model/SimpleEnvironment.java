/**
 * 
 */
package de.uni_kiel.progOOproject17.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Consumer;

import de.uni_kiel.progOOproject17.model.abs.Collidable;
import de.uni_kiel.progOOproject17.model.abs.Distance;
import de.uni_kiel.progOOproject17.model.abs.Environment;
import de.uni_kiel.progOOproject17.model.abs.GameElement;
import de.uni_kiel.progOOproject17.model.abs.GameObject;
import de.uni_kiel.progOOproject17.model.abs.Hitbox;

/**
 * @author Yannik Eikmeier
 * @since 30.03.2017
 *
 */
public class SimpleEnvironment implements Environment {

	private Collection<GameElement> gameElements;

	/**
	 * 
	 */
	public SimpleEnvironment(Collection<GameElement> gameElements) {
		this.gameElements = gameElements;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.uni_kiel.progOOproject17.model.abs.Environment#willCollide(de.
	 * uni_kiel.progOOproject17.model.abs.Collidable,
	 * de.uni_kiel.progOOproject17.model.abs.Distance)
	 */
	@Override
	public boolean willCollide(Collidable coll, Distance dist) {
		Hitbox tHitbox = coll.getHitbox();
		tHitbox.translate(dist);
		synchronized (gameElements) {
			for (GameElement e : gameElements)
				if (e instanceof Collidable)
					if (willCollide(coll, dist, (Collidable) e))
						return true;
		}
		return false;
	}

	/**
	 * Returns true of false whether the two {@link Collidable}s will collide
	 * after o1 moves for d1.
	 * 
	 * @param o1
	 *            the 1st {@link Collidable}
	 * @param d1
	 *            the {@link Distance} o1 will move
	 * @param o2
	 *            the 2nd {@link Collidable}
	 * @return whether o1 will collide with o2 after moving d1
	 */
	private boolean willCollide(Collidable c1, Distance d1, Collidable c2) {
		if (c1 == c2 || (c1 != null && c1.equals(c2)))
			return false;

		Hitbox h1 = c1.getHitbox();
		h1.translate(d1);

		Hitbox h2 = c2.getHitbox();

		if (h2.intersectsFAST(h1) && h2.intersects(h1)) {
			assert h1.intersects(h2) : "hitbox intersection failed: true=("+h2+").intersects("+h1+") but false other wise!!";
			return true;
		}
		
		return false;
	}

	private boolean collide(Collidable c1, Collidable c2) {
		return willCollide(c1, new Distance(), c2);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.uni_kiel.progOOproject17.model.abs.Environment#
	 * getCollisionDistance(de.uni_kiel.progOOproject17.model.abs. Collidable,
	 * de.uni_kiel.progOOproject17.model.abs.Distance)
	 */
	@Override
	public Distance getCollisionDistance(Collidable coll, Distance maxDist) {

		if (maxDist.x == 0 && maxDist.y == 0)
			return maxDist;

		ArrayList<Collidable> collObjts = new ArrayList<>();

		synchronized (gameElements) {
			for (GameElement e : gameElements)
				if (e instanceof Collidable) {
					Collidable c = (Collidable) e;
					if (c.isMovementRestricting() && willCollide(coll, maxDist, c))
						collObjts.add(c);
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
				dist.scale(signD);

				for (Collidable c : collObjts)
					// wenn collision mit nur einem anderen object ->
					// nächtse
					if (willCollide(coll, dist, c))
						continue nextPos;

				// sonst: eine mögliche Distance gefunden!

				if (dist.getSqLength() > currBestDist.getSqLength())
					// und sie ist besser!
					currBestDist = dist;

			}

		return currBestDist;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.uni_kiel.progOOproject17.model.abs.Environment#getCollObjects(de.
	 * uni_kiel.progOOproject17.model.abs.Collidable,
	 * de.uni_kiel.progOOproject17.model.abs.Distance)
	 */
	@Override
	public ArrayList<Collidable> getCollObjects(Collidable coll, Distance dist) {

		ArrayList<Collidable> collObjts = new ArrayList<>();

		synchronized (gameElements) {
			for (GameElement e : gameElements)
				if (e instanceof Collidable) {
					Collidable c = (Collidable) e;
					if (willCollide(coll, dist, c))
						collObjts.add(c);
				}
		}
		return collObjts;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.uni_kiel.progOOproject17.model.abs.Environment#contacts(de.
	 * uni_kiel.progOOproject17.model.abs.Collidable,
	 * de.uni_kiel.progOOproject17.model.abs.Collidable)
	 */
	@Override
	public boolean contacts(Collidable c1, Collidable c2) {
		if (c1 == c2)
			return false;
		return c1.getHitbox().contacts(c2.getHitbox());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.uni_kiel.progOOproject17.model.abs.Environment#isOnGround(de.
	 * uni_kiel.progOOproject17.model.abs.Collidable)
	 */
	@Override
	public boolean isOnGround(Collidable coll) {
		
		synchronized (gameElements) {
			for (GameElement e : gameElements)
				if (e instanceof Collidable) {
					Collidable c = (Collidable) e;

					if (c.isMovementRestricting() && willCollide(coll, new Distance(0, 1), c))
						return true;

				}
		}

		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.uni_kiel.progOOproject17.model.abs.Environment#forEachCollision(de
	 * .uni_kiel.progOOproject17.model.abs.Collidable,
	 * de.uni_kiel.progOOproject17.model.abs.Distance,
	 * java.util.function.Consumer)
	 */
	@Override
	public void forEachCollision(Collidable coll, Distance dist, Consumer<GameObject> consumer) {
		synchronized (gameElements) {
			for (GameElement e : gameElements)

				if (e instanceof GameObject) {
					GameObject o = (GameObject) e;
					if (willCollide(coll, dist, o))
						consumer.accept(o);

				}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.uni_kiel.progOOproject17.model.abs.Environment#forEachContact(de.
	 * uni_kiel.progOOproject17.model.abs.Collidable,
	 * java.util.function.Consumer)
	 */
	@Override
	public void forEachContact(Collidable coll, Consumer<GameObject> consumer) {

		synchronized (gameElements) {

			for (GameElement e : gameElements)

				if (e instanceof GameObject) {
					GameObject o = (GameObject) e;
					if (contacts(coll, o))
						consumer.accept(o);
				}
		}
	}

}
