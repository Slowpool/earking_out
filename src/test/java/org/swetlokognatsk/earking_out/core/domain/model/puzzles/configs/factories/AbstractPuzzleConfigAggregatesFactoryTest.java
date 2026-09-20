package org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.factories;

import static org.junit.Assert.*;
import org.junit.Test;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.factories.perfectpitch.AudioPerfectPitchConfigAggregatesFactory;
import org.swetlokognatsk.earking_out.infrastructure.adapters.base.SerializationCloner;
import static org.swetlokognatsk.earking_out.core.domain.model.exercises.ExercisesFactory.*;

public final class AbstractPuzzleConfigAggregatesFactoryTest {

    @Test
    public void testAudioPerfectPitch() {
        var exercise = AUDIO_PERFECT_PITCH_EXERCISE;
        var abstractPuzzleConfigAggregatesFactory = new AbstractPuzzleConfigAggregatesFactory(new SerializationCloner());

        var audioPerfectPitchFactory = abstractPuzzleConfigAggregatesFactory.createFactory(exercise);

        assertTrue(AudioPerfectPitchConfigAggregatesFactory.class.equals(audioPerfectPitchFactory.getClass()));
    }

}
