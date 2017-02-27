package de.uni_kiel.progOOproject17.controller.abs;

import de.uni_kiel.progOOproject17.model.abs.Ticked;
import de.uni_kiel.progOOproject17.model.abs.TickedDataModel;
import de.uni_kiel.progOOproject17.view.abs.InputView;
import de.uni_kiel.progOOproject17.view.abs.OutputView;

<<<<<<< HEAD
public abstract class TickedController extends AbstractController
		implements Runnable, Ticked {
=======
public abstract class TickedController extends AbstractController implements Runnable, Ticked {
>>>>>>> refs/heads/sidecrawlerBaseEngine

	private Thread thread;
<<<<<<< HEAD
	private boolean runnig = false;
=======
	private boolean running = false;
>>>>>>> refs/heads/sidecrawlerBaseEngine
	private int ticklength = 20;// ms = 1000/50 -> 50 ticks/sec
	private int fps = 0;
	private int tps = 0;
	private long gametime = 0;
	private boolean printTpsFps = false;

<<<<<<< HEAD
	public TickedController(OutputView out, InputView in, TickedDataModel model,
			int ticklength) {
=======
	public TickedController(OutputView out, InputView in, TickedDataModel model, int ticklength) {
>>>>>>> refs/heads/sidecrawlerBaseEngine
		super(out, in, model);
		this.ticklength = ticklength;
	}

<<<<<<< HEAD
	public TickedController(OutputView out, InputView in,
			TickedDataModel model) {
=======
	public TickedController(OutputView out, InputView in, TickedDataModel model) {
>>>>>>> refs/heads/sidecrawlerBaseEngine
		super(out, in, model);
	}

	@Override
	public void run() {

		// time allways in ms!

		long oldTime = System.currentTimeMillis();
		long timeAccumulator = 0;

		int secTimer = 0;
		int frames = 0;
		int ticks = 0;

<<<<<<< HEAD
		while (runnig) {

			long newTime = System.currentTimeMillis();
			long frameTime = newTime - oldTime;
			oldTime = newTime;

			secTimer += frameTime;
			timeAccumulator += frameTime; // this is how much time the game
			// has to calculate ticks for

			if (timeAccumulator >= ticklength) {

				while (timeAccumulator >= ticklength) {
					// System.out.println("tick");
					ticks++;
					tick(gametime);
					timeAccumulator -= ticklength;
					gametime += ticklength;
				}
				// System.out.println("render");
				frames++;
				renderAllViews();

			}

			if (secTimer >= 1000) {
				fps = frames;
				tps = ticks;
				secTimer = 0;
				frames = 0;
				ticks = 0;
				if (printTpsFps)
					System.out.println(
							"Running at: " + tps + " tps and " + fps + " fps.");

			}

		}
	}

	public void start() {
		if (thread == null) {
			thread = new Thread(this);
			runnig = true;
			gametime = 0;
			thread.start();
		}
	}

	public void stop() {
		runnig = false;
		thread = null;
	}

	@Override
=======
		while (running) {

			long newTime = System.currentTimeMillis();
			long frameTime = newTime - oldTime;
			oldTime = newTime;

			secTimer += frameTime;
			timeAccumulator += frameTime; // this is how much time the game
			// has to calculate ticks for

			if (timeAccumulator >= ticklength) {

				do {
					// System.out.println("tick");
					ticks++;
					tick(gametime);
					timeAccumulator -= ticklength;
					gametime += ticklength;
				} while (timeAccumulator >= ticklength);
				// System.out.println("render");
				frames++;
				renderAllViews();

			}

			if (secTimer >= 1000) {
				fps = frames;
				tps = ticks;
				secTimer = 0;
				frames = 0;
				ticks = 0;
				if (printTpsFps) {
					System.out.println("Running at: " + tps + " tps and " + fps + " fps.");
				}

			}

		}
	}

	public void start() {
		if (thread == null) {
			thread = new Thread(this);
			running = true;
			gametime = 0;
			thread.start();
		}
	}

	public void stop() {
		running = false;
		thread = null;
	}

>>>>>>> refs/heads/sidecrawlerBaseEngine
	public void tick(long timestamp) {
		getModel().tick(timestamp);
	}

	/**
	 * @return the fps
	 */
	public int getFps() {
		return fps;
	}

	/**
	 * @return the tps
	 */
	public int getTps() {
		return tps;
	}

	public void setEnableTpsFpsPrint(boolean enable) {
		printTpsFps = enable;
	}

	@Override
	public abstract TickedDataModel getModel();

}
