package org.swetlokognatsk.earking_out.infrastructure.adapters.events;

import java.util.Arrays;

import org.swetlokognatsk.earking_out.core.domain.events.DomainEvent;
import org.swetlokognatsk.earking_out.core.domain.events.exercises.AudioPerfectPitchExercisePickedEvent;
import org.swetlokognatsk.earking_out.core.domain.events.pianokeyboard.PianoKeyPressedEvent;
import org.swetlokognatsk.earking_out.core.domain.events.session.HintRepeatingRequestedEvent;
import org.swetlokognatsk.earking_out.core.domain.events.session.NewPuzzleCreatedEvent;
import org.swetlokognatsk.earking_out.core.domain.events.session.SessionFinishedEvent;
import org.swetlokognatsk.earking_out.core.domain.events.session.SessionStartedEvent;
import org.swetlokognatsk.earking_out.core.domain.events.session.UserTriedToGuessPuzzleEvent;

public enum EventVersionsRegistry {
    HINT_REPEATING_REQUESTED_EVENT(HintRepeatingRequestedEvent.class, 1),
    NEW_PUZZLE_CREATED_EVENT(NewPuzzleCreatedEvent.class, 1),
    SESSION_FINISHED_EVENT(SessionFinishedEvent.class, 1),
    SESSION_STARTED_EVENT(SessionStartedEvent.class, 1),
    USER_TRIED_TO_GUESS_PUZZLE_EVENT(UserTriedToGuessPuzzleEvent.class, 1),
    PIANO_KEY_PRESSED_EVENT(PianoKeyPressedEvent.class, 1),
    AUDIO_PERFECT_PITCH_EXERCISE_PICKED_EVENT(AudioPerfectPitchExercisePickedEvent.class, 1);

    public final Class<? extends DomainEvent> eventClass;
    public final int version;

    private EventVersionsRegistry(final Class<? extends DomainEvent> eventClass, final int version) {
        this.eventClass = eventClass;
        this.version = version;
    }

    // TODO use Map<> instead? it has O(1) complexity
    public static int getVersion(final Class<? extends DomainEvent> eventClass) {
        return Arrays.stream(values())
            .filter(event -> event.eventClass.equals(eventClass))
            .findFirst()
            .orElseThrow()
            .version;
    }
}
