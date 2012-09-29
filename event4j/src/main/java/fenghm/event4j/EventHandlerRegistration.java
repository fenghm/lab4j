/**
 * This file created at 2012-9-28.
 *
 * Copyright (c) 2002-2012 Bingosoft, Inc. All rights reserved.
 */
package fenghm.event4j;

public interface EventHandlerRegistration {
	<E extends Event,H> void addEventHandler(EventType<E,H> eventType,H handler);
}