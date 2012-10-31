/**
 * This file created at 2012-9-28.
 *
 * Copyright (c) 2002-2012 Bingosoft, Inc. All rights reserved.
 */
package fenghm.event4j.sample;

import fenghm.event4j.Event;
import fenghm.event4j.EventHandler;
import fenghm.event4j.EventHandlers;
import fenghm.event4j.EventType;
import fenghm.event4j.EventTypeGroup;
import fenghm.event4j.ThrowableAction;

public class HelloEvent implements Event{
	
	public static final EventTypeGroup<EventHandler> GROUP = new EventTypeGroup<EventHandler>() {
		public void fire(EventType<?, ?, ?> eventType, Event event, EventHandler handler) {
	        handler.onEvent(eventType, event);
        }
	};
	
	public static final EventType<HelloEvent, HelloEventHandler,EventHandler> TYPE = 
		new EventType<HelloEvent, HelloEventHandler,EventHandler>() {
		
			public EventTypeGroup<EventHandler> group() {
	            return GROUP;
            }

			public void fire(final HelloEvent event, EventHandlers<HelloEventHandler> handlers, EventHandlers<EventHandler> groupHandlers) {
		        handlers.fire(new ThrowableAction<HelloEventHandler>() {
					public void execute(HelloEventHandler handler) throws Throwable {
						handler.onHello(event);
					}
				});	
		        
		        final EventType<?,?,?> eventType = this;
		        groupHandlers.fire(new ThrowableAction<EventHandler>() {
					public void execute(EventHandler handler) throws Throwable {
						group().fire(eventType,event, handler);
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