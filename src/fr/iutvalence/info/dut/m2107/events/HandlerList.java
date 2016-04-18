package fr.iutvalence.info.dut.m2107.events;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class HandlerList implements Iterable<Listener> {
	
	private List<Listener> listeners = new ArrayList<Listener>();
	
	public void add(Listener listener) {
		listeners.add(listener);
	}
	
	public List<Listener> getListeners() {
		return this.listeners;
	}

	@Override
	public Iterator<Listener> iterator() {
		return listeners.iterator();
	}
	
}
