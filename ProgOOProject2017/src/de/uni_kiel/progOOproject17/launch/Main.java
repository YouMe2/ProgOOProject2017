package de.uni_kiel.progOOproject17.launch;

import de.uni_kiel.progOOproject17.controller.PLController;
import de.uni_kiel.progOOproject17.controller.abs.AbstractController;
import de.uni_kiel.progOOproject17.controller.abs.TickedController;
import de.uni_kiel.progOOproject17.model.Background;
import de.uni_kiel.progOOproject17.model.Block;
import de.uni_kiel.progOOproject17.model.Enemy;
import de.uni_kiel.progOOproject17.model.Floor;
import de.uni_kiel.progOOproject17.model.GameScreen;
import de.uni_kiel.progOOproject17.model.PLBaseModel;
import de.uni_kiel.progOOproject17.model.Particle;
import de.uni_kiel.progOOproject17.model.Player;
import de.uni_kiel.progOOproject17.model.Scoreboard;
import de.uni_kiel.progOOproject17.model.StartMenu;
import de.uni_kiel.progOOproject17.model.Stats;
import de.uni_kiel.progOOproject17.model.abs.AbstractBaseModel;
import de.uni_kiel.progOOproject17.model.abs.Collidable;
import de.uni_kiel.progOOproject17.model.abs.Deadly;
import de.uni_kiel.progOOproject17.model.abs.Destroyable;
import de.uni_kiel.progOOproject17.model.abs.Environment;
import de.uni_kiel.progOOproject17.model.abs.GameComponent;
import de.uni_kiel.progOOproject17.model.abs.GameElement;
import de.uni_kiel.progOOproject17.model.abs.GameEntity;
import de.uni_kiel.progOOproject17.model.abs.GameObject;
import de.uni_kiel.progOOproject17.model.abs.Gravitational;
import de.uni_kiel.progOOproject17.model.abs.Screen;
import de.uni_kiel.progOOproject17.model.levelgen.LevelGenerator;
import de.uni_kiel.progOOproject17.resources.GameProperties;
import de.uni_kiel.progOOproject17.resources.ResourceManager;
import de.uni_kiel.progOOproject17.view.PLDektopView;
import de.uni_kiel.progOOproject17.view.PLLighthouseView;
import de.uni_kiel.progOOproject17.view.abs.FramedIOView;
import de.uni_kiel.progOOproject17.view.abs.InputView;
import de.uni_kiel.progOOproject17.view.abs.MappedKeyInput;
import de.uni_kiel.progOOproject17.view.abs.OutputView;
import de.uni_kiel.progOOproject17.view.abs.Viewable;

