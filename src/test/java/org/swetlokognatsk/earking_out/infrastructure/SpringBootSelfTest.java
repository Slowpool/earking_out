package org.swetlokognatsk.earking_out.infrastructure;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequiredArgsConstructor(onConstructor = @__({ @Autowired }))
public class SpringBootSelfTest {

    private final ApplicationContext ctx;
    private final Environment env;

    @Test
    public void profileIsTest() {
        var profile = System.getProperty("spring.profiles.active");

        assertEquals(SpringProfiles.TEST, profile);
    }

    @Test
    public void eventBusExist() {
        EventBus eventBus = null;
        switch (env.getProperty("app.eventbus")) {
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
