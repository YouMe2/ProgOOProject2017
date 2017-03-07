package de.uni_kiel.progOOproject17.controller.abs;

import de.uni_kiel.progOOproject17.model.abs.Ticked;
import de.uni_kiel.progOOproject17.model.abs.TickedBaseModel;
import de.uni_kiel.progOOproject17.view.abs.InputView;
import de.uni_kiel.progOOproject17.view.abs.OutputView;

/**
 * This class provides a basic ticked program loop, together with the
 * {@link TickedBaseModel}. Once started, the loop continuously updates the
 * model every tick and renders the view in a smart way. In particular this
 * guarantees the same program behavior on nearly every machine independently of
 * the speed to the gpu or cpu.
 *
 * @see #start(long)
 * @see #stop()
 * @see #getFps()
 * @see #getTps()
 *
 *
 */
public abstract class TickedController extends AbstractController implements Runnable, Ticked {

	private Thread thread;

	private boolean running = false;

	private int ticklength = 20;// ms = 1000/50 -> 50 ticks/sec
	private int fps = 0;
	private int tps = 0;
	private long gametime = 0;
	private boolean printTpsFps = false;

	/**
	 * Constructs a new {@link TickedController} with out set as the
	 * {@link #standardOut}, in set as {@link #standardIn}, model set as
	 * {@link #model} and the ticklength in ms.
	 *
	 * @param out
	 *            The {@link #standardOut}
	 * @param in
	 *            The {@link #standardIn}
	 * @param model
	 *            The {@link #model}
	 * @param ticklength
	 *            The ticklength in milliseconds
	 */
	public TickedController(OutputView out, InputView in, TickedBaseModel model, int ticklength) {
		super(out, in, model);
		this.ticklength = ticklength;
	}

	/**
	 * Constructs a new {@link TickedController} with out set as the
	 * {@link #standardOut}, in set as {@link #standardIn}, model set as
	 * {@link #model} and a standart tickspeed of 20ms.
	 *
	 * @param out
	 *            The {@link #standardOut}
	 * @param in
	 *            The {@link #standardIn}
	 * @param model
	 *            The {@link #model}
	 */
	public TickedController(OutputView out, InputView in, TickedBaseModel model) {
		super(out, in, model);
	}

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {

		// time allways in ms!

		long oldTime = System.currentTimeMillis();
		long timeAccumulator = 0;

		int secTimer = 0;
		int frames = 0;
		int ticks = 0;

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
				if (printTpsFps)
					System.out.println("Running at: " + tps + " tps and " + fps + " fps.");

			}

		}
	}

	/**
	 * Starts the ticked program loop with the given timestamp.
	 *
	 * @param timestamp
	 *            The timestamp for the program to start with
	 */
	public void start(long timestamp) {
		if (thread == null) {
			gametime = timestamp;
			thread = new Thread(this);
			running = true;
			timestamp = 0;
			thread.start();
		}
	}

	/**
	 * Stops the program loop and return the timestamp of the programm.
	 *
	 * @return The timestamp
	 */
	public long stop() {
		running = false;
		thread = null;
		return gametime;
	}

	/**
	 * @see de.uni_kiel.progOOproject17.model.abs.Ticked#tick(long)
	 */
	@Override
	public void tick(long timestamp) {
		getModel().tick(timestamp);
	}

	/**
	 * @return the current frames per second
	 */
	public int getFps() {
		return fps;
	}

	/**
	 * @return the current ticks per second
	 */
	public int getTps() {
		return tps;
	}

	/**
	 * Enables or disables this controller to print out the fps und tps every
	 * second.
	 *
	 * @param enable
	 */
	public void setEnableTpsFpsPrint(boolean enable) {
		printTpsFps = enable;
	}
	
	/**
	 * Toggels this controller to print out the fps und tps every
	 * second.
	 *
	 * @param enable
	 */
	public void toggelEnableTpsFpsPrint() {
		printTpsFps = !printTpsFps;
	}

	/**
	 * Returns the {@link TickedBaseModel} of this {@link TickedController}.
	 *
	 * @see de.uni_kiel.progOOproject17.controller.abs.AbstractController#getModel()
	 */
	@Override
	public abstract TickedBaseModel getModel();

}