/**
 * <h1>Overview</h1> In our interpretation of the MVC pattern the controller is
 * the central core of the program and works as an intermediary between all the
 * views and the model.
 * <p>
 * The views and the controller are completely content-unrelated and the only
 * thing that is content (in this case game)-related is the model. In other
 * words: with this MVC structure it is possible to build basically any 2D-game
 * (or program) by only swapping out the model, because the view and the
 * controller are completely independent of the individual implementation of the
 * model.
 * <h1>Controller</h1> The controller section is divided into three layers of
 * abstraction.
 * <p>
 * The {@link AbstractController} provides the basic functionality of a
 * controller class. It has an {@link AbstractBaseModel}, {@link InputView}s and
 * {@link OutputView}s. Its core ability is to render all views.
 * <p>
 * The {@link TickedController} enhances this functionality by implementing the
 * ticking of the entire program. Every tick, the model is being ticked and then
 * viewed (by all output views).
 * <p>
 * The {@link PLController} specifies that class even further and defines how
 * the controller of our actual game behaves. It refers to instances of a
 * {@link PLDektopView} and a {@link PLLighthouseView}. Also, it specifies that
 * the model is a {@link PLBaseModel} (and thereby it defines that the program
 * is actually a game and not anything else).
 * <h1>View</h1> The view is again separated into two main parts: inputs and
 * outputs. As mentioned above, these are defined by the two interfaces
 * <code>InputView</code> and <code>OutputView</code> which are responsible for
 * handling input events and for outputting data, respectively. An input view
 * provides functionality for registering input events and mapping them to
 * actions while output view are able to render a list of {@link Viewable}s. The
 * (still abstract) class {@link FramedIOView} merges these two functionalities.
 * Note that it differentiates between actions for buttons and actions for keys.
 * The former can be added to the components directly while the latter are
 * managed by an instance of {@link MappedKeyInput} that maps key inputs to
 * actions.
 * <p>
 * Again, in our game, there's two different views, one for the desktop screen
 * and one for the skyscraper. <code>PLDesktopView</code> and
 * <code>PLLighthouseView</code> are both represented by classes extending
 * <code>FramedIOView</code>. The desktop view simply draws the viewables to the
 * screen, the lighthouse view sends them via the network.
 * <h1>Model</h1>
 * <h2>Screens</h2> The particular model in our game itself, similar to the over
 * all MVC structure, contains a massively branched structure which can be split
 * into three smaller parts: one serving as an internal controller for the game
 * model, the other part serving as a structure modeling the in-game GUI and the
 * last part serving as the actual game-related model.
 * <p>
 * The models base and internal game controller is represented by the
 * <code>PLBaseModel</code> class. It holds the most basic constants that are
 * important for the game logic and serves as the only connection point between
 * the upper program's controller and the actual game. Internally, it manages
 * the game GUI components in the form of different {@link Screen}s, and
 * redirects the input actions depending on the currently active screen. It is
 * important to note that those game GUI components are in no relation at all to
 * the views of the upper program itself, they are solely part of the game and
 * its implementation and therefore are part of the model and not the view.
 * <p>
 * So far, the overall class hierarchy can be summarized like this:
 * <img src="mvc_class_hierarchy.png" title="MVC Class hierarchy" alt="MVC Class
 * hierarchy" width="800" height="235">
 * <p>
 * Most of those <code>Screen</code>s are different menus for example the
 * {@link StartMenu} but there is also one special <code>Screen</code>: the
 * {@link GameScreen}. This class itself hosts the game logic and again serves
 * as a smaller kind of controller or manager for the game logic. It is the
 * {@link Environment} for all of the game logic and holds the {@link Player}'s
 * {@link Stats} as well as a {@link LevelGenerator} and a {@link Scoreboard}.
 * <h2>Game class hierarchy</h2> The game logic itself is build upon a big
 * branched structure. The superclass of the game logics structure is
 * {@link GameComponent}. It declares the very basics of every
 * <code>GameComponent</code>, mainly a bounding rect and a tick method. The
 * next more detailed but still abstract class in the hierachy is
 * {@link GameElement}, it specifies that all <code>GameElement</code>s need to
 * be {@link Viewable} and {@link Destroyable} is some form. {@link Particle}s
 * and {@link Background}s are <code>GameElement</code>s.
 * <p>
 * {@link GameObject}s are additionally to what <code>GameElement</code> already
 * specified are {@link Collidable} and implement {@link Deadly} which means
 * they can be set to be deadly for other <code>GameObject</code>s.
 * {@link Floor}s are <code>GameObject</code>s.
 * <p>
 * The last abstract layer is the {@link GameEntity} layer, they are
 * <code>GameObject</code>s and additionally are {@link Gravitational} and
 * provide the functionality of having a velocity and executing movement based
 * on the gravity, their velocity and collisions with other
 * <code>GameObject</code>s. The {@link Enemy}s, {@link Block}s and the
 * {@link Player} are <code>GameEntities</code>. The <code>Player</code> is
 * possibly the most interesting entity, because it implements the game
 * mechanics that are responsible for jumping and crouching, taking damage and
 * earning points.
 * <p>
 * The class hierarchy of the game model looks like this:
 * <img src="model_class_hierarchy.png" title="Class hierarchy in the model" alt
 * ="Class hierarchy in the model" width="800" height="360">
 * <h1>Further comments</h1>
 * <h2>General note to comply with the task:</h2>
 * <ul>
 * <li><strong>Visibility</strong>: as low as possible. All members inside a
 * class should be set to private, except when they really need to be accessed
 * from outside. Even int this case the visibility is supposed to be as
 * restricted as possible to prevent the program from malfunctioning. The fewer
 * possibilities of modifying an object's data there are, the fewer exceptional
 * cases have to be considered. This rule of thumb is applied throughout the
 * entire project.
 * <li><strong>Instance variables versus local variables</strong>: instance
 * variables can be seen as properties of an object, local variables are usually
 * intermediate results inside methods. The two shouldn't be confused. Similar
 * to above, examples to this can be found throughout the entire project.
 * </ul>
 */
public class Main {

	public static void main(String[] args) {

		ResourceManager.getInstance().init();

		new PLController(new PLDektopView(GameProperties.getInstance().getProperty("titleText")), new PLBaseModel())
				.start(0);

	}

}
