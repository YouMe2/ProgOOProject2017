package de.uni_kiel.progOOproject17.tests.mvc.abst;

import javax.swing.Action;
import javax.swing.ActionMap;

public interface InputView {

    public void addKeyAction(String key, Action action);
    
    public void addKeyActionMap(ActionMap actionMap);
    
    public void setEnabeled(boolean enabeled);
    
    public void setEnabeled(String key, boolean enabeled);
    
}
