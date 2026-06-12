package org.swetlokognatsk.earking_out.inftrastructure.adapters.puzzles.configs;

import java.util.HashMap;
import java.util.Map;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.Exercise;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.ExercisesFactory;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.AudioPerfectPitchExercise;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.VisualPerfectPitchExercise;
import org.swetlokognatsk.earking_out.core.domain.model.music.NoteNames;
import org.swetlokognatsk.earking_out.core.domain.model.music.NoteWithAccidental;
import org.swetlokognatsk.earking_out.core.domain.model.music.Octaves;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.PuzzleConfigAggregate;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.factories.AbstractPuzzleConfigAggregatesFactory;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.factories.PuzzleConfigAggregatesFactory;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.factories.perfectpitch.VisualPerfectPitchConfigAggregatesFactory;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.perfectpitch.AudioPerfectPitchConfigAggregate;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.perfectpitch.PerfectPitchInputMode;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.perfectpitch.VisualPerfectPitchConfigAggregate;
import org.swetlokognatsk.earking_out.core.ports.config.PuzzleConfigRepository;
import static org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.factories.AbstractPuzzleConfigAggregatesFactory.*;

public final class InMemoryPuzzleConfigRepository implements PuzzleConfigRepository {
    protected final Map<Exercise, PuzzleConfigAggregate<?>> aggregates;

    {
        // var notes = new byte[] { new NoteWithAccidental(NoteNames.D, null, Octaves.FIRST).normalize() };
        // appc = new AudioPerfectPitchConfigAggregate(100, false, notes, Byte.valueOf((byte) 25), PerfectPitchInputMode.NOTES_AS_CHARACTERS);
        // vppc = new VisualPerfectPitchConfigAggregate(0, false, new byte[0], null, PerfectPitchInputMode.KEYBOARD_AS_PIANO);
        aggregates = new HashMap<>();

        var exercises = ExercisesFactory.getAll();
        PuzzleConfigAggregatesFactory<?> factory;
        PuzzleConfigAggregate<?> puzzleConfigAggregate;
        for (var exercise : exercises) {
            factory = createFactory(exercise);
            puzzleConfigAggregate = factory.createDefault();
            aggregates.put(exercise, puzzleConfigAggregate);
        }
    }

    public InMemoryPuzzleConfigRepository() {
    }

    public <E extends Exercise, PCA extends PuzzleConfigAggregate<E>> PCA get(E exercise) {
        var puzzleConfigAggregate = aggregates.get(exercise);
        if (puzzleConfigAggregate == null) {
            throw new IllegalArgumentException("unknown exercise: " + exercise);
        }

        puzzleConfigAggregate = createShallowCopy(puzzleConfigAggregate);
        return (PCA) puzzleConfigAggregate;
    }

    protected <E extends Exercise> PuzzleConfigAggregate<E> createShallowCopy(final PuzzleConfigAggregate<E> puzzleConfigAggregate) {
        var puzzleConfigAggregateFactory = createFactory(puzzleConfigAggregate.exercise);
        var puzzleConfigAggregateCopy = puzzleConfigAggregateFactory.createShallowCopy(puzzleConfigAggregate);
        return (PuzzleConfigAggregate<E>) puzzleConfigAggregateCopy;
    }

    // TODO transaction stuff?
    public void save(final PuzzleConfigAggregate<?> puzzleConfigAggregate) {
        var puzzleConfigAggregateCopy = createShallowCopy(puzzleConfigAggregate);
        aggregates.put(puzzleConfigAggregateCopy.exercise, puzzleConfigAggregateCopy);
    }
}
