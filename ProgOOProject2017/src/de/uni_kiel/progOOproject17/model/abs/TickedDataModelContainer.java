package de.uni_kiel.progOOproject17.model.abs;

import java.util.ArrayList;

public abstract class TickedDataModelContainer<T extends TickedDataModel> extends TickedDataModel {

	protected final ArrayList<T> subData = new ArrayList<>();

	@Override
	public void tick(long timestamp) {
		synchronized (subData) {

			for (Ticked sD : subData)
				sD.tick(timestamp);
		}
	}

}
