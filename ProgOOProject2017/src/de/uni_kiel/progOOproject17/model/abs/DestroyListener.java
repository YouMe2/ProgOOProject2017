package de.uni_kiel.progOOproject17.model.abs;

@FunctionalInterface
public interface DestroyListener {

	public void onDestruction(Destroyable d);

}
