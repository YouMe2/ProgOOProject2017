package de.uni_kiel.progOOproject17.tests.mvc.abst;

import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.KeyStroke;

public class MappedInputView implements InputView {

    private InputMap inMap;
    private ActionMap aMap;
    private boolean enabeled = false;

    public void initMaps(InputMap inMap, ActionMap aMap) {
	this.inMap = inMap;
	this.aMap = aMap;
	enabeled = true;

    }

    @Override
    public void addKeyAction(String actionKey, Action action) {
	KeyStroke key = KeyStroke.getKeyStroke(actionKey);
	if (enabeled) {
	    inMap.put(key, key);
	} else {
	    inMap.put(key, "none");
	}
	aMap.put(key, action);
    }
    
    public void addAction(KeyStroke key, Action action) {
	if (enabeled) {
	    inMap.put(key, key);
	} else {
	    inMap.put(key, "none");
	}
	aMap.put(key, action);
    }

    public void setEnabeled(boolean enabeled) {
	this.enabeled = enabeled;

	for (KeyStroke key : inMap.keys()) {

	    if (enabeled) {
		inMap.put((KeyStroke) key, key);
	    } else {
		inMap.put((KeyStroke) key, "none");
	    }

	}

    }

    @Override
    public void setEnabeled(String actionKey, boolean enabeled) {
	KeyStroke key = KeyStroke.getKeyStroke(actionKey);
	if (enabeled) {
	    inMap.put((KeyStroke) key, key);
	} else {
	    inMap.put((KeyStroke) key, "none");
	}

    }

    @Override
    public void addKeyActionMap(ActionMap actionMap) {
	
	for (Object o : actionMap.keys()) {	    
	    addKeyAction((String)o, actionMap.get(o));
	    
	}
    }

}
