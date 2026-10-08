package org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.factories;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.factories.perfectpitch.AudioPerfectPitchConfigAggregatesFactory;
import static org.swetlokognatsk.earking_out.core.domain.model.exercises.ExercisesFactory.*;

@SpringBootTest
public final class PuzzleConfigAggregatesFactoryResolverTest {

    @Test
    public void testAudioPerfectPitch() {
        var exercise = AUDIO_PERFECT_PITCH_EXERCISE;
        var puzzleConfigAggregatesFactoryResolver = new PuzzleConfigAggregatesFactoryResolver();
        var audioPerfectPitchFactory = puzzleConfigAggregatesFactoryResolver.resolveFactory(exercise);

        assertTrue(AudioPerfectPitchConfigAggregatesFactory.class.equals(audioPerfectPitchFactory.getClass()));
    }

}
