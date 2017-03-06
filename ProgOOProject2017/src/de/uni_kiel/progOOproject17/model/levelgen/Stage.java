package de.uni_kiel.progOOproject17.model.levelgen;

import static de.uni_kiel.progOOproject17.model.levelgen.LevelGenerator.FLOOR_HEIGHT;
import static de.uni_kiel.progOOproject17.model.levelgen.LevelGenerator.FLOOR_POS;
import static de.uni_kiel.progOOproject17.model.levelgen.Obstacle.CENTER;
import static de.uni_kiel.progOOproject17.model.levelgen.Obstacle.DOUBLE;
import static de.uni_kiel.progOOproject17.model.levelgen.Obstacle.DOUBLE_HOVERING;
import static de.uni_kiel.progOOproject17.model.levelgen.Obstacle.HOVERING;
import static de.uni_kiel.progOOproject17.model.levelgen.Obstacle.SINGLE;
import static de.uni_kiel.progOOproject17.model.levelgen.Obstacle.TRIPLE_HOVERING;
import static de.uni_kiel.progOOproject17.model.levelgen.Obstacle.TWO_ON_TOP;
import static de.uni_kiel.progOOproject17.model.levelgen.Obstacle.TWO_ON_TOP_HIGH;

import de.uni_kiel.progOOproject17.model.CreationHelper;
import de.uni_kiel.progOOproject17.model.Floor;
import de.uni_kiel.progOOproject17.model.abs.Environment;
import de.uni_kiel.progOOproject17.model.abs.GameElement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * This enum defines the different stages the player encounters during the game.
 * Defining a stage requires four main properties:
 * <ol>
 * <li>A minimum distance between two obstacles</li>
 * <li>A maximum distance between two obstacles</li>
 * <li>A number of game elements in a particular stage</li>
 * <li>A set of possible {@link Obstacle}s inside that stage</li>
 * </ol>
 * When a stage is {@link #create(int, Environment, CreationHelper) create}d, it
 * creates a {@link Collection} of <code>GameElement</code>s. This collection
 * contains a floor and as many obstacles as needed to cover at least as many
 * <code>GameElement</code>s as specified on construction. The distance between
 * the obstacles is a random value between the minimal and the maximal distance
 * between obstacles.
 * <p>
 * As a side-effect, the ending position of the last created stage is stored
 * (until re-creating the stage) and can be acquired by invoking
 * {@link #getLastStageEnd()}. If the stage hasn't been created before, the
 * value if <code>-1</code> by default.
 *
 */
public enum Stage {

	ZERO(100, 500, 10, SINGLE, HOVERING, DOUBLE_HOVERING),
	ONE(90, 350, 15, SINGLE, DOUBLE, HOVERING, DOUBLE_HOVERING, TRIPLE_HOVERING),
	TWO(90, 300, 20, DOUBLE, HOVERING, DOUBLE_HOVERING, TWO_ON_TOP_HIGH),
	THREE(120, 200, 30, SINGLE, DOUBLE, HOVERING, TWO_ON_TOP),
	FOUR(105, 130, 20, SINGLE, CENTER, HOVERING),
	FIVE(120, 150, 30, SINGLE, CENTER, HOVERING, TWO_ON_TOP),
	FINAL(110, 130, 10, DOUBLE, CENTER, TWO_ON_TOP, TWO_ON_TOP_HIGH);

	/**
	 * The minimal distance between two obstacles.
	 */
	private final int minSpace;
	/**
	 * The maximal distance between two obstacles.
	 */
	private final int maxSpace;
	/**
	 * The minimum number of <code>GameElement</code>s required to create the
	 * stage. The actualy number of created elements may be higher if the last
	 * created obstacles exceeds the value.
	 */
	private final int elements;
	/**
	 * A list of possible obstacles to choose from when creating the stage.
	 */
	private final Obstacle[] possibleObstacles;
	/**
	 * The last ending position of the stage.
	 */
	private int lastEnd = -1;

	/**
	 * Creates a new stage with the given minimal space, maximal space, minimal
	 * number of elements and list of possible obstacles. The ending position
	 * will be stored and can be acquired afterwards by calling
	 * {@link #getLastStageEnd()}.
	 *
	 * @param minSpace
	 *            the minimal distance between two obstacles
	 * @param maxSpace
	 *            the maximal distance between two obstacles
	 * @param elements
	 *            the minimal number of elements this stage will contain after
	 *            creating it
	 * @param possibleObstacles
	 *            the list of possible obstacles to choose from
	 */
	private Stage(int minSpace, int maxSpace, int elements, Obstacle... possibleObstacles) {
		this.minSpace = minSpace;
		this.maxSpace = maxSpace;
		this.elements = elements;
		this.possibleObstacles = possibleObstacles;
	}

	/**
	 * Creates the stage at a specified starting position.
	 *
	 * @param stageStart
	 *            the position the stage is supposed to start at
	 * @return a collection of game elements representing the stage
	 */
	public Collection<GameElement> create(int stageStart) {
		Random r = ThreadLocalRandom.current();
		ArrayList<GameElement> res = new ArrayList<>();
		int stagePos = stageStart;
		// create obstacles
		while (res.size() < elements) {
			int obstacle = r.nextInt(possibleObstacles.length);
			Obstacle o = possibleObstacles[obstacle];
			List<GameElement> obstacleElements = Arrays.asList(o.createNew(stagePos));
			int randomSpace = minSpace + r.nextInt(maxSpace - minSpace);
			res.addAll(obstacleElements);
			stagePos += o.getWidth() + randomSpace;
		}
		// create floor
		Floor floor = new Floor("floor", stageStart, FLOOR_POS, stagePos, FLOOR_HEIGHT);
		res.add(floor);
		lastEnd = stagePos;
		return res;
	}

	/**
	 * Gets the ending position of the last time the stage was created.
	 *
	 * @return the ending position of the last time the stage was created
	 */
	public int getLastStageEnd() {
		return lastEnd;
	}

}
