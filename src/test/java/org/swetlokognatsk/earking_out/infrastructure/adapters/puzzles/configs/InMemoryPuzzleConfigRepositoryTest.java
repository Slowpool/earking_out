package org.swetlokognatsk.earking_out.infrastructure.adapters.puzzles.configs;

import static org.junit.Assert.*;
import org.junit.*;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.Exercise;
import org.swetlokognatsk.earking_out.core.domain.model.identity.UserId;
import org.swetlokognatsk.earking_out.core.domain.model.identity.UserUuid;
import org.swetlokognatsk.earking_out.core.domain.model.piano.key.PianoKeyNumber;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.PuzzleConfigAggregate;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.perfectpitch.AudioPerfectPitchConfigAggregate;
import org.swetlokognatsk.earking_out.core.ports.base.AggregateRootRepository;
import org.swetlokognatsk.earking_out.core.ports.di.DI;
import org.swetlokognatsk.earking_out.infrastructure.adapters.InMemoryRepositoryTest;
import static org.swetlokognatsk.earking_out.core.domain.model.exercises.ExercisesFactory.*;
import java.util.UUID;

public final class InMemoryPuzzleConfigRepositoryTest extends InMemoryRepositoryTest<Exercise, PuzzleConfigAggregate<Exercise>, AggregateRootRepository<Exercise, PuzzleConfigAggregate<Exercise>>> {

    private InMemoryPuzzleConfigRepository repository;
    // TODO delete it?
    // private final static User USER_A = new User(new UserId(1), new UserUuid(UUID.fromString("00000000-0000-0000-0000-000000000001")), "a");
    // private final static User USER_B = new User(new UserId(2), new UserUuid(UUID.fromString("00000000-0000-0000-0000-000000000002")), "b");

    protected AggregateRootRepository<Exercise, PuzzleConfigAggregate<Exercise>> getRepository() {
        return repository;
    }

    protected PuzzleConfigAggregate<Exercise> getSomeAggregate() {
        PuzzleConfigAggregate<?> someAggregate = getPerfectPitchConfigAggregate();
        return (PuzzleConfigAggregate<Exercise>) someAggregate;
    }

    protected void makeMinorChange(final PuzzleConfigAggregate<Exercise> aggregate) {
        var targetNumberOfPuzzles = aggregate.getTargetNumberOfPuzzles();
        aggregate.updateProperty(PuzzleConfigAggregate.TARGET_NUMBER_OF_PUZZLES_PROP, ++targetNumberOfPuzzles);
    }

    protected void assertAreDifferentByMinorChange(final PuzzleConfigAggregate<Exercise> sourceAggregate, final PuzzleConfigAggregate<Exercise> editedAggregate) {
        assertEquals(sourceAggregate.getTargetNumberOfPuzzles() + 1, editedAggregate.getTargetNumberOfPuzzles());
    }

    @Before
    public void setup() {
        DI.refreshDependencies();
        repository = DI.get(InMemoryPuzzleConfigRepository.class);
    }

    @Test
    public void validateAggregateClass() {
        var aggregate = getPerfectPitchConfigAggregate();

        assertTrue(AudioPerfectPitchConfigAggregate.class.equals(aggregate.getClass()));
    }

    /**
     * See
     * {@link org.swetlokognatsk.earking_out.infrastructure.adapters.piano.InMemoryPianoKeyboardRepositoryTest#changeAggregatePropertyWithoutSave}
     * regarding @Deprecated
     */
    @Test
    @Deprecated
    public void changeAggregatePropertyWithoutSave() {
        var aggregate = getPerfectPitchConfigAggregate();
        assertEquals(null, aggregate.getNormalizedRootNote());

        PianoKeyNumber newNormalizedRootNote = PianoKeyNumber.valueOf(9);
        aggregate.updateProperty(AudioPerfectPitchConfigAggregate.NORMALIZED_ROOT_NOTE_PROP, newNormalizedRootNote);

        aggregate = getPerfectPitchConfigAggregate();
        assertEquals(null, aggregate.getNormalizedRootNote());
    }

    /**
     * See
     * {@link org.swetlokognatsk.earking_out.infrastructure.adapters.piano.InMemoryPianoKeyboardRepositoryTest#changeAggregatePropertyWithoutSave}
     * regarding @Deprecated
     */
    @Test
    @Deprecated
    public void changeAggregatePropertyWithSave() {
        var aggregate = getPerfectPitchConfigAggregate();
        assertEquals(null, aggregate.getNormalizedRootNote());

        PianoKeyNumber newNormalizedRootNote = PianoKeyNumber.valueOf(9);
        aggregate.updateProperty(AudioPerfectPitchConfigAggregate.NORMALIZED_ROOT_NOTE_PROP, newNormalizedRootNote);
        repository.save(aggregate);

        aggregate = getPerfectPitchConfigAggregate();
        assertEquals(newNormalizedRootNote, aggregate.getNormalizedRootNote());
    }

    private AudioPerfectPitchConfigAggregate getPerfectPitchConfigAggregate() {
        var exercise = AUDIO_PERFECT_PITCH_EXERCISE;
        AudioPerfectPitchConfigAggregate aggregate = repository.genericGet(exercise);
        return aggregate;
    }

    protected PuzzleConfigAggregate<?> getSomePuzzleConfigAggregate() {
        return getPerfectPitchConfigAggregate();
    }

    @Test
    public void ensureGetMethodGivesCopyWithoutSaveProxy() {
        ensureGetMethodGivesCopyWithoutSave();
    }

    @Test
    public void ensureGetMethodGivesCopyAfterSaveProxy() {
        ensureGetMethodGivesCopyAfterSave();
    }

    @Test
    public void ensureSaveMethodPersistsCopyProxy() {
        ensureSaveMethodPersistsCopy();
    }

    // TODO test different user_id but the same exercise and vice versa
}
