package fenghm.event4j;

public interface EventHandler {

	void onEvent(EventType<?,?,?> eventType,Event event);
	
}