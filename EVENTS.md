# EVENTS

### How to Listen
 * Listening class must implement Listener (fr.iutvalence.info.dut.m2107.events.Listener)
 * Listening class must possess a method with a name as `get{EventName}(EventClass event)` where {EventName} is {EventClass} without "Event" in the end
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