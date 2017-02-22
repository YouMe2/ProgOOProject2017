package de.uni_kiel.progOOproject17.tests.mvc.abst.v;

import javax.swing.Action;
import javax.swing.ActionMap;

public interface InputView {

    public void addAction(String key, Action action);
    
    public void addActionMap(ActionMap actionMap);
    
    public void setEnabeled(boolean enabeled);
    
    public void setEnabeled(String key, boolean enabeled);
    
}
