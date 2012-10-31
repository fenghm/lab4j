package fenghm.event4j.sample;

import fenghm.event4j.Event;
import fenghm.event4j.EventHandler;
import fenghm.event4j.EventType;

public class HelloEventGroupHandler implements EventHandler {

	private EventType<?,?,?> lastEventType;
	private Event            lastEvent;
	
	public void onEvent(EventType<?, ?, ?> eventType, Event event) {
		this.lastEventType = eventType;
		this.lastEvent     = event;
		
		System.out.println(" event type '" + event.getClass().getSimpleName() + "' fire an event : " + event.toString());
    }

	public EventType<?, ?, ?> getLastEventType() {
		return lastEventType;
	}

	public Event getLastEvent() {
		return lastEvent;
	}
}
