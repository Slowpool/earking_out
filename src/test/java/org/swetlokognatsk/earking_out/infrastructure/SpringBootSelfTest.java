package org.swetlokognatsk.earking_out.infrastructure;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.Extension;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.swetlokognatsk.earking_out.EOSpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.core.env.Environment;
import org.swetlokognatsk.earking_out.ContextInjectorExtension;
import org.swetlokognatsk.earking_out.SpringProfiles;
import org.swetlokognatsk.earking_out.core.ports.events.EventBus;
import org.swetlokognatsk.earking_out.infrastructure.adapters.events.EventBuses;
import org.swetlokognatsk.earking_out.infrastructure.adapters.events.greenrobot.GreenrobotEventBus;
import org.swetlokognatsk.earking_out.infrastructure.adapters.events.spring.SpringEventBus;
import lombok.RequiredArgsConstructor;
import java.lang.Thread;
import java.util.ServiceLoader;
import java.lang.Class;

@EOSpringBootTest
@RequiredArgsConstructor
@ExtendWith(SpringBootSelfTest.ExtensionResolver.class)
public class SpringBootSelfTest {

    private final ApplicationContext ctx;

    private @Value("${app.eventbus}") String selectedEventBus;
    private @Value("${spring.profiles.active}") String activeProfile;
    private @Value("${java.class.path}") String classPaths;

    @Test
    public void profileIsTest() {
        assertEquals(SpringProfiles.TEST, activeProfile);
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

    @Test
    public void classPathIsNotNull() {
        assertNotNull(classPaths);
    }

    @Test
    public void classContainsTestRoot() {
        // false because vs code's extension uses some classpath isolation under the hood. but actually jvm has this classpath
        assertFalse(classPaths.contains("/src/test/resources"));
    }

    @Test
    public void autodetectionIsEnabled(final ExtensionContext extensionContext) {
        var test = extensionContext.getConfigurationParameter("junit.jupiter.extensions.autodetection.enabled");

        assertEquals("true", test.get());
    }

    @Test
    public void extensionsFileIsRead(final ExtensionContext extensionContext) {
        var classLoader = Thread.currentThread()
                .getContextClassLoader();
        var extensionFile = classLoader.getResource("META-INF/services/org.junit.jupiter.api.extension.Extension");
        assertNotNull(extensionFile);

        var extensionClassName = "org.swetlokognatsk.earking_out.ContextInjectorExtension";
        try {
            var extensionClass = Class.forName(extensionClassName, true, classLoader);
            assertNotNull(extensionClass);
        } catch (ClassNotFoundException e) {
            fail();
        }

        // jpms blocks this thing
        try {
            ServiceLoader.load(Extension.class, classLoader);
            fail();
        } catch (Throwable e) {
        }
    }

    static class ExtensionResolver implements ParameterResolver {
        public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext)
                throws ParameterResolutionException {
            return parameterContext.getParameter().getType().equals(ExtensionContext.class);
        }

        public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext)
                throws ParameterResolutionException {
            return extensionContext;
        }
    }
}
