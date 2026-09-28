package org.swetlokognatsk.earking_out;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.swetlokognatsk.earking_out.app.desktop.EarkingOutDesktopApplication;
import org.swetlokognatsk.earking_out.core.domain.events.handlers.DomainEventHandlers;
import org.swetlokognatsk.earking_out.core.ports.di.DI;
import javafx.application.Application;

public final class DesktopEntryPoint {

    public static void main(String[] args) {
        var context = runSpringApp(args);
        initDI(context);
        DomainEventHandlers.registerDomainEventHandlers();
        Application.launch(EarkingOutDesktopApplication.class, args);
    }

    public static void initDI(final ApplicationContext context) {
        DI.setContext(context);
    }

    public static ApplicationContext runSpringApp(String[] args) {
        SpringApp.build = Build.DESKTOP;
        var springApplication = new SpringApplication(SpringApp.class);
        springApplication.setBannerMode(Banner.Mode.OFF);
        return springApplication.run(args);
    }

}
