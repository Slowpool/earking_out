package org.swetlokognatsk.earking_out.core.domain.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.factories.perfectpitch.AudioPerfectPitchConfigAggregatesFactory;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.perfectpitch.AudioPerfectPitchConfigAggregate;

public class AudioPerfectPitchConfigAggregatesFactoryTest {

    @Test
    public void createDefault() {
        var factory = new AudioPerfectPitchConfigAggregatesFactory();
        var puzzleConfig = factory.createDefault();
        assertEquals(AudioPerfectPitchConfigAggregate.class.getName(), puzzleConfig.getClass().getName());
    }
}
