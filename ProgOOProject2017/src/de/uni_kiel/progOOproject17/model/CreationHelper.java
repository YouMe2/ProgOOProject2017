package de.uni_kiel.progOOproject17.model;

import de.uni_kiel.progOOproject17.model.abs.DestroyListener;
import de.uni_kiel.progOOproject17.model.abs.ElementCreator;

/**
 * Serves as a wraper for the interfaces {@link ElementCreator} and {@link DestroyListener}.
 *
 */
public interface CreationHelper extends ElementCreator, DestroyListener {

}
