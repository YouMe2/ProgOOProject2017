/**
 *
 */
package de.uni_kiel.progOOproject17.launch;

import de.uni_kiel.progOOproject17.controller.PLController;
import de.uni_kiel.progOOproject17.model.PLGameModel;
import de.uni_kiel.progOOproject17.resources.ResourceManager;
import de.uni_kiel.progOOproject17.view.PLDektopView;
import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * @author Yannik Eikmeier
 * @since 23.02.2017
 */
public class Main {

	// TODO class Velocity statt Dimension
	// TODO PLGameModel aufbauen
	// TODO LevelGenerator
	// TODO Scoreboard
	// TODO Lighthouse util

	public static void main(String[] args) {

		ResourceManager.getInstance().init();

		new PLController(
				new PLDektopView(
						"Project Lighthouze - Yannik Eikemeier und Steffen Trog"),
				new PLGameModel()).start();

	}

}
