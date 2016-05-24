# EVENTS

## Broadcast Events

### How to Listen
 * Listening class must implement Listener (fr.iutvalence.info.dut.m2107.events.Listener)
 * Listening class must possess a method with a name as `get[EventName]([EventClass] event)` where [EventName] is [EventClass] without "Event" in the end
 * Listener must register itself using `EventManager.register(Listener listener)` where `listener` the listener itself
 * When the listener is destroyed it must be unregistered using `EventManager.unregister(Listener listener)` where `listener` is the listener itself


### How to Make a new Event
 * The new Event must be in a new class in a new file
 * The Event's class name must be `{EventName}Event` where {EventName} is the name of the event (ex: TileActivatedEvent)
 * The Event's class must inherit of Event
 * The Event's class must have a `protected static handlerClasses` field of type `Map<Class<?>, Method>`
 * The Event's class must have a `public static handlers` field of type `HandlerList` (initialised to `new HandlerList()`)
 * The Event's class must contain a `public static void init()` method that initialises the handlerClasses to `ListenersScanner.getHandlers({EventClass}.class)` where {EventClass} is event's class name
 * It is recommended to have a field that contains a reference to the object that triggered this Event

##### Exemple
```java
public class EntityCollisionEvent extends EntityEvent {
	
	protected static Map<Class<?>, Method> handlerClasses;
	
	public static HandlerList handlers = new HandlerList();
	
	public static void init() {
		handlerClasses = ListenersScanner.getHandlers(EntityCollisionEvent.class);
	}
	
	public EntityCollisionEvent(Entity entity) {
		super(entity);
	}
	
}
```

## Precise Events

### How to Listen
 * Listener class must implement Listener (fr.iutvalence.info.dut.m2107.events.Listener)
 * Listener class must possess a method with a name as `on[EvenName]([EventClass] event)` where `[EventName]` is `[EventClass]` without "Event" in the end
 * Listening instance must register itself on a sender using `[sender].registerListener([listener])` where `[sender]` is the sender instance and `[listener]` is the listening instance
 * Listening instance must unregister itself when it is destroyed using `[sender].unregisterListener([listener])` where sender is the sender it was previously registered to and `[listener]` is the listening instance
 * Notice : A listener can listen for multiple senders (you need to unregister of all the sender when destroyed)

### How to Send
 * Sender class must implement Sender (fr.iutvalance.ifo.dut.m2107.events.Sender)
 * Sending instance uses `sendPreciseEvent([event])` method to send an event

### Making an Event
 * While Events that work with broadcasting will work with precise, Events that work with precise do not need to meet the requirements for broadcasting
 * Event class must be named `[eventName]Event` where `[eventName]` is the name of the event
 * Event class must extend Event

#### Exemple
```java
public class PlayerWonEvent extends Event {
	
}
```
