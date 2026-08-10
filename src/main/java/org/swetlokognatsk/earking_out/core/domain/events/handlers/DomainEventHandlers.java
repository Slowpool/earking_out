package org.swetlokognatsk.earking_out.core.domain.events.handlers;

import org.swetlokognatsk.earking_out.core.ports.events.EventBus;
import org.swetlokognatsk.earking_out.core.domain.events.pianokeyboard.PianoKeyPressedEvent;
import org.swetlokognatsk.earking_out.core.domain.events.session.HintRepeatingRequestedEvent;
import org.swetlokognatsk.earking_out.core.domain.events.session.NewPuzzleCreatedEvent;
import org.swetlokognatsk.earking_out.core.domain.events.session.SessionFinishedEvent;
import org.swetlokognatsk.earking_out.core.domain.events.session.SessionStartedEvent;
import org.swetlokognatsk.earking_out.core.domain.events.session.UserTriedToGuessPuzzleEvent;
import org.swetlokognatsk.earking_out.core.ports.di.DI;

public final class DomainEventHandlers {

    public static void registerDomainEventHandlers() {
        var eventBus = DI.get(EventBus.class);

        var soundPlayerOnPianoKeyPressedHandler = DI.get(SoundPlayerOnPianoKeyPressedHandler.class);
        eventBus.subscribe(PianoKeyPressedEvent.class, soundPlayerOnPianoKeyPressedHandler::handlePianoKeyPressedEvent);

        var hintDemonstratingOnHintRepeatingRequestedHandler = DI.get(HintDemonstratingOnHintRepeatingRequestedHandler.class);
        eventBus.subscribe(HintRepeatingRequestedEvent.class, hintDemonstratingOnHintRepeatingRequestedHandler::handleHintRepeatingRequestedEvent);

        var hintDemonstratingOnNewPuzzleCreatedHandler = DI.get(HintDemonstratingOnNewPuzzleCreatedHandler.class);
        eventBus.subscribe(NewPuzzleCreatedEvent.class, hintDemonstratingOnNewPuzzleCreatedHandler::handleNewPuzzleCreatedEvent);

        var sessionEventsLoggerHandler = DI.get(SessionEventsLoggerHandler.class);
        eventBus.subscribe(SessionStartedEvent.class, sessionEventsLoggerHandler::handleSessionStartedEvent);
        eventBus.subscribe(NewPuzzleCreatedEvent.class, sessionEventsLoggerHandler::handleNewPuzzleCreatedEvent);
        eventBus.subscribe(UserTriedToGuessPuzzleEvent.class, sessionEventsLoggerHandler::handleUserTriedToGuessPuzzleEvent);
        eventBus.subscribe(HintRepeatingRequestedEvent.class, sessionEventsLoggerHandler::handleHintRepeatingRequestedEvent);
        eventBus.subscribe(SessionFinishedEvent.class, sessionEventsLoggerHandler::handleSessionFinishedEvent);

        var puzzleConfigUpdatingOnPianoKeyPressedHandler = DI.get(PuzzleConfigUpdatingOnPianoKeyPressedHandler.class);
        eventBus.subscribe(PianoKeyPressedEvent.class, puzzleConfigUpdatingOnPianoKeyPressedHandler::handlePianoKeyPressedEvent);

        var sessionGuessingOnPianoKeyPressedHandler = DI.get(SessionGuessingOnPianoKeyPressedHandler.class);
        eventBus.subscribe(PianoKeyPressedEvent.class, sessionGuessingOnPianoKeyPressedHandler::handlePianoKeyPressedEvent);
    }
}
