package org.swetlokognatsk.earking_out.infrastructure.adapters.events.spring;

import java.util.function.Consumer;
import org.swetlokognatsk.earking_out.core.domain.events.DomainEvent;
import org.swetlokognatsk.earking_out.core.domain.events.pianokeyboard.PianoKeyPressedEvent;
import org.swetlokognatsk.earking_out.core.domain.events.session.HintRepeatingRequestedEvent;
import org.swetlokognatsk.earking_out.core.domain.events.session.NewPuzzleCreatedEvent;
import org.swetlokognatsk.earking_out.core.domain.events.session.SessionFinishedEvent;
import org.swetlokognatsk.earking_out.core.domain.events.session.SessionStartedEvent;
import org.swetlokognatsk.earking_out.core.domain.events.session.UserTriedToGuessPuzzleEvent;
import org.swetlokognatsk.earking_out.core.ports.di.DI;
import org.swetlokognatsk.earking_out.core.ports.events.EventBus;

public final class SpringEventBus implements EventBus {

    public <DE extends DomainEvent> void subscribe(final Class<DE> clazz, final Consumer<DE> action) {
        // TODO awkwaaaaaard. pretty sure dynamic handlers can be created. via anonymous classes? via so-called `event multicaster`?
        var clazzName = clazz.getName();
        if (clazzName.equals(PianoKeyPressedEvent.class.getName())) {
            var handler = DI.get(SpringPianoKeyPressedHandler.class);
            handler.appendCallback((Consumer<PianoKeyPressedEvent>) action);

        } else if (clazzName.equals(SessionStartedEvent.class.getName())) {
            var handler = DI.get(SpringSessionStartedHandler.class);
            handler.appendCallback((Consumer<SessionStartedEvent>) action);
        } else if (clazzName.equals(NewPuzzleCreatedEvent.class.getName())) {
            var handler = DI.get(SpringNewPuzzleCreatedHandler.class);
            handler.appendCallback((Consumer<NewPuzzleCreatedEvent>) action);
        } else if (clazzName.equals(UserTriedToGuessPuzzleEvent.class.getName())) {
            var handler = DI.get(SpringUserTriedToGuessPuzzleHandler.class);
            handler.appendCallback((Consumer<UserTriedToGuessPuzzleEvent>) action);
        } else if (clazzName.equals(HintRepeatingRequestedEvent.class.getName())) {
            var handler = DI.get(SpringHintRepeatingRequestedHandler.class);
            handler.appendCallback((Consumer<HintRepeatingRequestedEvent>) action);
        } else if (clazzName.equals(SessionFinishedEvent.class.getName())) {
            var handler = DI.get(SpringSessionFinishedHandler.class);
            handler.appendCallback((Consumer<SessionFinishedEvent>) action);

        } else {
            throw new RuntimeException("unknown class to subscribe: " + clazz.getName());
        }
    }

}
