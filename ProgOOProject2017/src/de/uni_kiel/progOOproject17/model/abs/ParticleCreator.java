package de.uni_kiel.progOOproject17.model.abs;

import de.uni_kiel.progOOproject17.model.Particle;

@FunctionalInterface
public interface ParticleCreator {

	public void create(Particle p);

}
