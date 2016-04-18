package fr.iutvalence.info.dut.m2107.events;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class EventManager {
	
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
	
	public static void sendEvent(TileActivatedEvent event) {
		for (Class<?> cla : TileActivatedEvent.handlerClasses.keySet()) {
			for (Listener listener : TileActivatedEvent.handlers.getListeners()) {
				try {
					TileActivatedEvent.handlerClasses.get(cla).invoke(listener, event);
				} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public static void register(Class<?> baseClass, Listener listener) {
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
