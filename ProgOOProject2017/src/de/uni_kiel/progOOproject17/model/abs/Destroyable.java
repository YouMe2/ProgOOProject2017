package de.uni_kiel.progOOproject17.model.abs;

import de.uni_kiel.progOOproject17.model.CreationHelper;

public interface Destroyable {

	public boolean isAlive();

	public void destroy();

	public void activate(Environment environment, CreationHelper creatHelp);

}
