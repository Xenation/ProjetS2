package fr.iutvalence.info.dut.m2107.events;

/**
 * Defines a sender and provides shortcut methods
 * @author Xenation
 *
 */
public interface Sender {
	
	/**
	 * Sends an event to all the listeners of this sender
	 * @param event the event to send
	 */
	public default void sendPreciseEvent(Event event) {
		SenderManager.sendPreciseEvent(this, event);
	}
	
	/**
	 * Registers a listener to this sender
	 * @param listener the listener to register
	 */
	public default void registerListener(Listener listener) {
		SenderManager.registerPreciseListener(this, listener);
	}
	
	/**
	 * Unregisters a listener from this sender
	 * @param listener the listener to unregister
	 */
	public default void unregisterListener(Listener listener) {
		SenderManager.unregisterPreciseListener(this, listener);
	}
	
}
