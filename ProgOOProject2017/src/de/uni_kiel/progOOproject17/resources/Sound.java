package de.uni_kiel.progOOproject17.resources;

import javax.sound.sampled.Clip;

public class Sound {

	// public static final Sound PING = new Sound("pickup");
	// public static final Sound PLAYER_HURT = new Sound("playerhurt");
	// public static final Sound PLAYER_DEATH = new Sound("death");
	// public static final Sound MONSTER_HURT = new Sound("monsterhurt");
	// public static final Sound TEST = new Sound("test");
	// public static final Sound BOSS_DEATH = new Sound("bossdeath");
	// public static final Sound CRAFT = new Sound("craft");

	private Clip sound; // Creates a audio clip to be played

	Sound(Clip sound) {
		this.sound = sound;
	}

	public void play() {
		if (sound.isActive())
			sound.stop();
		sound.setFramePosition(0);
		sound.start();
	}
}