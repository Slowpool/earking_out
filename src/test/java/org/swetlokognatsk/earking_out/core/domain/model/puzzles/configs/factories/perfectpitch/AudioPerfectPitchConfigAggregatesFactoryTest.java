package org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.factories.perfectpitch;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.AudioPerfectPitchExercise;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.factories.AbstractPuzzleConfigAggregatesFactory;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.factories.perfectpitch.AudioPerfectPitchConfigAggregatesFactory;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.factories.perfectpitch.PerfectPitchConfigDependentAggregatesDTO;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.perfectpitch.AudioPerfectPitchConfigAggregate;

public class AudioPerfectPitchConfigAggregatesFactoryTest {

    @Test
    public void createDefault() {
        AudioPerfectPitchConfigAggregatesFactory factory = AbstractPuzzleConfigAggregatesFactory.createFactory(new AudioPerfectPitchExercise());
        var puzzleConfig = factory.createDefault(PerfectPitchConfigDependentAggregatesDTO.EMPTY);
        assertEquals(AudioPerfectPitchConfigAggregate.class.getName(), puzzleConfig.getClass().getName());
    }
}
