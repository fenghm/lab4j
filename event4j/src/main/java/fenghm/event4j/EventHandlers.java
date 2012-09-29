/**
 * This file created at 2012-9-28.
 *
 * Copyright (c) 2002-2012 Bingosoft, Inc. All rights reserved.
 */
package fenghm.event4j;

public interface EventHandlers<H> extends Iterable<H> {
	int size();
	
	boolean isEmpty();
	
	void fire(Action<H> action);
	
	void fire(Func<H, Boolean> func);

	void fire(ThrowableAction<H> action);
	
	void fire(ThrowableFunc<H,Boolean> func);
}