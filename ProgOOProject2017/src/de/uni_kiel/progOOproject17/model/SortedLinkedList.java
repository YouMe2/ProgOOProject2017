/**
 * 
 */
package de.uni_kiel.progOOproject17.model;

import java.util.Collection;
import java.util.LinkedList;
import java.util.ListIterator;

/**
 * @author Yannik Eikmeier
 * @since 30.03.2017
 *
 */
public class SortedLinkedList<T extends Priority> extends LinkedList<T> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 944137668105543179L;
	private float maxPri = 0;
	

	@Override
	public boolean add(T e) {

		if (e.getPriority() >= maxPri) {
			maxPri = e.getPriority();
			addLast(e);
		}
		
		
		ListIterator<T> it = listIterator();
		
		int index = size();
		
		while (it.hasNext()) {
			
			T t = it.next();
			
			if (e.getPriority() <= t.getPriority()) {
				index = it.previousIndex();
				break;
			}
		}
		
		if(index == size()){
			addLast(e);
			System.out.println(" messed up list? maybe?");
			return true;
		}
		else {
			add(index, e);
			return true;			
		}
	}
	
	public void updatePosition(T e) {	
		remove(e);
		add(e);
	}
	
	/* (non-Javadoc)
	 * @see java.util.LinkedList#addAll(java.util.Collection)
	 */
	@Override
	public boolean addAll(Collection<? extends T> c) {
		
		if (c == null)
			return false;
		if (c.isEmpty())
			return false;
		
		for (T e : c) {
			add(e);			
		}
		
		return true ;
	}
	
	
}
