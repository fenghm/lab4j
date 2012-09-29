/**
 * This file created at 2012-9-28.
 *
 * Copyright (c) 2002-2012 Bingosoft, Inc. All rights reserved.
 */
package fenghm.event4j.sample;

import fenghm.event4j.EventManager;
import fenghm.event4j.EventTypeRegistration;

public class HelloEventProvider {

	private final EventManager eventManager;
	
	public HelloEventProvider(EventManager eventManager, EventTypeRegistration registration){
		this.eventManager = eventManager;
		registration.addEventType(HelloEvent.TYPE);
	}
	
	public void sayHello(String message){
		eventManager.fireEvent(HelloEvent.TYPE, new HelloEvent(message));
	}
}
