package de.uni_kiel.progOOproject17.model.abs;

public abstract class GameObject extends GameElement implements Deadly, Collidable {

	private boolean deadly;
	private int killcounter;

	public GameObject(String resKey, int x, int y, int w, int h) {
		super(resKey, x, y, w, h);
	}

	@Override
	public boolean isDeadly() {
		return deadly;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see de.uni_kiel.progOOproject17.model.abs.Deadly#setDeadly(boolean)
	 */
	@Override
	public void setDeadly(boolean deadly) {
		this.deadly = deadly;

	}

	@Override
	public void addKill() {
		killcounter++;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see de.uni_kiel.progOOproject17.model.abs.Deadly#getKills()
	 */
	@Override
	public int getKills() {
		return killcounter;
	}
}
