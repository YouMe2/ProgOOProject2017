/**
 * 
 */
package de.uni_kiel.progOOproject17.model.abs;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.function.Consumer;

import javax.swing.AbstractAction;
import javax.swing.Action;

import de.uni_kiel.progOOproject17.model.Background;
import de.uni_kiel.progOOproject17.model.Block;
import de.uni_kiel.progOOproject17.model.CreationHelper;
import de.uni_kiel.progOOproject17.model.Floor;
import de.uni_kiel.progOOproject17.model.InputActionKey;
import de.uni_kiel.progOOproject17.view.abs.Viewable;

/**
 * @author Yannik Eikmeier
 * @since 28.03.2017
 *
 */
public class DebugScreen extends Screen {

	private final LinkedList<GameElement> gameElements;
	private final LinkedList<GameElement> createdElements;
	private final LinkedList<Destroyable> destroyedElements;

	private Environment environment = new Environment() {

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * de.uni_kiel.progOOproject17.model.abs.Environment#willCollide(de.
		 * uni_kiel.progOOproject17.model.abs.Collidable,
		 * de.uni_kiel.progOOproject17.model.abs.Distance)
		 */
		@Override
		public boolean willCollide(Collidable coll, Distance dist) {
			Hitbox tHitbox = coll.getHitbox().getCloneTranslate(dist);
			synchronized (gameElements) {
				for (GameElement e : gameElements)
					if (e instanceof Collidable)
						if (!e.equals(coll) && tHitbox.intersectsFAST(((Collidable) e).getHitbox()))
							if (tHitbox.intersects(((Collidable) e).getHitbox()))
								return true;
			}
			return false;
		}

