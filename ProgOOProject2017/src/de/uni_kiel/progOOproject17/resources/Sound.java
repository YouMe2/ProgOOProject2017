package de.uni_kiel.progOOproject17.resources;

import javax.sound.sampled.Clip;

/**
 * A class representing a sound in the game.
 *
 */
public class Sound {

	/**
	 * A {@link Clip} to be played.
	 */
	private Clip sound;

	/**
	 * Creates a new sound based on the given <code>Clip</code>.
	 *
	 * @param sound
	 *            the <code>Clip</code> to be played
	 */
	Sound(Clip sound) {
		this.sound = sound;
	}

	/**
	 * Plays the sound asynchronously in the background. If is is still playing
	 * from a previous call of this method, the playing will be interrupted and
	 * restarted.
	 */
	public void play() {
		if (sound.isActive())
			sound.stop();
		sound.setFramePosition(0);
		sound.start();
	}
}