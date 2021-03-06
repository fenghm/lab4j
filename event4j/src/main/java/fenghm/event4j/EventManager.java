/**
 * This file created at 2012-8-30.
 *
 * Copyright (c) 2002-2012 Bingosoft, Inc. All rights reserved.
 */
package fenghm.event4j;

public interface EventManager {
	
	EventHandlerRegistration getEventHandlerRegistration();
	
	<E extends Event,TH,GH> void fireEvent(EventType<E,TH,GH> eventType,E event);
}