		/**
		 * Returns true of false whether the two {@link Collidable}s will
		 * collide after o1 moves for d1.
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
			if (c1 == c2)
				return false;

			Hitbox tHitbox = c1.getHitbox().getCloneTranslate(d1);
			
			if (c2.getHitbox().intersectsFAST(tHitbox))
				return c2.getHitbox().intersects(tHitbox);
			return false;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see de.uni_kiel.progOOproject17.model.abs.Environment#
		 * getCollisionDistance(de.uni_kiel.progOOproject17.model.abs.
		 * Collidable, de.uni_kiel.progOOproject17.model.abs.Distance)
		 */
		@Override
		public Distance getCollisionDistance(Collidable coll, Distance maxDist) {

			if (maxDist.x == 0 && maxDist.y == 0)
				return maxDist;

			ArrayList<Collidable> collObjts = getCollObjects(coll, maxDist);

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
		 * @see
		 * de.uni_kiel.progOOproject17.model.abs.Environment#getCollObjects(de.
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
			Hitbox tHitbox = coll.getHitbox().getCloneTranslate(new Distance(0, 1));
			synchronized (gameElements) {
				for (GameElement e : gameElements)
					if (e instanceof Collidable) {
						Collidable c = (Collidable) e;
						if (!c.equals(coll) && c.getHitbox().intersectsFAST(tHitbox))
							if (c.getHitbox().intersects(tHitbox))
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
		 * @see
		 * de.uni_kiel.progOOproject17.model.abs.Environment#forEachContact(de.
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

	};

	private CreationHelper creationHelper = new CreationHelper() {
		/*
		 * (non-Javadoc)
		 * 
		 * @see de.uni_kiel.progOOproject17.model.abs.ElementCreator#create(de.
		 * uni_kiel.progOOproject17.model.abs.GameElement)
		 */
		@Override
		public void create(GameElement g) {

			System.out.println("Created: " + g.getResourceKey());

			createdElements.add(g);
			g.activate(environment, this);
		}

		@Override
		public void onDestruction(Destroyable d) {
			System.out.println("Destroyed: " + ((GameElement) d).getResourceKey());

			destroyedElements.add(d);
		}

	};

	private Block testBlock;
	private Block block;

	/**
	 * @param w
	 * @param h
	 */
	public DebugScreen(int w, int h, Action resumeAction) {
		super(w, h);
		gameElements = new LinkedList<>();
		destroyedElements = new LinkedList<>();
		createdElements = new LinkedList<>();

		testBlock = new Block(new Hitbox.PolygonHitbox(
				new Point[] { new Point(60, 60), new Point(90, 90), new Point(60, 90), new Point(50, 70) }));

		// testBlock = new Block(new Hitbox.CircleHitbox(60, 60, 6));

		// testBlock = new Block(new Hitbox.LineHitbox(60, 60, 60, 6));

		testBlock.setView("floor", 60, 60, 4, 4, Viewable.ENTITY_LAYER);
		testBlock.activate(environment, creationHelper);

		creationHelper.create(new Block(new Hitbox.LineHitbox(300, 50, 280, 150)));

		creationHelper.create(new Block(new Hitbox.CircleHitbox(170, 170, 32)));

		creationHelper.create(new Block(new Hitbox.PointHitbox(50, 200)));

		// creationHelper.create(new Background("floor", 50, 50, 1, 1));

		block = new Block(
				new Hitbox.PolygonHitbox(new Point[] { new Point(100, 100), new Point(200, 90), new Point(100, 130) }));
		block.setView("floor", 100, 100, 100, 30, Viewable.ENTITY_LAYER);

		creationHelper.create(block);
		creationHelper.create(testBlock);

		putAction(InputActionKey.UP_P, new AbstractAction() {

			@Override
			public void actionPerformed(ActionEvent e) {
				testBlock.setCurrMoveCommand(MoveCommand.UP);

			}
		});
		putAction(InputActionKey.DOWN_P, new AbstractAction() {

			@Override
			public void actionPerformed(ActionEvent e) {
				testBlock.setCurrMoveCommand(MoveCommand.DOWN);

			}
		});
		putAction(InputActionKey.LEFT_P, new AbstractAction() {

			@Override
			public void actionPerformed(ActionEvent e) {
				testBlock.setCurrMoveCommand(MoveCommand.LEFT);

			}
		});
		putAction(InputActionKey.RIGHT_P, new AbstractAction() {

			@Override
			public void actionPerformed(ActionEvent e) {
				testBlock.setCurrMoveCommand(MoveCommand.RIGHT);

			}
		});

		putAction(InputActionKey.UP_R, new AbstractAction() {

			@Override
			public void actionPerformed(ActionEvent e) {
				testBlock.setCurrMoveCommand(MoveCommand.NONE);

			}
		});
		putAction(InputActionKey.DOWN_R, new AbstractAction() {

			@Override
			public void actionPerformed(ActionEvent e) {
				testBlock.setCurrMoveCommand(MoveCommand.NONE);

			}
		});
		putAction(InputActionKey.LEFT_R, new AbstractAction() {

			@Override
			public void actionPerformed(ActionEvent e) {
				testBlock.setCurrMoveCommand(MoveCommand.NONE);

			}
		});
		putAction(InputActionKey.RIGHT_R, new AbstractAction() {

			@Override
			public void actionPerformed(ActionEvent e) {
				testBlock.setCurrMoveCommand(MoveCommand.NONE);

			}
		});

		putAction(InputActionKey.SELECT_P, resumeAction);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.uni_kiel.progOOproject17.model.abs.Ticked#tick(long)
	 */
	@Override
	public void tick(long timestamp) {

		for (GameElement gameElement : gameElements) {
			gameElement.tick(timestamp);
		}
		//
		// System.out.println(testBlock.getHitbox());
		// System.out.println(block.getHitbox());

		// System.out.println(((Hitbox.PolygonHitbox)testBlock.getHitbox()).isInside(new
		// Point(50, 50)));

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

		ArrayList<Viewable> views = new ArrayList<>();

		for (GameElement e : gameElements) {
			if (e instanceof Collidable)
				views.addAll(Arrays.asList(((Collidable) e).getHitbox().getDebugViewables()));
		}
		views.addAll(gameElements);
		return views.toArray(new Viewable[views.size()]);

	}

}
