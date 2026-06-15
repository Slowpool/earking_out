package org.swetlokognatsk.earking_out.inftrastructure.adapters.puzzles.configs;

import java.util.HashMap;
import java.util.Map;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.Exercise;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.ExercisesFactory;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.PuzzleConfigAggregate;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.factories.PuzzleConfigAggregatesFactory;
import org.swetlokognatsk.earking_out.core.ports.DI;
import org.swetlokognatsk.earking_out.core.ports.config.PuzzleConfigRepository;
import org.swetlokognatsk.earking_out.core.ports.piano.PianoKeyboardRepository;

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

    public <E extends Exercise, PCA extends PuzzleConfigAggregate<E>> PCA genericGet(final E exercise) {
        var puzzleConfigAggregate = aggregates.get(exercise);
        if (puzzleConfigAggregate == null) {
            throw new IllegalArgumentException("unknown exercise: " + exercise);
        }

        puzzleConfigAggregate = createDeepCopy(puzzleConfigAggregate);
        return (PCA) puzzleConfigAggregate;
    }

    protected <PCA extends PuzzleConfigAggregate<?>> PCA createDeepCopy(final PCA puzzleConfigAggregate) {
        var puzzleConfigAggregateFactory = (PuzzleConfigAggregatesFactory<PCA>) createFactory(puzzleConfigAggregate.getId());
        PCA puzzleConfigAggregateCopy = puzzleConfigAggregateFactory.createDeepCopy(puzzleConfigAggregate);
        return (PCA) puzzleConfigAggregateCopy;
    }

    // TODO this method must be synchronous distributed transaction.
    public void genericSave(final PuzzleConfigAggregate<?> puzzleConfigAggregate) {
        var puzzleConfigAggregateCopy = createDeepCopy(puzzleConfigAggregate);
        aggregates.put(puzzleConfigAggregateCopy.getId(), puzzleConfigAggregateCopy);
        // draft version
        // var pianoKeyboardRepository = DI.get(PianoKeyboardRepository.class);
        // for(var pianoKeyboard : puzzleConfigAggregate.get)
        // pianoKeyboard

    }

    public PuzzleConfigAggregate<Exercise> get(final Exercise exercise) {
        return genericGet(exercise);
    }

    public void save(final PuzzleConfigAggregate<Exercise> aggregate) {
        genericSave(aggregate);
    }
}
