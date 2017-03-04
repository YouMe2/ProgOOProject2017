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

public enum Stage {

	ZERO(100, 500, 10, SINGLE, HOVERING, DOUBLE_HOVERING),
	ONE(90, 350, 15, SINGLE, DOUBLE, HOVERING, DOUBLE_HOVERING, TRIPLE_HOVERING),
	TWO(90, 300, 20, DOUBLE, HOVERING, DOUBLE_HOVERING, TWO_ON_TOP_HIGH),
	THREE(120, 200, 30, SINGLE, DOUBLE, HOVERING, TWO_ON_TOP),
	FOUR(105, 130, 20, SINGLE, CENTER, HOVERING),
	FIVE(120, 150, 30, SINGLE, CENTER, HOVERING, TWO_ON_TOP),
	FINAL(110, 130, 10, DOUBLE, CENTER, TWO_ON_TOP, TWO_ON_TOP_HIGH);

	private final int minSpace;
	private final int maxSpace;
	private final int elements;
	private final Obstacle[] possibleObstacles;
	private int lastWidth = -1;

	private Stage(int minSpace, int maxSpace, int elements, Obstacle... possibleObstacles) {
		this.minSpace = minSpace;
		this.maxSpace = maxSpace;
		this.elements = elements;
		this.possibleObstacles = possibleObstacles;
	}

	public Collection<GameElement> create(int stageStart, Environment e, CreationHelper c) {
		Random r = ThreadLocalRandom.current();
		ArrayList<GameElement> res = new ArrayList<>();
		int stagePos = stageStart;
		// create obstacles
		while (res.size() < elements) {
			int obstacle = r.nextInt(possibleObstacles.length);
			Obstacle o = possibleObstacles[obstacle];
			List<GameElement> obstacleElements = Arrays.asList(o.createNew(stagePos, e, c));
			int randomSpace = minSpace + r.nextInt(maxSpace - minSpace);
			res.addAll(obstacleElements);
			stagePos += o.getWidth() + randomSpace;
		}
		// create floor
		Floor floor = new Floor("floor", stageStart, FLOOR_POS, stagePos, FLOOR_HEIGHT);
		res.add(floor);
		lastWidth = stagePos;
		return res;
	}

	public int getLastStageEnd() {
		return lastWidth;
	}

}
