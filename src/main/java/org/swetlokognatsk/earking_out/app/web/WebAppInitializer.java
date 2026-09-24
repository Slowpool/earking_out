package org.swetlokognatsk.earking_out.app.web;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.swetlokognatsk.earking_out.Build;
import org.swetlokognatsk.earking_out.SpringApp;
import org.swetlokognatsk.earking_out.core.domain.events.handlers.DomainEventHandlers;
import org.swetlokognatsk.earking_out.core.ports.di.DI;

@Component
public class WebAppInitializer implements CommandLineRunner {

    private final ApplicationContext ctx;

    public WebAppInitializer(final ApplicationContext ctx) {
        this.ctx = ctx;
    }

    public void run(final String... args) throws Exception {
        // TODO pretty sure some more elegant solution exists via @Component @ConditionalOn or kinda
        if (SpringApp.build == Build.WEB) {
            initDI(ctx);
            DomainEventHandlers.registerDomainEventHandlers();
        }
    }

    public static void initDI(final ApplicationContext context) {
        DI.setContext(context);
    }
}
