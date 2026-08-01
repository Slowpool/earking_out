package org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.factories;

import static org.junit.Assert.*;
import org.junit.Test;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.AudioPerfectPitchExercise;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.factories.perfectpitch.AudioPerfectPitchConfigAggregatesFactory;
import org.swetlokognatsk.earking_out.inftrastructure.adapters.base.SerializationCloner;

public final class AbstractPuzzleConfigAggregatesFactoryTest {

    @Test
    public void testAudioPerfectPitch() {
        var exercise = new AudioPerfectPitchExercise();
        var abstractPuzzleConfigAggregatesFactory = new AbstractPuzzleConfigAggregatesFactory(new SerializationCloner());

        var audioPerfectPitchFactory = abstractPuzzleConfigAggregatesFactory.createFactory(exercise);

        assertEquals(AudioPerfectPitchConfigAggregatesFactory.class.getName(), audioPerfectPitchFactory.getClass().getName());
    }

}
