package org.swetlokognatsk.earking_out.core.domain.model.piano.keyboard;

import java.util.Arrays;
import java.util.Objects;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.Exercise;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.AudioPerfectPitchExercise;

// TODO use DTOs wherever it's possible instead of aggregates
/**
 * This specific enum is used for each PianoKeyboard id because: app has finite
 * number of piano keyboards, each of which is code-dependent - e.g. the same
 * piano keyboard is always used for root note picking in audio perfect pitch
 * exercise. so, it's state is singleton-like. the same thing with other piano
 * keyboards. PianoKeyboard has a state and it's definitely not a value object.
 * according to domain rules, user cannot create new PianoKeyboards, so, their
 * ids are just hardcoded here.
 */
public enum PianoKeyboardId {
    AUDIO_PERFECT_PITCH_ROOT_NOTE_PICKER(new AudioPerfectPitchExercise(), PianoKeyboardContext.PUZZLE_CONFIG), AUDIO_PERFECT_PITCH_NOTES_PICKER(new AudioPerfectPitchExercise(), PianoKeyboardContext.PUZZLE_CONFIG), AUDIO_PERFECT_PITCH_NOTES_GUESSING(new AudioPerfectPitchExercise(), PianoKeyboardContext.SESSION);

    public final Exercise exercise;
    public final PianoKeyboardContext context;

    private PianoKeyboardId(final Exercise exercise, final PianoKeyboardContext context) {
        this.exercise = Objects.requireNonNull(exercise);
        this.context = Objects.requireNonNull(context);
    }

    public static PianoKeyboardId[] getPianoKeyboardIds(final Exercise exercise) {
        var allIds = values();
        var stream = Arrays.stream(allIds);
        var filteredIds = stream.filter((PianoKeyboardId pianoKeyboardId) -> exercise.equals(pianoKeyboardId.exercise));
        var pianoKeyboardIds = filteredIds.toArray(PianoKeyboardId[]::new);
        return pianoKeyboardIds;
    }
}
