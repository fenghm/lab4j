/**
 * This file created at 2012-9-28.
 *
 * Copyright (c) 2002-2012 Bingosoft, Inc. All rights reserved.
 */
package fenghm.event4j.sample;

import fenghm.event4j.EventManager;

public class HelloEventConsumer implements HelloEventHandler {
	
	private HelloEvent lastEvent;

	public HelloEventConsumer(EventManager eventManager){
		eventManager.getEventHandlerRegistration().addEventHandler(HelloEvent.TYPE, this);
	}

	public void onHello(HelloEvent event) {
		this.lastEvent = event;
		System.out.println("on event : " + event.getMessage());
    }

	public HelloEvent getLastEvent() {
		return lastEvent;
	}
}