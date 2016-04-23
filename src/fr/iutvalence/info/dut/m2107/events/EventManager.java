package fr.iutvalence.info.dut.m2107.events;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * Utility class to Manage events.
 * Run init() before using events.
 * @author Xenation
 *
 */
public class EventManager {
	
	/**
	 * Initialises The ListenerScanner and every detected events
	 */
	public static void init() {
		ListenersScanner.init();
		for (Class<?> eventClass : ListenersScanner.eventClasses) {
			try {
				eventClass.getMethod("init").invoke(null);
			} catch (NoSuchMethodException e) {
				// Ignored not possible
			} catch (SecurityException e) {
				// Ignored
			} catch (IllegalAccessException e) {
				// Ignored
			} catch (IllegalArgumentException e) {
				// Ignored
			} catch (InvocationTargetException e) {
				// Ignored
			}
		}
	}
	
	/**
	 * Sends an event to all listeners of the given event.
	 * @param eventClass the class of the event to send
	 * @param event the event to send
	 */
	public static void sendEvent(Event event) {
		Class<?> eventClass = event.getClass();
		try {
			@SuppressWarnings("unchecked")
			Map<Class<?>, Method> handlerClasses = (Map<Class<?>, Method>) eventClass.getField("handlerClasses").get(null);
			HandlerList handlers = (HandlerList) eventClass.getField("handlers").get(null);
			for (Class<?> cla : handlerClasses.keySet()) {
				for (Listener listener : handlers.getListeners()) {
					try {
						handlerClasses.get(cla).invoke(listener, event);
					} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
						e.printStackTrace();
					}
				}
			}
		} catch (IllegalArgumentException e1) {
			// Ignored
		} catch (IllegalAccessException e1) {
			// Ignored
		} catch (NoSuchFieldException e1) {
			// Ignored
		} catch (SecurityException e1) {
			// Ignored
		}
	}
	
	/**
	 * Registers an instance of a listener into the events that it handles
	 * @param baseClass the Class of the listener (used to detect which events are listened)
	 * @param listener the listener itself
	 */
	public static void register(Listener listener) {
		Class<?> baseClass = listener.getClass();
		if (ListenersScanner.listenersClasses.contains(baseClass)) {
			Method[] baseMethods = baseClass.getMethods();
			for (Class<?> cla : ListenersScanner.eventClasses) {
				for (Method method : baseMethods) {
					if (method.getName().contains(cla.getSimpleName().substring(0, cla.getSimpleName().length()-5))) {
						try {
							Field handlersField = cla.getField("handlers");
							HandlerList handlers = (HandlerList) handlersField.get(null);
							handlers.add(listener);
//							System.out.println("mk"+handlers.getListeners().size());
						} catch (NoSuchFieldException e) {
							System.err.println(baseClass.getName());
							System.err.println(cla.getName());
							e.printStackTrace();
						} catch (SecurityException e) {
							e.printStackTrace();
						} catch (IllegalArgumentException e) {
							e.printStackTrace();
						} catch (IllegalAccessException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
	}
	
	/**
	 * Unregisters an instance of a listener from all the events that had it registered
	 * @param listener the listener to unregister
	 */
	public static void unregister(Listener listener) {
		for (Class<?> cla : ListenersScanner.eventClasses) {
			Field handlersField;
			try {
				handlersField = cla.getField("handlers");
				HandlerList handlers = (HandlerList) handlersField.get(null);
				handlers.remove(listener);
//				System.out.println("rm"+handlers.getListeners().size());
			} catch (NoSuchFieldException e) {
				System.err.println(cla.getName());
				e.printStackTrace();
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
	}
	
}
