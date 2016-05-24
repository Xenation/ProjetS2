package fr.iutvalence.info.dut.m2107.events;

public interface Sender {
	
	public default void sendPreciseEvent(Event event) {
		SenderManager.sendPreciseEvent(this, event);
	}
	
	public default void registerListener(Listener listener) {
		SenderManager.registerPreciseListener(this, listener);
	}
	
}
