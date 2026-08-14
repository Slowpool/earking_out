package org.swetlokognatsk.earking_out.core.domain.events.handlers;

import org.swetlokognatsk.earking_out.core.ports.events.EventBus;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Handler;
import static java.util.Map.entry;
import org.swetlokognatsk.earking_out.core.domain.events.DomainEvent;
import org.swetlokognatsk.earking_out.core.domain.events.exercises.AudioPerfectPitchExercisePickedEvent;
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

        eventBus.subscribe(PianoKeyPressedEvent.class, DI.get(SoundPlayerOnPianoKeyPressedHandler.class));

        eventBus.subscribe(HintRepeatingRequestedEvent.class, DI.get(HintDemonstratingOnHintRepeatingRequestedHandler.class));

        eventBus.subscribe(NewPuzzleCreatedEvent.class, DI.get(HintDemonstratingOnNewPuzzleCreatedHandler.class));

        eventBus.subscribe(SessionStartedEvent.class, DI.get(LogEventOnSessionStartedHandler.class));

        eventBus.subscribe(NewPuzzleCreatedEvent.class, DI.get(LogEventOnNewPuzzleCreatedHandler.class));

        eventBus.subscribe(UserTriedToGuessPuzzleEvent.class, DI.get(LogEventOnUserTriedToGuessPuzzleHandler.class));

        eventBus.subscribe(HintRepeatingRequestedEvent.class, DI.get(LogEventOnHintRepeatingRequestedHandler.class));

        eventBus.subscribe(SessionFinishedEvent.class, DI.get(LogEventOnSessionFinishedHandler.class));

        eventBus.subscribe(PianoKeyPressedEvent.class, DI.get(PuzzleConfigUpdatingOnPianoKeyPressedHandler.class));

        eventBus.subscribe(SessionStartedEvent.class, DI.get(SessionPianoKeyboardUpdatingOnSessionStartedHandler.class));

        eventBus.subscribe(PianoKeyPressedEvent.class, DI.get(AudioPerfectPitchGuessingOnPianoKeyPressedHandler.class));

        eventBus.subscribe(AudioPerfectPitchExercisePickedEvent.class, DI.get(ActualizePianoKeyboardsOnAudioPerfectPitchExercisePickedHandler.class));
    }
}
