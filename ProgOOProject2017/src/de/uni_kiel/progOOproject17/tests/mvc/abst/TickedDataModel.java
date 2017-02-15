package de.uni_kiel.progOOproject17.tests.mvc.abst;

import java.util.ArrayList;

public abstract class TickedDataModel extends AbstractDataModel{

    protected ArrayList<TickedDataModel> subData = new ArrayList<>();
    
    public void tick(long timestamp) {
	for (TickedDataModel sD : subData) {
	    sD.tick(timestamp);
	}
    }
    
}
