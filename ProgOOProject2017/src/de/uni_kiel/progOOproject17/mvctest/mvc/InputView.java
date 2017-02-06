package de.uni_kiel.progOOproject17.mvctest.mvc;

import javax.swing.Action;
import javax.swing.ActionMap;

public interface InputView {

    public void addAction(String actionKey, Action action);
    
    public void addActionMap(ActionMap actionMap);
    
    public void setEnabeled(boolean enabeled);
    
    public void setEnabeled(String actionKey, boolean enabeled);
    
}
