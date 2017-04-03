package de.uni_kiel.progOOproject17.model.abs;

/**
 * This class represents a advanced {@link GameElement} that also is can be
 * {@link Deadly} and is {@link Collidable}.
 * 
 */
public abstract class GameObject extends GameElement implements Deadly, Collidable {

	private boolean deadly;
	private int killcounter;

	private Hitbox hitbox;
	private boolean solid = true;

	/**
	 * Constructs a new {@link GameObject}.
	 * 
	 */
	public GameObject(Hitbox hitbox) {
		this(hitbox, null, 0, 0, 0, 0, -1);
	}

	/**
	 * 
	 */
	public GameObject(Hitbox hitbox, String resKey, int x, int y, int w, int h, int layer) {
		super(resKey, x, y, w, h, layer);
		this.hitbox = hitbox;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.uni_kiel.progOOproject17.model.abs.Collidable#isMovementRestricting()
	 */
	@Override
	public boolean isMovementRestricting() {
		return solid;
	}

	/**
	 * Setts this {@link GameObject} to be solid, and therefore be
	 * moventrestricting.
	 * 
	 * Default true.
	 * 
	 * @param solid
	 */
	public void setSolid(boolean solid) {
		this.solid = solid;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.uni_kiel.progOOproject17.model.abs.Collidable#getHitbox()
	 */
	@Override
	public Hitbox getHitbox() {
//		return hitbox;
		return hitbox.clone();
	}

	protected Hitbox getThisHitbox() {
		return hitbox;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.uni_kiel.progOOproject17.model.abs.Deadly#isDeadly()
	 */
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.uni_kiel.progOOproject17.model.abs.Deadly#addKill()
	 */
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
