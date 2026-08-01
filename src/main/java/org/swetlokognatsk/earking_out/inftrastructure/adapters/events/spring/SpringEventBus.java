package org.swetlokognatsk.earking_out.inftrastructure.adapters.events.spring;

import java.util.function.Consumer;
import org.swetlokognatsk.earking_out.core.domain.events.DomainEvent;
import org.swetlokognatsk.earking_out.core.domain.events.pianokeyboard.PianoKeyPressedEvent;
import org.swetlokognatsk.earking_out.core.domain.events.puzzles.HintRepeatingRequestedEvent;
import org.swetlokognatsk.earking_out.core.domain.events.puzzles.NewPuzzleCreatedEvent;
import org.swetlokognatsk.earking_out.core.ports.di.DI;
import org.swetlokognatsk.earking_out.core.ports.events.EventBus;

public final class SpringEventBus implements EventBus {

    public <DE extends DomainEvent> void subscribe(final Class<DE> clazz, final Consumer<DE> action) {
        // TODO awkwaaaaaard
        var clazzName = clazz.getName();
        if (clazzName.equals(PianoKeyPressedEvent.class.getName())) {
            var handler = DI.get(SpringPianoKeyPressedHandler.class);
            handler.setCallback((Consumer<PianoKeyPressedEvent>)action);
        } else if (clazzName.equals(HintRepeatingRequestedEvent.class.getName())) {
            var handler = DI.get(SpringHintRepeatingRequestedHandler.class);
            handler.setCallback((Consumer<HintRepeatingRequestedEvent>)action);
        } else if (clazzName.equals(NewPuzzleCreatedEvent.class.getName())) {
            var handler = DI.get(SpringNewPuzzleCreatedHandler.class);
            handler.setCallback((Consumer<NewPuzzleCreatedEvent>)action);
        } else {
            throw new RuntimeException("unknown class to subscribe: " + clazz.getName());
        }
    }

}
