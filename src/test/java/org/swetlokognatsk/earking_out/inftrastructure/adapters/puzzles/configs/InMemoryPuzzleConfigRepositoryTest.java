package org.swetlokognatsk.earking_out.inftrastructure.adapters.puzzles.configs;

import static org.junit.Assert.*;
import org.junit.*;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.Exercise;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.AudioPerfectPitchExercise;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.PuzzleConfigAggregate;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.perfectpitch.AudioPerfectPitchConfigAggregate;
import org.swetlokognatsk.earking_out.core.ports.base.AggregateRepository;
import org.swetlokognatsk.earking_out.inftrastructure.adapters.InMemoryRepositoryTest;

public final class InMemoryPuzzleConfigRepositoryTest extends InMemoryRepositoryTest<Exercise, PuzzleConfigAggregate<Exercise>, AggregateRepository<Exercise, PuzzleConfigAggregate<Exercise>>> {

    protected InMemoryPuzzleConfigRepository repository;

    protected AggregateRepository<Exercise, PuzzleConfigAggregate<Exercise>> getRepository() {
        return repository;
    }

    protected PuzzleConfigAggregate<Exercise> getSomeAggregate() {
        return getPerfectPitchConfigAggregate();
    }

    protected void makeMinorChange(final PuzzleConfigAggregate<?> aggregate) {
        var targetNumberOfPuzzles = aggregate.getTargetNumberOfPuzzles();
        aggregate.updateProperty(PuzzleConfigAggregate.TARGET_NUMBER_OF_PUZZLES_PROP, ++targetNumberOfPuzzles);
    }

    protected void assertAreDifferentByMinorChange(final PuzzleConfigAggregate<?> sourceAggregate, final PuzzleConfigAggregate<?> editedAggregate) {
        assertEquals(sourceAggregate.getTargetNumberOfPuzzles() + 1, editedAggregate.getTargetNumberOfPuzzles());
    }

    @Before
    public void setup() {
        repository = new InMemoryPuzzleConfigRepository();
    }

    @Test
    public void validateAggregateClass() {
        var aggregate = getPerfectPitchConfigAggregate();

        assertEquals(AudioPerfectPitchConfigAggregate.class.getName(), aggregate.getClass().getName());
    }

    /**
     * See
     * {@link org.swetlokognatsk.earking_out.inftrastructure.adapters.InMemoryPianoKeyboardRepositoryTest#changeAggregatePropertyWithoutSave}
     * regarding @Deprecated
     */
    @Test
    @Deprecated
    public void changeAggregatePropertyWithoutSave() {
        var aggregate = getPerfectPitchConfigAggregate();
        assertEquals(null, aggregate.getNormalizedRootNote());

        Byte newNormalizedRootNote = 9;
        aggregate.updateProperty(AudioPerfectPitchConfigAggregate.NORMALIZED_ROOT_NOTE_PROP, newNormalizedRootNote);

        aggregate = getPerfectPitchConfigAggregate();
        assertEquals(null, aggregate.getNormalizedRootNote());
    }

    /**
     * See
     * {@link org.swetlokognatsk.earking_out.inftrastructure.adapters.InMemoryPianoKeyboardRepositoryTest#changeAggregatePropertyWithoutSave}
     * regarding @Deprecated
     */
    @Test
    @Deprecated
    public void changeAggregatePropertyWithSave() {
        var aggregate = getPerfectPitchConfigAggregate();
        assertEquals(null, aggregate.getNormalizedRootNote());

        Byte newNormalizedRootNote = 9;
        aggregate.updateProperty(AudioPerfectPitchConfigAggregate.NORMALIZED_ROOT_NOTE_PROP, newNormalizedRootNote);
        repository.save(aggregate);

        aggregate = getPerfectPitchConfigAggregate();
        assertEquals(newNormalizedRootNote, aggregate.getNormalizedRootNote());
    }

    protected AudioPerfectPitchConfigAggregate getPerfectPitchConfigAggregate() {
        var exercise = new AudioPerfectPitchExercise();
        AudioPerfectPitchConfigAggregate aggregate = repository.get(exercise);
        return aggregate;
    }

    protected PuzzleConfigAggregate<?> getSomePuzzleConfigAggregate() {
        return getPerfectPitchConfigAggregate();
    }
}
