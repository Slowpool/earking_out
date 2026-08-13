package org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.factories.perfectpitch;

import static org.junit.Assert.*;
import org.junit.Test;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.AudioPerfectPitchExercise;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.factories.AbstractPuzzleConfigAggregatesFactory;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.perfectpitch.AudioPerfectPitchConfigAggregate;
import org.swetlokognatsk.earking_out.infrastructure.adapters.base.SerializationCloner;

public class AudioPerfectPitchConfigAggregatesFactoryTest {

    @Test
    public void createDefault() {
        var abstractPuzzleConfigAggregatesFactory = new AbstractPuzzleConfigAggregatesFactory(new SerializationCloner());
        AudioPerfectPitchConfigAggregatesFactory factory = abstractPuzzleConfigAggregatesFactory.createFactory(new AudioPerfectPitchExercise());

        var puzzleConfig = factory.createDefault();

        assertTrue(AudioPerfectPitchConfigAggregate.class.equals(puzzleConfig.getClass()));
    }
}
