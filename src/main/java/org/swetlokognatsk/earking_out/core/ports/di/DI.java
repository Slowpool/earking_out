package org.swetlokognatsk.earking_out.core.ports.di;

import org.swetlokognatsk.earking_out.inftrastructure.adapters.di.HandmadeIoCContainer;
import org.swetlokognatsk.earking_out.inftrastructure.adapters.di.SpringIoCContainer;

public final class DI {
    public static final String TEST_MODE = "test_mode";
    public static final String APP_MODE = "app_mode";
    public static final String mode = TEST_MODE;

    public static final IoCContainer iocContainer;

    static {
        iocContainer = initIoCContainer();
    }

    private static IoCContainer initIoCContainer() {
        IoCContainer iocContainer;
        if (inTestMode()) {
            iocContainer = new HandmadeIoCContainer();
        } else if (inProdMode()) {
            iocContainer = new SpringIoCContainer();
        } else {
            throw new RuntimeException("unknown mode: " + mode);
        }
        return iocContainer;
    }

    public static boolean inTestMode() {
        return mode.equals(DI.TEST_MODE);
    }

    public static boolean inProdMode() {
        return mode.equals(DI.TEST_MODE);
    }

    private DI() {
    }

    public static <T> T get(Class<T> someClass, Object... args) {
        return iocContainer.get(someClass, args);
    }

    public static void refreshDependencies() {
        switch (mode) {
        case APP_MODE:
            // either delete this exception either do something with spring ioc container implementation
            throw new RuntimeException("refreshing dependencies on prod must never be called");
        case TEST_MODE:
            iocContainer.refreshDependencies();
            break;
        default:
            throw new RuntimeException("unknown environment: " + mode);
        }
    }
}
