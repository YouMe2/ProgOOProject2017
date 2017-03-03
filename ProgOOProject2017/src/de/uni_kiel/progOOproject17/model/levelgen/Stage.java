package de.uni_kiel.progOOproject17.model.levelgen;

import static de.uni_kiel.progOOproject17.model.levelgen.Obstacle.DOUBLE;
import static de.uni_kiel.progOOproject17.model.levelgen.Obstacle.DOUBLE_HOVERING;
import static de.uni_kiel.progOOproject17.model.levelgen.Obstacle.HOVERING;
import static de.uni_kiel.progOOproject17.model.levelgen.Obstacle.SINGLE;
import static de.uni_kiel.progOOproject17.model.levelgen.Obstacle.TRIPLE;

import de.uni_kiel.progOOproject17.model.abs.GameElement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public enum Stage {

	ZERO(15, SINGLE, DOUBLE, HOVERING),
	ONE(25, SINGLE, DOUBLE, TRIPLE, HOVERING, DOUBLE_HOVERING),
	TWO(0),
	THREE(0),
	FOUR(0),
	FIVE(0),
	FINAL(0);

	private final int length;
	private final Obstacle[] possibleObstacles;

	private Stage(int length, Obstacle... possibleObstacles) {
		this.length = length;
		this.possibleObstacles = possibleObstacles;
	}

	public Collection<GameElement> create(int stageStart) {
		Random r = ThreadLocalRandom.current();
		ArrayList<GameElement> res = new ArrayList<>();
		while (res.size() < length) {
			int obstacle = r.nextInt(possibleObstacles.length);
			Obstacle o = possibleObstacles[obstacle];
			res.addAll(Arrays.asList(o.createNew(stageStart)));
			stageStart += o.getWidth();
		}
		return res;
	}

}
