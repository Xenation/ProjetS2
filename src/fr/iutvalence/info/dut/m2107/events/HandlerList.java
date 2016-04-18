package fr.iutvalence.info.dut.m2107.events;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Defines a list of listeners
 * @author Xenation
 *
 */
public class HandlerList implements Iterable<Listener> {
	
	/**
	 * The list of listeners
	 */
	private List<Listener> listeners = new ArrayList<Listener>();
	
	/**
	 * Adds a listener
	 * @param listener the listener to add
	 */
	public void add(Listener listener) {
		listeners.add(listener);
	}
	
	/**
	 * Removes a listener
	 * @param listener the listener to remove
	 */
	public void remove(Listener listener) {
		listeners.remove(listener);
	}
	
	/**
	 * Returns the list of all listeners
	 * @return the list of all listeners
	 */
	public List<Listener> getListeners() {
		return this.listeners;
	}
	
	//// ITERATOR \\\\
	@Override
	public Iterator<Listener> iterator() {
		return listeners.iterator();
	}
	
}
