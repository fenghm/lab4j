package fenghm.event4j;

public interface EventTypeGroup<H> {
	
	public static final EventTypeGroup<EventHandler> NONE = new EventTypeGroup<EventHandler>(){
		public void fire(EventType<?, ?, ?> eventType, Event event,EventHandler handler) {
	        //do nothing
        }
	};
	
	void fire(EventType<?,?,?> eventType,Event event,H handler);
	
}