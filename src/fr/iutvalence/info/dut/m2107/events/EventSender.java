package fr.iutvalence.info.dut.m2107.events;

import java.lang.reflect.InvocationTargetException;

public class EventSender {
	
	public static void sendEvent(TileActivatedEvent event) {
		for (Class<?> cla : TileActivatedEvent.handlerClasses.keySet()) {
			for (Listener listener : event.handlers.getListeners()) {
				try {
					TileActivatedEvent.handlerClasses.get(cla).invoke(listener, event);
				} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
}
