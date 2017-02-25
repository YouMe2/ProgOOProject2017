/**
 * 
 */
package de.uni_kiel.progOOproject17.model;

import java.awt.Point;

import de.uni_kiel.progOOproject17.model.abs.TickedDataModelContainer;
import de.uni_kiel.progOOproject17.view.abs.Viewable;
import de.uni_kiel.progOOproject17.view.abs.ViewableData;

/**
 * @author Yannik Eikmeier
 * @since 23.02.2017
 *
 */
public class SCGameModel extends TickedDataModelContainer {

    //scoreboard
    //bg?
    //floors?
    //entities?
    //particles?
    //sounds???

    //LevelGenerator
	
	//a Destroyer for the Particles
	//a Destroyer for the Objects
    
    public static final int LH_WIDTH = 28;
    public static final int LH_HEIGHT = 14;
    
    public static final int WIDTH_FACTOR = 15;
    public static final int HEIGHT_FACTOR = 35;
    
    public static final int GAME_WIDTH = LH_WIDTH * WIDTH_FACTOR; // = 420
    public static final int GAME_HEIGHT = LH_HEIGHT * HEIGHT_FACTOR ; // = 490
    
    public SCGameModel(/* params ?*/) {
	//TODO
	
	
	
	
    }
    
    
   
    @Override
    public Viewable getViewable() {
	
	ViewableData vD = new ViewableData();
	
	//TODO
	
	//layer0
	
	//layer1
	
	//layer2
	
	//usw
	
	
	
	
	return vD;
    }
    
    public static Point lhToGameCoord(int x, int y) {
	return new Point(x*WIDTH_FACTOR, y*HEIGHT_FACTOR);
    }
    

}
