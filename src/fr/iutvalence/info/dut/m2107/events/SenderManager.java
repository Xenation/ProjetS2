package fr.iutvalence.info.dut.m2107.events;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Manages the links of sender/listener for precise events.
 * @author Xenation
 *
 */
public class SenderManager {
	
	/**
	 * Map that groups stores all the listeners for a sender
	 */
	private static Map<Sender, List<Listener>> preciseListeners = new HashMap<Sender, List<Listener>>();
	
	
	/**
	 * Registers a listener to a sender
	 * @param sender the sender
	 * @param listener the listener
	 */
	public static void registerPreciseListener(Sender sender, Listener listener) {
		List<Listener> listens = preciseListeners.get(sender);
		if (listens == null) {
			listens = new ArrayList<Listener>();
			preciseListeners.put(sender, listens);
		}
		listens.add(listener);
	}
	
	/**
	 * Unregisters a listener from a sender
	 * @param sender the sender
	 * @param listener the listener
	 */
	public static void unregisterPreciseListener(Sender sender, Listener listener) {
		List<Listener> listeners = preciseListeners.get(sender);
		if (listeners != null) {
			listeners.remove(listener);
			if (listeners.isEmpty()) {
				preciseListeners.remove(sender);
			}
		}
	}
	
	/**
	 * Sends a given event to all listeners of the specified sender
	 * @param sender the sender
	 * @param event the event
	 */
	public static void sendPreciseEvent(Sender sender, Event event) {
		List<Listener> listeners = preciseListeners.get(sender);
		if (listeners != null) {
			for (Listener listener : listeners) {
				Method handlingMeth = ListenersScanner.getPreciseHandler(listener, event.getClass());
				if (handlingMeth != null) {
					try {
						handlingMeth.invoke(listener, event);
					} catch (IllegalAccessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IllegalArgumentException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
	}
	
}
