/**
 * This file created at 2012-9-28.
 *
 * Copyright (c) 2002-2012 Bingosoft, Inc. All rights reserved.
 */
package fenghm.event4j;

public interface EventType<E extends Event,TH,GH> {
	
	EventTypeGroup<GH> group();
	
	void fire(E event,EventHandlers<TH> handlers,EventHandlers<GH> groupHandlers);
	
}