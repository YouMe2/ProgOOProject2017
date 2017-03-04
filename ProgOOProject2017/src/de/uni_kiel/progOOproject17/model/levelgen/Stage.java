package de.uni_kiel.progOOproject17.model.levelgen;

import static de.uni_kiel.progOOproject17.model.levelgen.Obstacle.DOUBLE;
import static de.uni_kiel.progOOproject17.model.levelgen.Obstacle.DOUBLE_HOVERING;
import static de.uni_kiel.progOOproject17.model.levelgen.Obstacle.HOVERING;
import static de.uni_kiel.progOOproject17.model.levelgen.Obstacle.SINGLE;
import static de.uni_kiel.progOOproject17.model.levelgen.Obstacle.TRIPLE_HOVERING;

import de.uni_kiel.progOOproject17.model.Background;
import de.uni_kiel.progOOproject17.model.CreationHelper;
import de.uni_kiel.progOOproject17.model.Floor;
import de.uni_kiel.progOOproject17.model.PLGameModel;
import de.uni_kiel.progOOproject17.model.abs.Environment;
import de.uni_kiel.progOOproject17.model.abs.GameElement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public enum Stage {

	ZERO(100, 500, 10, SINGLE, DOUBLE, HOVERING),
	ONE(90, 350, 15, SINGLE, DOUBLE, HOVERING, DOUBLE_HOVERING),
	TWO(80, 300, 20, DOUBLE, DOUBLE_HOVERING, TRIPLE_HOVERING),
	THREE(0, 0, 0),
	FOUR(0, 0, 0),
	FIVE(0, 0, 0),
	FINAL(0, 0, 0);

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
		Floor floor = new Floor("floor", stageStart, PLGameModel.GAME_HEIGHT - LevelGenerator.FLOOR_HEIGHT, stagePos,
				LevelGenerator.FLOOR_HEIGHT);
		res.add(floor);
		// create backgrounds
		int covered = stageStart;
		while (covered < stagePos) {
			Background b = new Background("bg", covered, 0, 1024, PLGameModel.GAME_HEIGHT);
			covered += b.getWidth();
			res.add(b);
		}
		lastWidth = covered;
		return res;
	}

	public int getLastStageEnd() {
		return lastWidth;
	}

}
