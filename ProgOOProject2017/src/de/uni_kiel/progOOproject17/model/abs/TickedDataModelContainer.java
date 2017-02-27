package de.uni_kiel.progOOproject17.model.abs;

import java.util.ArrayList;
import java.util.Vector;

public abstract class TickedDataModelContainer<T extends Ticked> extends TickedDataModel{
    
    protected final ArrayList<T> subData = new ArrayList<T>();
    
    public void tick(long timestamp) {
	synchronized (subData) {
	    
	    for (Ticked sD : subData) {
		sD.tick(timestamp);
	    }
	}
    }
    
}
