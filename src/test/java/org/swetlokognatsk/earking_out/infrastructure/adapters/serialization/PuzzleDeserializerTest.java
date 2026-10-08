package org.swetlokognatsk.earking_out.infrastructure.adapters.serialization;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.ExerciseNames;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.ExerciseTypes;
import org.swetlokognatsk.earking_out.core.domain.model.piano.key.PianoKeyNumber;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.Puzzle;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.perfectpitch.AudioPerfectPitchPuzzle;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.perfectpitch.PerfectPitchPuzzle;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.perfectpitch.VisualPerfectPitchPuzzle;
import org.swetlokognatsk.earking_out.core.domain.model.solutions.perfectpitch.AudioPerfectPitchSolution;
import tools.jackson.databind.module.SimpleModule;

@SpringBootTest
public class PuzzleDeserializerTest extends DeserializerBaseTest<Puzzle<?, ?>, PuzzleDeserializer> {

    private static final String AUDIO_PERFECT_PITCH_PUZZLE = """
            {
                "exercise": {
                    "name": "PERFECT_PITCH",
                    "type": "AUDIO"
                },
                "solution": {
                    "keyNumber": 53
                }
            }
            """;
    private static final String VISUAL_PERFECT_PITCH_PUZZLE = """
            {
                "exercise": {
                    "name": "PERFECT_PITCH",
                    "type": "VISUAL"
                },
                "solution": {
                    "keyNumber": 53
                }
            }
            """;

    protected Class<PuzzleDeserializer> getTestedDeserializerClass() {
        return PuzzleDeserializer.class;
    }

    @SuppressWarnings("unchecked")
    protected Class<Puzzle<?, ?>> getTestedDeserializerType() {
        // weeeeell
        return (Class<Puzzle<?, ?>>) (Class<?>) Puzzle.class;
    }

    private void assertAudioPerfectPitchPuzzle(final Puzzle<?, ?> puzzle) {
        if (puzzle instanceof AudioPerfectPitchPuzzle typedPuzzle) {
            assertEquals(typedPuzzle.exercise.type, ExerciseTypes.AUDIO);

            assertPerfectPitchPuzzle(typedPuzzle);
        } else {
            fail();
        }
    }

    private void assertVisualPerfectPitchPuzzle(final Puzzle<?, ?> puzzle) {
        if (puzzle instanceof VisualPerfectPitchPuzzle typedPuzzle) {
            assertEquals(typedPuzzle.exercise.type, ExerciseTypes.VISUAL);

            assertPerfectPitchPuzzle(typedPuzzle);
        } else {
            fail();
        }
    }

    private void assertPerfectPitchPuzzle(final PerfectPitchPuzzle<?, ?> puzzle) {
        assertEquals(puzzle.exercise.name, ExerciseNames.PERFECT_PITCH);
        assertEquals(puzzle.solution.keyNumber, PianoKeyNumber.valueOf(53));
    }

    @Test
    public void deserializeAudioPerfectPitchPuzzleWithVC() {
        var puzzle = readValue(AUDIO_PERFECT_PITCH_PUZZLE, AudioPerfectPitchPuzzle.class);

        assertAudioPerfectPitchPuzzle(puzzle);
    }

    @Test
    public void deserializeAudioPerfectPitchPuzzleWithAbstractVC() {
        var puzzle = (AudioPerfectPitchPuzzle) readValue(AUDIO_PERFECT_PITCH_PUZZLE, Puzzle.class);

        assertAudioPerfectPitchPuzzle(puzzle);
    }

    // TODO return when i'll return to visual perfect pitch
    // @Test
    // public void deserializeVisualPerfectPitchPuzzleWithVC() {
    //     var puzzle = readValue(VISUAL_PERFECT_PITCH_PUZZLE, VisualPerfectPitchPuzzle.class);

    //     assertVisualPerfectPitchPuzzle(puzzle);
    // }

    // @Test
    // public void deserializeVisualPerfectPitchPuzzleWithAbstractVC() {
    //     var puzzle = (VisualPerfectPitchPuzzle) readValue(VISUAL_PERFECT_PITCH_PUZZLE, Puzzle.class);

    //     assertVisualPerfectPitchPuzzle(puzzle);
    // }
}
