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
	
    private final Map<EventType, CopyOnWriteEventHandlers>      eventTypes		         = new ConcurrentHashMap<EventType, CopyOnWriteEventHandlers>();
    private final Map<EventTypeGroup, CopyOnWriteEventHandlers> eventGroups              = new ConcurrentHashMap<EventTypeGroup, EventManagerImpl.CopyOnWriteEventHandlers>();

    private final EventTypeRegistration 				        eventTypeRegistration    = new EventTypeRegistrationImpl();
    private final EventHandlerRegistration 				        eventHandlerRegistration = new EventHandlerRegistrationImpl();

	public <E extends Event,TH,GH> void fireEvent(EventType<E, TH,GH> eventType, E event) {
		eventType.fire(event, eventTypes.get(eventType),eventGroups.get(eventType.group()));
	}
	
	public EventTypeRegistration getEventTypeRegistration() {
		return eventTypeRegistration;
	}

	public EventHandlerRegistration getEventHandlerRegistration() {
		return eventHandlerRegistration;
	}

	private final class EventTypeRegistrationImpl implements EventTypeRegistration {
		public <E extends Event,TH,GH> void addEventType(EventType<E,TH,GH> eventType) {
			Assert.notNull(eventType);
			Assert.notNull(eventType.group());
	        Assert.isFalse(eventTypes.containsKey(eventType),"event type '{0}' aleady registered",eventType);
	        eventTypes.put(eventType, new CopyOnWriteEventHandlers<TH>());
	        
	        CopyOnWriteEventHandlers<GH> groupHandlers = eventGroups.get(eventType.group());
	        if(null == groupHandlers){
	        	eventGroups.put(eventType.group(), new CopyOnWriteEventHandlers());
	        }
        }
	}
	
	private final class EventHandlerRegistrationImpl implements EventHandlerRegistration {
		public <E extends Event,TH,GH> void addEventHandler(EventType<E,TH,GH> eventType,TH handler) {
			CopyOnWriteEventHandlers<TH> handlers = eventTypes.get(eventType);
			Assert.notNull(handlers,"event type '{0}' did not registered,cannot add event handler",eventType);
			handlers.add(handler);
        }

		public <H> void addGroupHandler(EventTypeGroup<H> group, H handler) {
			CopyOnWriteEventHandlers<H> handlers = eventGroups.get(group);
			Assert.notNull(handlers,"event type group '{0}' did not registered,cannot add event handler",eventGroups);
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