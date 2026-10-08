package org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.factories.perfectpitch;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.AudioPerfectPitchExercise;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.factories.PuzzleConfigAggregatesFactoryResolver;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.perfectpitch.AudioPerfectPitchConfigAggregate;
import org.swetlokognatsk.earking_out.infrastructure.adapters.base.SerializationCloner;
import static org.swetlokognatsk.earking_out.core.domain.model.exercises.ExercisesFactory.*;

@SpringBootTest
public class AudioPerfectPitchConfigAggregatesFactoryTest {

    @Test
    public void createDefault() {
        var puzzleConfigAggregatesFactoryResolver = new PuzzleConfigAggregatesFactoryResolver();
        AudioPerfectPitchConfigAggregatesFactory factory = puzzleConfigAggregatesFactoryResolver.resolveFactory(AUDIO_PERFECT_PITCH_EXERCISE);

        var puzzleConfig = factory.createDefault();

        assertTrue(AudioPerfectPitchConfigAggregate.class.equals(puzzleConfig.getClass()));
    }
}
