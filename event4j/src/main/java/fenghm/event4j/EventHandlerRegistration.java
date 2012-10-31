/**
 * This file created at 2012-9-28.
 *
 * Copyright (c) 2002-2012 Bingosoft, Inc. All rights reserved.
 */
package fenghm.event4j;

public interface EventHandlerRegistration {
	
	<E extends Event,TH,GH> void addEventHandler(EventType<E,TH,GH> eventType,TH handler);
	
	<H> void addGroupHandler(EventTypeGroup<H> group,H handler);
	
}