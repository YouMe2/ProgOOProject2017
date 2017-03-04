package de.uni_kiel.progOOproject17.model.levelgen;

import static de.uni_kiel.progOOproject17.model.levelgen.Obstacle.DOUBLE;
import static de.uni_kiel.progOOproject17.model.levelgen.Obstacle.DOUBLE_HOVERING;
import static de.uni_kiel.progOOproject17.model.levelgen.Obstacle.HOVERING;
import static de.uni_kiel.progOOproject17.model.levelgen.Obstacle.SINGLE;
import static de.uni_kiel.progOOproject17.model.levelgen.Obstacle.TRIPLE;

import de.uni_kiel.progOOproject17.model.CreationHelper;
import de.uni_kiel.progOOproject17.model.abs.Environment;
import de.uni_kiel.progOOproject17.model.abs.GameElement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public enum Stage {

	ZERO(80, 500, 15, SINGLE),
	ONE(60, 350, 25, SINGLE, DOUBLE, TRIPLE, HOVERING, DOUBLE_HOVERING),
	TWO(0, 0, 0),
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

	public Collection<GameElement> create(int stagePos, Environment e, CreationHelper c) {
		System.out.println("stage for " + stagePos + " from\n\t" + Arrays.toString(possibleObstacles));
		Random r = ThreadLocalRandom.current();
		ArrayList<GameElement> res = new ArrayList<>();
		while (res.size() < elements) {
			int obstacle = r.nextInt(possibleObstacles.length);
			Obstacle o = possibleObstacles[obstacle];
			List<GameElement> obstacleElements = Arrays.asList(o.createNew(stagePos, e, c));
			int randomSpace = minSpace + r.nextInt(maxSpace - minSpace);
			res.addAll(obstacleElements);
			stagePos += o.getWidth() + randomSpace;
			System.out.println("w = " + o.getWidth() + ", rnd = " + randomSpace + " -> " + stagePos);
		}
		lastWidth = stagePos;
		System.out.println("STAGE '" + this + "' CREATED, WIDTH = " + lastWidth);
		return res;
	}

	public int getLastWidth() {
		return lastWidth;
	}

}
