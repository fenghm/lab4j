/**
 * This file created at 2012-8-30.
 *
 * Copyright (c) 2002-2012 Bingosoft, Inc. All rights reserved.
 */
package fenghm.event4j;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fenghm.event4j.utils.Assert;

@SuppressWarnings({"unchecked","rawtypes"})
public class EventManagerImpl implements EventManager {
	
	private static final Logger log = LoggerFactory.getLogger(EventManagerImpl.class);
	
    private final Map<EventType, CopyOnWriteEventHandlers> eventTypes		        = new ConcurrentHashMap<EventType, CopyOnWriteEventHandlers>();
    private final EventTypeRegistration 				   eventTypeRegistration    = new EventTypeRegistrationImpl();
    private final EventHandlerRegistration 				   eventHandlerRegistration = new EventHandlerRegistrationImpl();

	public <E extends Event, H> void fireEvent(EventType<E, H> eventType, E event) {
		eventType.fire(event, eventTypes.get(eventType));
	}
	
	public EventTypeRegistration getEventTypeRegistration() {
		return eventTypeRegistration;
	}

	public EventHandlerRegistration getEventHandlerRegistration() {
		return eventHandlerRegistration;
	}

	private final class EventTypeRegistrationImpl implements EventTypeRegistration {
		public <E extends Event, H> void addEventType(EventType<E, H> eventType) {
			Assert.notNull(eventType);
	        Assert.isFalse(eventTypes.containsKey(eventType),"event type '{0}' aleady registered",eventType);
	        eventTypes.put(eventType, new CopyOnWriteEventHandlers<H>());
        }
	}
	
	private final class EventHandlerRegistrationImpl implements EventHandlerRegistration {
		public <E extends Event, H> void addEventHandler(EventType<E, H> eventType, H handler) {
			CopyOnWriteEventHandlers<H> handlers = eventTypes.get(eventType);
			Assert.notNull(handlers,"event type '{0}' did not registered,cannot add event handler",eventType);
			handlers.add(handler);
        }
	}
	
	private final class CopyOnWriteEventHandlers<H> extends CopyOnWriteArrayList<H> implements EventHandlers<H> {

		private static final long serialVersionUID = 5765578032668608685L;
		
		public void fire(Action<H> action) {
			for(H handler : this){
        		if(log.isTraceEnabled()){
        			log.trace("firing event to handler '{}'",handler.getClass().getName());	
        		}				
				action.execute(handler);
			}
        }

		public void fire(Func<H, Boolean> func) {
			for(H handler : this){
				if(func.apply(handler)){
					log.info("handler '{}' told me stop firing event to other handlers",handler.getClass().getName());
					break;
				}
			}
        }

		public void fire(ThrowableAction<H> action) {
	        for(H handler : this){
	        	try {
	        		if(log.isTraceEnabled()){
	        			log.trace("firing event to handler '{}'",handler.getClass().getName());	
	        		}
	                action.execute(handler);
                } catch (Throwable e) {
                	log.warn("error execute event handler '{}' : {}",new Object[]{handler.getClass().getName(),e.getMessage(),e});
                }
	        }
        }

		public void fire(ThrowableFunc<H,Boolean> func) {
	        for(H handler : this){
	        	try {
	        		if(log.isTraceEnabled()){
	        			log.trace("firing event to handler '{}'",handler.getClass().getName());	
	        		}
	        		
	        		if(func.apply(handler)){
	        			log.info("handler '{}' told me stop firing event to other handlers",handler.getClass().getName());
	        			break;
	        		}
                } catch (Throwable e) {
                	log.warn("error execute event handler '{}' : {}",new Object[]{handler.getClass().getName(),e.getMessage(),e});
                }
	        }
        }
	}
}