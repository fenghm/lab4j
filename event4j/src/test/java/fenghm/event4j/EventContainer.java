/**
 * This file created at 2012-9-28.
 *
 * Copyright (c) 2002-2012 Bingosoft, Inc. All rights reserved.
 */
package fenghm.event4j;

import fenghm.event4j.EventManager;
import fenghm.event4j.EventManagerImpl;

public class EventContainer {
	private static final EventContainer INSTANCE = new EventContainer();
	
	public static EventContainer get() {
		return INSTANCE;
	}

	private final EventManagerImpl eventManager = new EventManagerImpl();
	
	public EventManager getEventManager() {
		return eventManager;
	}
	
	public EventTypeRegistration getEventTypeRegistration(){
		return eventManager.getEventTypeRegistration();
	}
}
