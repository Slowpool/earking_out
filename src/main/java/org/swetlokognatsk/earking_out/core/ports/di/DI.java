package org.swetlokognatsk.earking_out.core.ports.di;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.swetlokognatsk.earking_out.inftrastructure.adapters.di.HandmadeIoCContainer;
import org.swetlokognatsk.earking_out.inftrastructure.adapters.di.SpringIoCContainer;

public final class DI implements ApplicationContextAware {
    private static final String TEST_MODE = "test_mode";
    private static final String APP_MODE = "app_mode";
    private static String mode = TEST_MODE;

    public static IoCContainer iocContainer;

    private static void initIoCContainer() {
        if (inTestMode()) {
            iocContainer = new HandmadeIoCContainer();
        } else if (inAppMode()) {
            iocContainer = new SpringIoCContainer();
        } else {
            throw new RuntimeException("unknown mode: " + mode);
        }
    }

    /**
     * it is supposed to be called after setting the `mode` property. i.e. right now
     * there're two ways to init DI container: 1. if you call DI.get() in clean
     * client code without any code before, `HandmadeIoCContainer` will be
     * initialized via this method, because `iocContainer` class variable is null
     * and default mode is `TEST_MODE`. 2. otherwise, if you called
     * `DI.setContext(springContext)` before any another `DI.<method>()` calls, the
     * most of all DI is in full app running mode, so the mode will be set to
     * `PROD_MODE` and then iocContainer will be initialized with
     * `SpringIoCContainer` object
     */
    private static IoCContainer getIocContainer() {
        if (iocContainer == null) {
            initIoCContainer();
        }
        return iocContainer;
    }

    public static boolean inTestMode() {
        return mode.equals(DI.TEST_MODE);
    }

    public static boolean inAppMode() {
        return mode.equals(DI.APP_MODE);
    }

    // never called actually. it's mandatory for ApplicationContextAware interface. that interface is implemented by DI to explicitly show that it knows about context. small partcile of coupling to spring.
    public void setApplicationContext(final ApplicationContext context) throws BeansException {
        DI.setContext(context);
    }

    public static void setContext(final ApplicationContext context) throws BeansException {
        if (iocContainer != null) {
            throw new RuntimeException("ioc container is already initialized");
        }
        // context is supposed to be set only by app (port)
        mode = DI.APP_MODE;
        ((SpringIoCContainer) getIocContainer()).setContext(context);
    }

    private DI() {
    }

    public static <T> T get(Class<T> someClass, Object... args) {
        return getIocContainer().get(someClass, args);
    }

    public static void refreshDependencies() {
        switch (mode) {
        case APP_MODE:
            // either delete this exception either do something with spring ioc container implementation
            throw new RuntimeException("refreshing dependencies on prod must never be called");
        case TEST_MODE:
            getIocContainer().refreshDependencies();
            break;
        default:
            throw new RuntimeException("unknown environment: " + mode);
        }
    }
}
