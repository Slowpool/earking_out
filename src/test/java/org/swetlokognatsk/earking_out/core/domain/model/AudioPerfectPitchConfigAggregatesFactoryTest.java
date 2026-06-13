package org.swetlokognatsk.earking_out.core.domain.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.AudioPerfectPitchExercise;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.factories.AbstractPuzzleConfigAggregatesFactory;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.perfectpitch.AudioPerfectPitchConfigAggregate;

public class AudioPerfectPitchConfigAggregatesFactoryTest {

    @Test
    public void createDefault() {
        var factory = AbstractPuzzleConfigAggregatesFactory.createFactory(new AudioPerfectPitchExercise());
        var puzzleConfig = factory.createDefault();
        assertEquals(AudioPerfectPitchConfigAggregate.class.getName(), puzzleConfig.getClass().getName());
    }
}
