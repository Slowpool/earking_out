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

        // TODO refactoring (map<Class,Class> + foreach?)
        var soundPlayerOnPianoKeyPressedHandler = DI.get(SoundPlayerOnPianoKeyPressedHandler.class);
        eventBus.subscribe(PianoKeyPressedEvent.class, soundPlayerOnPianoKeyPressedHandler);

        var hintDemonstratingOnHintRepeatingRequestedHandler = DI.get(HintDemonstratingOnHintRepeatingRequestedHandler.class);
        eventBus.subscribe(HintRepeatingRequestedEvent.class, hintDemonstratingOnHintRepeatingRequestedHandler);

        var hintDemonstratingOnNewPuzzleCreatedHandler = DI.get(HintDemonstratingOnNewPuzzleCreatedHandler.class);
        eventBus.subscribe(NewPuzzleCreatedEvent.class, hintDemonstratingOnNewPuzzleCreatedHandler);

        var logEventOnSessionStartedHandler = DI.get(LogEventOnSessionStartedHandler.class);
        eventBus.subscribe(SessionStartedEvent.class, logEventOnSessionStartedHandler);

        var logEventOnNewPuzzleCreatedHandler = DI.get(LogEventOnNewPuzzleCreatedHandler.class);
        eventBus.subscribe(NewPuzzleCreatedEvent.class, logEventOnNewPuzzleCreatedHandler);

        var logEventOnUserTriedToGuessPuzzleHandler = DI.get(LogEventOnUserTriedToGuessPuzzleHandler.class);
        eventBus.subscribe(UserTriedToGuessPuzzleEvent.class, logEventOnUserTriedToGuessPuzzleHandler);

        var logEventOnHintRepeatingRequestedHandler = DI.get(LogEventOnHintRepeatingRequestedHandler.class);
        eventBus.subscribe(HintRepeatingRequestedEvent.class, logEventOnHintRepeatingRequestedHandler);

        var logEventOnSessionFinishedHandler = DI.get(LogEventOnSessionFinishedHandler.class);
        eventBus.subscribe(SessionFinishedEvent.class, logEventOnSessionFinishedHandler);

        var puzzleConfigUpdatingOnPianoKeyPressedHandler = DI.get(PuzzleConfigUpdatingOnPianoKeyPressedHandler.class);
        eventBus.subscribe(PianoKeyPressedEvent.class, puzzleConfigUpdatingOnPianoKeyPressedHandler);

        // var sessionGuessingOnPianoKeyPressedHandler = DI.get(SessionGuessingOnPianoKeyPressedHandler.class);
        // eventBus.subscribe(PianoKeyPressedEvent.class, sessionGuessingOnPianoKeyPressedHandler::handlePianoKeyPressedEvent);

        var sessionPianoKeyboardUpdatingOnSessionStartedHandler = DI.get(SessionPianoKeyboardUpdatingOnSessionStartedHandler.class);
        eventBus.subscribe(SessionStartedEvent.class, sessionPianoKeyboardUpdatingOnSessionStartedHandler);

        var sessionGuessingOnPianoKeyPressedHandler = DI.get(SessionGuessingOnPianoKeyPressedHandler.class);
        eventBus.subscribe(PianoKeyPressedEvent.class, sessionGuessingOnPianoKeyPressedHandler);
    }
}
