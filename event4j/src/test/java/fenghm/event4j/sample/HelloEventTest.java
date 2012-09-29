package fenghm.event4j.sample;

import org.junit.Assert;
import org.junit.Test;

import fenghm.event4j.EventContainer;


public class HelloEventTest extends Assert{
	
	private EventContainer container = EventContainer.get();

	@Test
	public void test() {
		HelloEventProvider provider = new HelloEventProvider(container.getEventManager(),container.getEventTypeRegistration());
		HelloEventConsumer consumer = new HelloEventConsumer(container.getEventManager());
		
		provider.sayHello("hi, can you hear me?");
		
		HelloEvent event = consumer.getLastEvent();
		assertNotNull(event);
		assertEquals("hi, can you hear me?", event.getMessage());
	}

}
