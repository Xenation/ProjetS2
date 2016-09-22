# Tasks

## **Precise Event Listeners**
* A Listener can listen for event coming from a certain object
* Shouldn't use general sending system
* PreciseSender interface:
	* `registerListener(Listener)`: adds a listener use `default` to avoid rewriting the code
	* `sendPreciseEvent(Sender, Event)`: sends an event to all the listeners of the given sender
* SenderManager:
	* `preciseListeners`: `Map<Sender, List<Listener>>` it links a sender (key) to a list of listeners (value)

## **Optimised tile updates**
* **`softUpdade()`**: updates only the necessary (ex: attributes)
* **`heavyUpdate()`**: a update that shouldn't be done every frame because it needs more processing
* try to trigger most heavy updates that are caused by exterior objects through events (precise events)