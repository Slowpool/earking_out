package org.swetlokognatsk.earking_out.core.domain.events.handlers;

import org.swetlokognatsk.earking_out.core.ports.events.EventBus;
import org.swetlokognatsk.earking_out.core.domain.events.pianokeyboard.PianoKeyPressedEvent;
import org.swetlokognatsk.earking_out.core.domain.events.puzzles.HintRepeatingRequestedEvent;
import org.swetlokognatsk.earking_out.core.domain.events.puzzles.NewPuzzleCreatedEvent;
import org.swetlokognatsk.earking_out.core.ports.di.DI;

public final class DomainEventHandlers {

    public static void registerDomainEventHandlers() {
        var eventBus = DI.get(EventBus.class);

        // TODO rename handlers to what they do, not what they handle
        var pianoKeyPressedHandler = DI.get(PianoKeyPressedHandler.class);
        eventBus.subscribe(PianoKeyPressedEvent.class, pianoKeyPressedHandler::handlePianoKeyPressedEvent);

        var hintRepeatingRequestedHandler = DI.get(HintRepeatingRequestedHandler.class);
        eventBus.subscribe(HintRepeatingRequestedEvent.class, hintRepeatingRequestedHandler::handleHintRepeatingRequestedEvent);

        var newPuzzleCreatedHandler = DI.get(NewPuzzleCreatedHandler.class);
        eventBus.subscribe(NewPuzzleCreatedEvent.class, newPuzzleCreatedHandler::handleNewPuzzleCreatedEvent);


    }
}
