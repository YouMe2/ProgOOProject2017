package de.uni_kiel.progOOproject17.mvctest.mvc;

import javax.swing.AbstractAction;

public interface InputView {

    public void addAction(String actionKey, AbstractAction action);
    
    public void setEnabeled(boolean enabeled);
    
    public void setEnabeled(String actionKey, boolean enabeled);
    
}
