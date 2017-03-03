/**
 * 
 */
package de.uni_kiel.progOOproject17.model;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.function.Consumer;

import de.uni_kiel.progOOproject17.model.abs.Collidable;
import de.uni_kiel.progOOproject17.model.abs.Destroyable;
import de.uni_kiel.progOOproject17.model.abs.Distance;
import de.uni_kiel.progOOproject17.model.abs.Environment;
import de.uni_kiel.progOOproject17.model.abs.GameCompound;
import de.uni_kiel.progOOproject17.model.abs.GameElement;
import de.uni_kiel.progOOproject17.model.abs.GameObject;
import de.uni_kiel.progOOproject17.view.abs.Viewable;

/**
 * @author Yannik Eikmeier
 * @since 03.03.2017
 *
 */
public class GameScreen extends GameCompound implements Environment, CreationHelper {

	private final LinkedList<GameElement> gameElements;
	private final LinkedList<GameElement> createdElements;
	private final LinkedList<Destroyable> destroyedElements;

	private final Player player;
	private final Distance screenVelocity;

	private LevelGeneratorDEMO levelGenerator;


	/**
	 * @param x
	 * @param y
	 * @param w
	 * @param h
	 */
	public GameScreen(int w, int h) {
		super(0, 0, w, h);

		gameElements = new LinkedList<>();
		destroyedElements = new LinkedList<>();
		createdElements = new LinkedList<>();

		levelGenerator = new LevelGeneratorDEMO(this, this);
		screenVelocity = new Distance( 8, 0);
		

		player = new Player("cat", PLGameModel.lhToGame(3, PLGameModel.LH_HEIGHT - 3), this, this);
		player.setPermaXVel(screenVelocity.x);
		create(player);

		levelGenerator.setRunning(true);

		Floor floor = new Floor("floor", PLGameModel.lhToGam(0, PLGameModel.LH_HEIGHT - 1, PLGameModel.LH_WIDTH+50, 1), this, this);
		Floor barier = new Floor(null, PLGameModel.lhToGam(-20, 0, 1, PLGameModel.LH_HEIGHT), this, this);
		barier.setDeadly(true);
		Background bg = new Background("background", 0, 0, w, h, this, this);
		create(bg);
		create(floor);
		create(barier);

		// PARTICLE TEST:
//		 Particle particle = new Particle("partTest", 800, 0, 300, 300, 1000, 4, this, this);
//		 create(particle);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.uni_kiel.progOOproject17.model.abs.Ticked#tick(long)
	 */
	@Override
	public void tick(long timestamp) {
		
		this.setLocation(player.getX()-PLGameModel.LHPIXEL_WIDTH*2 ,0);
		
		levelGenerator.tick(timestamp);
		gameElements.forEach(e -> e.tick(timestamp));
		
		
		gameElements.removeAll(destroyedElements);
		destroyedElements.clear();
		
		gameElements.addAll(createdElements);
		createdElements.clear();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.uni_kiel.progOOproject17.model.abs.GameCompound#getViewables()
	 */
	@Override
	public Viewable[] getViewables() {
		
		return gameElements.toArray(new Viewable[gameElements.size()]);
	}

	public Player getPlayer() {
		return player;

	}

	@Override
	public boolean willCollide(Collidable obj, Distance dist) {
		Rectangle rect = obj.getBoundingRect();
		rect.translate(dist.x, dist.y);
		synchronized (gameElements) {
			for (GameElement o : gameElements) {

				if (o instanceof Collidable) {
					Collidable c = (Collidable) o;
					if (rect.intersects(o.getBoundingRect()) && !o.equals(obj))
						return true;
				}

			}
		}

		return false;
	}

	private boolean willCollide(Collidable o1, Distance d1, Collidable o2) {
		if (o1 == o2)
			return false;

		Rectangle rect = o1.getBoundingRect();
		rect.translate(d1.x, d1.y);

		return rect.intersects(o2.getBoundingRect());
	}

	@Override
	public Distance getCollisionDistance(Collidable obj, Distance maxDist) {

		if (maxDist.x == 0 && maxDist.y == 0)
			return maxDist;

		ArrayList<Collidable> collObjts = getCollObjects(obj, maxDist);

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

				for (Collidable o : collObjts) {
					// wenn collision mit nur einem anderen object -> nächtse
					if (willCollide(obj, dist, o))
						continue nextPos;
				}

				// sonst: eine mögliche Distance gefunden!

				if (dist.getSqLenghth() > currBestDist.getSqLenghth())
					// und sie ist besser!
					currBestDist = dist;

			}

		return currBestDist;

	}

	@Override
	public ArrayList<Collidable> getCollObjects(Collidable obj, Distance dist) {

		ArrayList<Collidable> collObjts = new ArrayList<>();

		synchronized (gameElements) {
			for (GameElement o : gameElements) {

				if (o instanceof Collidable) {
					Collidable c = (Collidable) o;
					if (willCollide(obj, dist, c))
						collObjts.add(c);
				}

			}
		}
		return collObjts;
	}

	@Override
	public boolean contacts(Collidable o1, Collidable o2) {

		if (o1 == o2)
			return false;
		Rectangle rect = o1.getBoundingRect();
		rect.grow(1, 1);

		return rect.intersects(o2.getBoundingRect());
	}

	@Override
	public boolean isOnGround(Collidable obj) {
		Rectangle rect = obj.getBoundingRect();
		rect.translate(0, 1);
		synchronized (gameElements) {
			for (GameElement e : gameElements) {

				if (e instanceof Collidable) {
					Collidable c = (Collidable) e;
					if (rect.intersects(c.getBoundingRect()) && !c.equals(obj))
						return true;

				}

			}
		}

		return false;
	}

	@Override
	public void forEachCollision(Collidable obj, Distance dist, Consumer<GameObject> consumer) {
		synchronized (gameElements) {
			for (GameElement o : gameElements)

				if (o instanceof GameObject) {
					GameObject c = (GameObject) o;
					if (willCollide(obj, dist, c))
						consumer.accept(c);

				}
		}
	}

	@Override
	public void forEachContact(Collidable obj, Consumer<GameObject> consumer) {

		synchronized (gameElements) {
			
			for (GameElement o : gameElements)
				
				if (o instanceof Collidable) {
					
					Collidable c = (Collidable) o;
					if (contacts(obj, c))
						consumer.accept((GameObject)c);
				}
		}
	}

	@Override
	public void create(GameElement g) {
		
		System.out.println("Created: "+ g.getResourceKey());
		
		createdElements.add(g);
		g.activate();
	}

	@Override
	public void onDestruction(Destroyable d) {
		System.out.println("Destroyed: "+ ((GameElement)d).getResourceKey());
		
		destroyedElements.add(d);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.uni_kiel.progOOproject17.model.abs.Environment#getScreenShiftDistance(
	 * )
	 */
	@Override
	public Rectangle getScreenRect() {
		return getBoundingRect();
	}

}
