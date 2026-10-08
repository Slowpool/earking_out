package org.swetlokognatsk.earking_out.core.ports.di;

import java.util.function.Function;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.swetlokognatsk.earking_out.infrastructure.adapters.di.SpringIoCContainer;

public final class DI implements ApplicationContextAware {
    public static IoCContainer iocContainer;

    private static IoCContainer getIocContainer() {
        if (iocContainer == null) {
            initIoCContainer();
        }
        return iocContainer;
    }

    private static void initIoCContainer() {
        iocContainer = new SpringIoCContainer();
    }

    // never called actually. it's mandatory for ApplicationContextAware interface. that interface is implemented by DI to explicitly show that it knows about context. small partcile of coupling to spring.
    public void setApplicationContext(final ApplicationContext context) throws BeansException {
    }

    public static void setContext(final ApplicationContext context, final boolean replace) throws BeansException {
        if (iocContainer != null) {
            if (replace) {
                iocContainer = null;
            } else {
                throw new RuntimeException("ioc container is already initialized");
            }
        }
        ((SpringIoCContainer) getIocContainer())
                .setContext(context);
    }

    public static void setContext(final ApplicationContext context) throws BeansException {
        setContext(context, false);
    }

    private DI() {
    }

    public static <T> T get(Class<T> someClass, Object... args) {
        return getIocContainer()
                .get(someClass, args);
    }

    public static void refreshDependencies() {
        // TODO how to do it in spring? how it is supposed to be done in spring?
    }

    public static <T> void register(final Class<T> someClass, final Function<Object[], T> depFactory) throws IllegalStateException {
        iocContainer.register(someClass, depFactory);
    }
}
