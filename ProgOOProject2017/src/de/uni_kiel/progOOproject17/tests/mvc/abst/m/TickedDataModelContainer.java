package de.uni_kiel.progOOproject17.tests.mvc.abst.m;

import java.util.Vector;

public abstract class TickedDataModelContainer<T extends TickedDataModel> extends TickedDataModel{
    
    protected final Vector<T> subData = new Vector<T>();
    
    public void tick(long timestamp) {
	
	for (Ticked sD : subData) {
	    sD.tick(timestamp);
	}
    }
    
}
