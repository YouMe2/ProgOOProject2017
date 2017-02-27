package de.uni_kiel.progOOproject17.model;

import de.uni_kiel.progOOproject17.model.abs.TickedDataModel;
import de.uni_kiel.progOOproject17.view.abs.Viewable;
import java.util.List;

public class Destroyer extends TickedDataModel {

	private final List<? extends Destroyable> list;

	public Destroyer(List<? extends Destroyable> list) {
		this.list = list;
	}

	@Override
	public void tick(long timestamp) {

		for (Destroyable obj : list)
			if (!obj.isAlive())
				list.remove(obj);

	}

	@Override
	public Viewable getViewable() {
		return null;
	}

}
