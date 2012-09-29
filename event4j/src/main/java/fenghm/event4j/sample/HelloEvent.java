/**
 * This file created at 2012-9-28.
 *
 * Copyright (c) 2002-2012 Bingosoft, Inc. All rights reserved.
 */
package fenghm.event4j.sample;

import fenghm.event4j.Event;
import fenghm.event4j.EventHandlers;
import fenghm.event4j.EventType;
import fenghm.event4j.ThrowableAction;

public class HelloEvent implements Event{
	
	public static final EventType<HelloEvent, HelloEventHandler> TYPE = 
		new EventType<HelloEvent, HelloEventHandler>() {
			public void fire(final HelloEvent event, EventHandlers<HelloEventHandler> handlers) {
		        handlers.fire(new ThrowableAction<HelloEventHandler>() {
					public void execute(HelloEventHandler handler) throws Throwable {
						handler.onHello(event);
					}
				});			
			}
		};

	private final String message;
	
	public HelloEvent(String message){
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

}