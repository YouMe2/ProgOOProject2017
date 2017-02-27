package de.uni_kiel.progOOproject17.sound;

import java.applet.Applet;
import java.applet.AudioClip;

public class Sound {

	public static final Sound PING = new Sound("pickup");
	public static final Sound PLAYER_HURT = new Sound("playerhurt");
	public static final Sound PLAYER_DEATH = new Sound("death");
	public static final Sound MONSTER_HURT = new Sound("monsterhurt");
	public static final Sound TEST = new Sound("test");
	public static final Sound BOSS_DEATH = new Sound("bossdeath");
	public static final Sound CRAFT = new Sound("craft");

	private AudioClip clip; // Creates a audio clip to be played

	private Sound(String name) {
		// clip = Resources.getInstance().getSound(name);
		try {

			// tries to load the audio clip from the name you gave above.
			clip = Applet.newAudioClip(Sound.class.getResource(name));

		} catch (Throwable e) {
			e.printStackTrace(); // else it will throw an error
		}
	}

	public void play() {
		try {
			new Thread() { // creates a new thread (string of events)
				@Override
				public void run() { // runs the thread
					clip.play(); // plays the sound clip when called
				}
			}.start(); // starts the thread
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
}