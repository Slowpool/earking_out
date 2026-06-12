package org.swetlokognatsk.earking_out.inftrastructure.adapters.puzzles.configs;

import static org.junit.Assert.*;
import org.junit.*;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.AudioPerfectPitchExercise;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.perfectpitch.AudioPerfectPitchConfigAggregate;

public final class InMemoryPuzzleConfigRepositoryTest {

    protected InMemoryPuzzleConfigRepository repository;

    @Before
    public void before() {
        repository = new InMemoryPuzzleConfigRepository();
    }

    @Test
    public void validateAggregateClass() {
        var audioPerfectPitchConfigAggregate = getAudioPerfectPitchConfigAggregate();

        assertEquals(AudioPerfectPitchConfigAggregate.class.getName(), audioPerfectPitchConfigAggregate.getClass().getName());
    }

    @Test
    public void changeAggregatePropertyWithoutSave() {
        var audioPerfectPitchConfigAggregate = getAudioPerfectPitchConfigAggregate();
        assertEquals(null, audioPerfectPitchConfigAggregate.normalizedRootNote);

        audioPerfectPitchConfigAggregate.normalizedRootNote = 9;

        audioPerfectPitchConfigAggregate = getAudioPerfectPitchConfigAggregate();
        assertEquals(null, audioPerfectPitchConfigAggregate.normalizedRootNote);
    }

    @Test
    public void changeAggregatePropertyWithSave() {
        var audioPerfectPitchConfigAggregate = getAudioPerfectPitchConfigAggregate();
        assertEquals(null, audioPerfectPitchConfigAggregate.normalizedRootNote);

        Byte newNormalizedRootNote = 9;
        audioPerfectPitchConfigAggregate.normalizedRootNote = newNormalizedRootNote;
        repository.save(audioPerfectPitchConfigAggregate);

        audioPerfectPitchConfigAggregate = getAudioPerfectPitchConfigAggregate();
        assertEquals(newNormalizedRootNote, audioPerfectPitchConfigAggregate.normalizedRootNote);
    }

    protected AudioPerfectPitchConfigAggregate getAudioPerfectPitchConfigAggregate() {
        var exercise = new AudioPerfectPitchExercise();
        AudioPerfectPitchConfigAggregate aggregate = repository.get(exercise);
        return aggregate;
    }
}
