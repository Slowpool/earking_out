package org.swetlokognatsk.earking_out.inftrastructure.adapters.puzzles.configs;

import org.swetlokognatsk.earking_out.core.domain.model.exercises.Exercise;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.AudioPerfectPitchExercise;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.VisualPerfectPitchExercise;
import org.swetlokognatsk.earking_out.core.domain.model.music.NoteNames;
import org.swetlokognatsk.earking_out.core.domain.model.music.NoteWithAccidental;
import org.swetlokognatsk.earking_out.core.domain.model.music.Octaves;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.PuzzleConfigAggregate;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.factories.AbstractPuzzleConfigAggregatesFactory;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.perfectpitch.AudioPerfectPitchConfigAggregate;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.perfectpitch.PerfectPitchInputMode;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.perfectpitch.VisualPerfectPitchConfigAggregate;
import org.swetlokognatsk.earking_out.core.ports.config.PuzzleConfigRepository;

public class InMemoryPuzzleConfigRepository implements PuzzleConfigRepository {
    // TODO use Map<Exercise, PuzzleConfig>
    static AudioPerfectPitchConfigAggregate appc;
    static VisualPerfectPitchConfigAggregate vppc;

    static {
        var notes = new byte[] { new NoteWithAccidental(NoteNames.D, null, Octaves.FIRST).normalize() };
        appc = new AudioPerfectPitchConfigAggregate(100, false, notes, Byte.valueOf((byte) 25), PerfectPitchInputMode.NOTES_AS_CHARACTERS);
        vppc = new VisualPerfectPitchConfigAggregate(0, false, new byte[0], null, PerfectPitchInputMode.KEYBOARD_AS_PIANO);
    }

    public <E extends Exercise, PC extends PuzzleConfigAggregate<E>> PC fetch(final Class<E> exerciseClass, final Exercise exercise) {
        if (!exerciseClass.equals(exercise.getClass())) {
            throw new IllegalArgumentException("exercise class does not correspond to exerciseClass");
        }

        // making the shallow copy
        var puzzleConfig = switch (exercise) {
        case VisualPerfectPitchExercise e -> new VisualPerfectPitchConfigAggregate(vppc.targetNumberOfPuzzles, vppc.statsRecording, vppc.normalizedNotesForPuzzle, vppc.normalizedRootNote, vppc.inputMode);
        case AudioPerfectPitchExercise e -> new AudioPerfectPitchConfigAggregate(appc.targetNumberOfPuzzles, appc.statsRecording, appc.normalizedNotesForPuzzle, appc.normalizedRootNote, appc.inputMode);
        default -> throw new RuntimeException("unknown exercise on config fetching: " + exercise);
        };

        return (PC) puzzleConfig;
    }

    public InMemoryPuzzleConfigRepository() {
    }

    public <E extends Exercise, PCA extends PuzzleConfigAggregate<? extends PuzzleConfigAggregate<E>>> PCA get(E exercise) {
        var puzzleConfigAggregate = AbstractPuzzleConfigAggregatesFactory.create(exercise.getClass(), exercise);
        return (PCA) puzzleConfigAggregate;
    }

    // TODO transaction stuff?
    // TODO it has to be more elegant
    // TODO refine
    public void save(final PuzzleConfigAggregate<?> puzzleConfig) {
        // var puzzleConfig = puzzleConfigAggregate.getPuzzleConfig();
        // writeService.save(puzzleConfig);

        switch (puzzleConfig.exercise) {
        case AudioPerfectPitchExercise exercise:
            InMemoryReadPuzzleConfigService.appc = (AudioPerfectPitchConfigAggregate) puzzleConfig;
            break;
        default:

            break;
        }
    }
}
