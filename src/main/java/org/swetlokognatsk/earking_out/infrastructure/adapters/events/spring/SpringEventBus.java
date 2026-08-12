package org.swetlokognatsk.earking_out.infrastructure.adapters.events.spring;

import java.util.function.Consumer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.event.GenericApplicationListener;
import org.springframework.context.event.GenericApplicationListenerAdapter;
import org.springframework.core.ResolvableType;
import org.swetlokognatsk.earking_out.core.domain.events.DomainEvent;
import org.swetlokognatsk.earking_out.core.domain.events.handlers.DomainEventHandler;
import org.swetlokognatsk.earking_out.core.domain.events.pianokeyboard.PianoKeyPressedEvent;
import org.swetlokognatsk.earking_out.core.domain.events.session.HintRepeatingRequestedEvent;
import org.swetlokognatsk.earking_out.core.domain.events.session.NewPuzzleCreatedEvent;
import org.swetlokognatsk.earking_out.core.domain.events.session.SessionFinishedEvent;
import org.swetlokognatsk.earking_out.core.domain.events.session.SessionStartedEvent;
import org.swetlokognatsk.earking_out.core.domain.events.session.UserTriedToGuessPuzzleEvent;
import org.swetlokognatsk.earking_out.core.ports.di.DI;
import org.swetlokognatsk.earking_out.core.ports.events.EventBus;

public final class SpringEventBus implements EventBus {

    private ConfigurableApplicationContext ctx;

    public SpringEventBus(final ConfigurableApplicationContext ctx) {
        this.ctx = ctx;
    }

    public <DE extends DomainEvent> void subscribe(final Class<DE> eventClass, final DomainEventHandler<DE> domainEventHandler) {
        ApplicationListener<?> listener;
        // TODO is it possible to just create callback and than cast it instead of creating it for each class
        if (eventClass == NewPuzzleCreatedEvent.class) {
            listener = (SpringNewPuzzleCreatedEvent e) -> domainEventHandler.handle((DE) (e.domainEvent));
            else if (eventClass == PianoKeyPressedEvent.class) {

        } else {
            // TODO return
            return;
            // throw new RuntimeException("unknown event class: " + eventClass.getName());
        }
        ctx.addApplicationListener(listener);
    }

    // private Object wrapDomainEventHandler(final DomainEventHandler<?> domainEventHandler) {
    //     return switch (domainEventHandler) {

    //     };
    //     // TODO awkwaaaaaard. pretty sure dynamic handlers can be created. via anonymous classes? via so-called `event multicaster`?
    //     var clazzName = clazz.getName();
    //     if (clazzName.equals(PianoKeyPressedEvent.class.getName())) {
    //         var handler = DI.get(SpringPianoKeyPressedHandler.class);
    //         handler.appendCallback((Consumer<PianoKeyPressedEvent>) action);

    //     } else if (clazzName.equals(SessionStartedEvent.class.getName())) {
    //         var handler = DI.get(SpringSessionStartedHandler.class);
    //         handler.appendCallback((Consumer<SessionStartedEvent>) action);
    //     } else if (clazzName.equals(NewPuzzleCreatedEvent.class.getName())) {
    //         var handler = DI.get(SpringNewPuzzleCreatedHandler.class);
    //         handler.appendCallback((Consumer<NewPuzzleCreatedEvent>) action);
    //     } else if (clazzName.equals(UserTriedToGuessPuzzleEvent.class.getName())) {
    //         var handler = DI.get(SpringUserTriedToGuessPuzzleHandler.class);
    //         handler.appendCallback((Consumer<UserTriedToGuessPuzzleEvent>) action);
    //     } else if (clazzName.equals(HintRepeatingRequestedEvent.class.getName())) {
    //         var handler = DI.get(SpringHintRepeatingRequestedHandler.class);
    //         handler.appendCallback((Consumer<HintRepeatingRequestedEvent>) action);
    //     } else if (clazzName.equals(SessionFinishedEvent.class.getName())) {
    //         var handler = DI.get(SpringSessionFinishedHandler.class);
    //         handler.appendCallback((Consumer<SessionFinishedEvent>) action);

    //     } else {
    //         throw new RuntimeException("unknown class to subscribe: " + clazz.getName());
    //     }
    // }

}
