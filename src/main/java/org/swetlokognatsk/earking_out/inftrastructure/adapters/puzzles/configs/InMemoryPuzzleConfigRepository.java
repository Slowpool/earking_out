package org.swetlokognatsk.earking_out.inftrastructure.adapters.puzzles.configs;

import java.util.HashMap;
import java.util.Map;
import org.swetlokognatsk.earking_out.core.domain.model.base.DependentAggregatesDTO;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.Exercise;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.ExercisesFactory;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.AudioPerfectPitchExercise;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.VisualPerfectPitchExercise;
import org.swetlokognatsk.earking_out.core.domain.model.piano.keyboard.PianoKeyboardAggregate;
import org.swetlokognatsk.earking_out.core.domain.model.piano.keyboard.PianoKeyboardId;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.PuzzleConfigAggregate;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.factories.PuzzleConfigAggregatesFactory;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.factories.perfectpitch.PerfectPitchConfigDependentAggregatesDTO;
import org.swetlokognatsk.earking_out.core.ports.config.PuzzleConfigRepository;
import org.swetlokognatsk.earking_out.core.ports.piano.PianoKeyboardRepository;
import static org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.factories.AbstractPuzzleConfigAggregatesFactory.*;

public final class InMemoryPuzzleConfigRepository implements PuzzleConfigRepository {
    protected final Map<Exercise, PuzzleConfigAggregate<?>> aggregates = new HashMap<>();

    protected final PianoKeyboardRepository pianoKeyboardRepository;

    protected <DADTO extends DependentAggregatesDTO, E extends Exercise, F extends PuzzleConfigAggregatesFactory<? extends PuzzleConfigAggregate<E>, DADTO>> PuzzleConfigAggregate<E> createDefault(final F factory, final E exercise) {
        var defaultPuzzleConfig = switch (exercise) {
        // TODO refactoring. probably via factory - earlier i thought it should not utilize repositories, but now it seems completely fine.
        case AudioPerfectPitchExercise e -> {
            var dependentAggregates = getAudioPerfectPitchConfigDependentAggregates(exercise);
            var audioPerfectPitchConfig = factory.createDefault((DADTO) dependentAggregates);
            yield audioPerfectPitchConfig;
        }
        case VisualPerfectPitchExercise e -> {
            var dependentAggregates = getAudioPerfectPitchConfigDependentAggregates(exercise);
            var visualPerfectPitchConfig = factory.createDefault((DADTO) dependentAggregates);
            yield visualPerfectPitchConfig;
        }
        default -> throw new IllegalArgumentException("unknown exercise: " + exercise);
        };

        return defaultPuzzleConfig;
    }

    // TODO polymorphic stuff??
    protected PerfectPitchConfigDependentAggregatesDTO getAudioPerfectPitchConfigDependentAggregates(final Exercise exercise) {
        var pianoKeyboardIds = PianoKeyboardId.getPianoKeyboardIds(exercise);
        var pianoKeyboards = new PianoKeyboardAggregate[pianoKeyboardIds.length];
        for (int i = 0; i < pianoKeyboards.length; i++) {
            pianoKeyboards[i] = pianoKeyboardRepository.get(pianoKeyboardIds[i]);
        }
        var dependentAggregates = new PerfectPitchConfigDependentAggregatesDTO(pianoKeyboards);
        return dependentAggregates;
    }

    public InMemoryPuzzleConfigRepository(final PianoKeyboardRepository pianoKeyboardRepository) {
        this.pianoKeyboardRepository = pianoKeyboardRepository;

        seedConfigs();
    }

    protected void seedConfigs() {
        // TODO what is this??
        // var notes = new byte[] { new NoteWithAccidental(NoteNames.D, null, Octaves.FIRST).normalize() };
        // appc = new AudioPerfectPitchConfigAggregate(100, false, notes, Byte.valueOf((byte) 25), PerfectPitchInputMode.NOTES_AS_CHARACTERS);
        // vppc = new VisualPerfectPitchConfigAggregate(0, false, new byte[0], null, PerfectPitchInputMode.KEYBOARD_AS_PIANO);
        var exercises = ExercisesFactory.getAll();
        PuzzleConfigAggregate<?> puzzleConfigAggregate;
        for (var exercise : exercises) {
            var factory = createFactory(exercise);
            puzzleConfigAggregate = createDefault(factory, exercise);
            aggregates.put(exercise, puzzleConfigAggregate);
        }
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
        var puzzleConfigAggregateFactory = (PuzzleConfigAggregatesFactory<PCA, ?>) createFactory(puzzleConfigAggregate.getId());
        PCA puzzleConfigAggregateCopy = puzzleConfigAggregateFactory.createDeepCopy(puzzleConfigAggregate);
        return (PCA) puzzleConfigAggregateCopy;
    }

    // TODO this method must be synchronous distributed transaction.
    public void genericSave(final PuzzleConfigAggregate<?> puzzleConfigAggregate) {
        var puzzleConfigAggregateCopy = createDeepCopy(puzzleConfigAggregate);
        aggregates.put(puzzleConfigAggregateCopy.getId(), puzzleConfigAggregateCopy);
        // draft version. i'm not sure whether should repository be used here cuz PianoKeyboardAggregate is not a root aggregate. whilst in classic ddd only root aggregates should have repository
        var pianoKeyboardsToSave = puzzleConfigAggregate.pianoKeyboardAggregates;
        PianoKeyboardAggregate pianoKeyboard;
        for (var pianoKeyboardId : pianoKeyboardsToSave.keySet()) {
            pianoKeyboard = pianoKeyboardsToSave.get(pianoKeyboardId);
            pianoKeyboardRepository.save(pianoKeyboard);
        }

    }

    public PuzzleConfigAggregate<Exercise> get(final Exercise exercise) {
        return genericGet(exercise);
    }

    public void save(final PuzzleConfigAggregate<Exercise> aggregate) {
        genericSave(aggregate);
    }
}
