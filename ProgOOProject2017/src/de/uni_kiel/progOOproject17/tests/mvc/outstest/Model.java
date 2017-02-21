package de.uni_kiel.progOOproject17.tests.mvc.outstest;

import java.awt.Color;

import de.uni_kiel.progOOproject17.tests.mvc.abst.m.AbstractDataModel;

public class Model extends AbstractDataModel {

    private Color c;
    
   
    
    public Model() {
	changeColor();
    }
    
    public void changeColor() {
	c = new Color((int)(Math.random()*256*256*256));
    }

   
    public Color getBGColor() {
	return c;
    }
    
    
    
    

}
