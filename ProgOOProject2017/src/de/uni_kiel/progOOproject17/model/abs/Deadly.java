package de.uni_kiel.progOOproject17.model.abs;

/**
 * This interface implies that instances of this it can be deadly and therefore
 * can gain a kill.
 */
public interface Deadly {

	/**
	 * Return true or false, whether this object is deadly or not.
	 * 
	 * @return whether this is deadly
	 */
	public boolean isDeadly();

	/**
	 * Sets this object deadly or nondeadly.
	 * 
	 * @param deadly whether this is deadly
	 */
	public void setDeadly(boolean deadly);

	/**
	 * Adds a kill to the killcounter of this object.
	 */
	public void addKill();

	/**
	 * Returns how many kills this object got.
	 * 
	 * @return the number of kills
	 */
	public int getKills();

}
