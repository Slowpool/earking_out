package org.swetlokognatsk.earking_out.app.web;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.swetlokognatsk.earking_out.core.domain.events.handlers.DomainEventHandlers;
import org.swetlokognatsk.earking_out.core.ports.di.DI;

@Component
public class WebAppInitializer implements CommandLineRunner {

    private final ApplicationContext ctx;

    public WebAppInitializer(final ApplicationContext ctx) {
        this.ctx = ctx;
    }

    public void run(final String... args) throws Exception {
        initDI(ctx);
        DomainEventHandlers.registerDomainEventHandlers();
    }

    public static void initDI(final ApplicationContext context) {
        DI.setContext(context);
    }
}
