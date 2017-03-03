package de.uni_kiel.progOOproject17.model;

import de.uni_kiel.progOOproject17.model.abs.DestroyListener;
import de.uni_kiel.progOOproject17.model.abs.GameObjectCreator;
import de.uni_kiel.progOOproject17.model.abs.ParticleCreator;

public interface CreationHelper extends ParticleCreator, GameObjectCreator, DestroyListener {

}
