package org.swetlokognatsk.earking_out.infrastructure;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericApplicationContext;
import org.swetlokognatsk.earking_out.SpringProfiles;
import org.swetlokognatsk.earking_out.infrastructure.adapters.events.greenrobot.GreenrobotEventBus;
import lombok.AllArgsConstructor;

@SpringBootTest
@AllArgsConstructor
public class SpringBootSelfTest {

    private final ApplicationContext ctx;

    @Test
    public void profileIsTest() {
        var profile = System.getProperty("spring.profiles.active");

        assertEquals(SpringProfiles.TEST, profile);
    }

    @Test
    public void someTestDependenciesExist() {
        var greenRobotEventBus = ((GenericApplicationContext) ctx).getBean(GreenrobotEventBus.class);

        assertNotNull(greenRobotEventBus);
    }
}
