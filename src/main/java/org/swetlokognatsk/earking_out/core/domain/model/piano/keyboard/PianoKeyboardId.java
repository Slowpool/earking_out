package org.swetlokognatsk.earking_out.core.domain.model.piano.keyboard;

import org.swetlokognatsk.earking_out.core.domain.model.exercises.Exercise;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.AudioPerfectPitchExercise;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.VisualPerfectPitchExercise;

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
    ROOT_NOTE_PICKER(new AudioPerfectPitchExercise()), PERFECT_PITCH_NOTES_PICKER(new AudioPerfectPitchExercise()), PERFECT_PITCH_NOTES_GUESSING(new AudioPerfectPitchExercise());

    public final Exercise exercise;

    private PianoKeyboardId(final Exercise exercise) {
        this.exercise = exercise;
    }

    public static PianoKeyboardId[] getPianoKeyboardIds(final Exercise exercise) {
        // TODO stream/loop refactoring
        var pianoKeyboardIds = switch (exercise) {
        case AudioPerfectPitchExercise e -> new PianoKeyboardId[] { PianoKeyboardId.PERFECT_PITCH_NOTES_PICKER, PianoKeyboardId.ROOT_NOTE_PICKER };
        case VisualPerfectPitchExercise e -> new PianoKeyboardId[] { PianoKeyboardId.PERFECT_PITCH_NOTES_PICKER, PianoKeyboardId.ROOT_NOTE_PICKER };
        default -> throw new IllegalArgumentException("unknown exercise: " + exercise);
        };
        return pianoKeyboardIds;
    }
}
