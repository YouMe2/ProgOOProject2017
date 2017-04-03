/**
 * 
 */
package de.uni_kiel.progOOproject17.model;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.SortedSet;
import java.util.function.Consumer;

import javax.swing.AbstractAction;
import javax.swing.Action;

import de.uni_kiel.progOOproject17.model.abs.Collidable;
import de.uni_kiel.progOOproject17.model.abs.Destroyable;
import de.uni_kiel.progOOproject17.model.abs.Distance;
import de.uni_kiel.progOOproject17.model.abs.Environment;
import de.uni_kiel.progOOproject17.model.abs.GameElement;
import de.uni_kiel.progOOproject17.model.abs.GameObject;
import de.uni_kiel.progOOproject17.model.abs.Hitbox;
import de.uni_kiel.progOOproject17.model.abs.MoveCommand;
import de.uni_kiel.progOOproject17.model.abs.Screen;
import de.uni_kiel.progOOproject17.model.abs.Hitbox.CircleHitbox;
import de.uni_kiel.progOOproject17.model.abs.Hitbox.LineHitbox;
import de.uni_kiel.progOOproject17.model.abs.Hitbox.PointHitbox;
import de.uni_kiel.progOOproject17.model.abs.Hitbox.PolygonHitbox;
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
	private final Environment environment;
	private final CreationHelper creationHelper = new CreationHelper() {
		/*
		 * (non-Javadoc)
		 * 
		 * @see de.uni_kiel.progOOproject17.model.abs.ElementCreator#create(de.
		 * uni_kiel.progOOproject17.model.abs.GameElement)
		 */
		@Override
		public void create(GameElement g) {

			System.out.println("Created: " + g.getContentKey());

			createdElements.add(g);
			g.activate(environment, this);
		}

		@Override
		public void onDestruction(Destroyable d) {
			System.out.println("Destroyed: " + ((GameElement) d).getContentKey());

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
		environment = new SimpleEnvironment(gameElements);

		testBlock = new Block(new Hitbox.PolygonHitbox( new Point(60, 60), 
				new Point[] { new Point(0, 0), new Point(30, 30), new Point(0, 30), new Point(-10, 10) }));

		 testBlock = new Block(new Hitbox.CircleHitbox(60, 60, 6));

		 testBlock = new Block(new Hitbox.LineHitbox(60, 60, 80, 70));

		testBlock.setView("floor", 60, 60, 4, 4, Viewable.ENTITY_LAYER);
		testBlock.activate(environment, creationHelper);

		Block line = new Block(new Hitbox.LineHitbox(20, 100, 20, 200));
		creationHelper.create(line);
		System.out.println(line.getHitbox());

		creationHelper.create(new Block(new Hitbox.CircleHitbox(170, 170, 32)));

		creationHelper.create(new Block(new Hitbox.PointHitbox(50, 200)));

//		creationHelper.create(new Background("floor", 50, 20, 1, 1));

		block = new Block( new Hitbox.PolygonHitbox(new Point(100, 100), new Point[] { new Point(0, 0), new Point(100, -10), new Point(0, 30) }));
		block.setView("floor", 100, 100, 100, 30, Viewable.ENTITY_LAYER);

//		System.out.println(block.getHitbox().intersects(testBlock.getHitbox()));
		
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

//		System.out.println(((Hitbox.PolygonHitbox) testBlock.getHitbox()).isInside(new Point(50, 20)));

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
