package org.swetlokognatsk.earking_out.infrastructure;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.core.env.Environment;
import org.swetlokognatsk.earking_out.SpringProfiles;
import org.swetlokognatsk.earking_out.core.ports.events.EventBus;
import org.swetlokognatsk.earking_out.infrastructure.adapters.events.EventBuses;
import org.swetlokognatsk.earking_out.infrastructure.adapters.events.greenrobot.GreenrobotEventBus;
import org.swetlokognatsk.earking_out.infrastructure.adapters.events.spring.SpringEventBus;
import lombok.RequiredArgsConstructor;

@SpringBootTest
@RequiredArgsConstructor
public class SpringBootSelfTest {

    private final ApplicationContext ctx;
    private @Value("${app.eventbus}") String selectedEventBus;

    @Test
    public void profileIsTest() {
        var profile = System.getProperty("spring.profiles.active");

        assertEquals(SpringProfiles.TEST, profile);
    }

    @Test
    public void eventBusExists() {
        EventBus eventBus = null;
        switch (selectedEventBus) {
        case EventBuses.GREENROBOT:
            eventBus = ((GenericApplicationContext) ctx).getBean(GreenrobotEventBus.class);
            break;
        case EventBuses.SPRING:
            eventBus = ((GenericApplicationContext) ctx).getBean(SpringEventBus.class);
            break;
        default:
            fail();
        }

        assertNotNull(eventBus);
    }
}
