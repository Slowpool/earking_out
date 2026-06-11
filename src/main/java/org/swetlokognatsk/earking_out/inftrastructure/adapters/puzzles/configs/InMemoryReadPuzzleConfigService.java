package org.swetlokognatsk.earking_out.inftrastructure.adapters.puzzles.configs;

import org.swetlokognatsk.earking_out.core.domain.model.exercises.Exercise;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.AudioPerfectPitchExercise;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.VisualPerfectPitchExercise;
import org.swetlokognatsk.earking_out.core.domain.model.music.NoteNames;
import org.swetlokognatsk.earking_out.core.domain.model.music.NoteWithAccidental;
import org.swetlokognatsk.earking_out.core.domain.model.music.Octaves;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.PuzzleConfig;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.perfectpitch.AudioPerfectPitchConfig;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.perfectpitch.PerfectPitchInputMode;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.perfectpitch.VisualPerfectPitchConfig;
import org.swetlokognatsk.earking_out.core.ports.config.ReadPuzzleConfigService;

public final class InMemoryReadPuzzleConfigService implements ReadPuzzleConfigService {
    // TODO use Map<Exercise, PuzzleConfig>
    static AudioPerfectPitchConfig appc;
    static VisualPerfectPitchConfig vppc;

    static {
        var notes = new byte[] { new NoteWithAccidental(NoteNames.D, null, Octaves.FIRST).normalize() };
        appc = new AudioPerfectPitchConfig(100, false, notes, Byte.valueOf((byte) 25), PerfectPitchInputMode.NOTES_AS_CHARACTERS);
        vppc = new VisualPerfectPitchConfig(0, false, new byte[0], null, PerfectPitchInputMode.KEYBOARD_AS_PIANO);
    }

    public <E extends Exercise, PC extends PuzzleConfig<E>> PC fetch(Class<E> exerciseClass, Exercise exercise) {
        if (!exerciseClass.equals(exercise.getClass())) {
            throw new IllegalArgumentException("exercise class does not correspond to exerciseClass");
        }

        // making the shallow copy
        var puzzleConfig = switch (exercise) {
        case VisualPerfectPitchExercise e -> new VisualPerfectPitchConfig(vppc.targetNumberOfPuzzles, vppc.statsRecording, vppc.normalizedNotesForPuzzle, vppc.normalizedRootNote, vppc.inputMode);
        case AudioPerfectPitchExercise e -> new AudioPerfectPitchConfig(appc.targetNumberOfPuzzles, appc.statsRecording, appc.normalizedNotesForPuzzle, appc.normalizedRootNote, appc.inputMode);
        default -> throw new RuntimeException("unknown exercise on config fetching: " + exercise);
        };

        return (PC) puzzleConfig;
    }
}
