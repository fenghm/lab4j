/**
 * This file created at 2012-9-28.
 *
 * Copyright (c) 2002-2012 Bingosoft, Inc. All rights reserved.
 */
package fenghm.event4j;


public interface EventTypeRegistration {
	
	<E extends Event,H> void addEventType(EventType<E, H> eventType);
	
}