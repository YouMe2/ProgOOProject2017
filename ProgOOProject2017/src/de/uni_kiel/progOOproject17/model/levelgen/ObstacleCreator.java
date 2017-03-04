package de.uni_kiel.progOOproject17.model.levelgen;

import de.uni_kiel.progOOproject17.model.abs.GameElement;

@FunctionalInterface
public interface ObstacleCreator {

	public GameElement[] createNew(int obstacleStart);

}
