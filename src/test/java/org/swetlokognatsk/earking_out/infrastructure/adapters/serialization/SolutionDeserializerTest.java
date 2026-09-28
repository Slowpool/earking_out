package org.swetlokognatsk.earking_out.infrastructure.adapters.serialization;

import static org.junit.Assert.*;
import org.junit.*;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.ExerciseNames;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.ExerciseTypes;
import org.swetlokognatsk.earking_out.core.domain.model.piano.key.PianoKeyNumber;
import org.swetlokognatsk.earking_out.core.domain.model.solutions.Solution;
import org.swetlokognatsk.earking_out.core.domain.model.solutions.perfectpitch.AudioPerfectPitchSolution;
import org.swetlokognatsk.earking_out.core.domain.model.solutions.perfectpitch.PerfectPitchSolution;
import org.swetlokognatsk.earking_out.core.domain.model.solutions.perfectpitch.VisualPerfectPitchSolution;

public class SolutionDeserializerTest extends DeserializerBaseTest<Solution, SolutionDeserializer> {
    private static final String AUDIO_PERFECT_PITCH_SOLUTION = """
            {
                "exercise": {
                    "name": "PERFECT_PITCH",
                    "type": "AUDIO"
                },
                "keyNumber": 53
            }
            """;
    private static final String VISUAL_PERFECT_PITCH_SOLUTION = """
            {
                "exercise": {
                    "name": "PERFECT_PITCH",
                    "type": "VISUAL"
                },
                "keyNumber": 53
            }
            """;

    protected Class<SolutionDeserializer> getTestedDeserializerClass() {
        return SolutionDeserializer.class;
    }

    protected Class<Solution> getTestedDeserializerType() {
        return Solution.class;
    }

    private void assertAudioPerfectPitchSolution(final Solution solution) {
        if (solution instanceof AudioPerfectPitchSolution typedSolution) {
            assertEquals(typedSolution.exercise.type, ExerciseTypes.AUDIO);

            assertPerfectPitchSolution(typedSolution);
        } else {
            fail();
        }
    }

    private void assertVisualPerfectPitchSolution(final Solution solution) {
        if (solution instanceof VisualPerfectPitchSolution typedSolution) {
            assertEquals(typedSolution.exercise.type, ExerciseTypes.VISUAL);

            assertPerfectPitchSolution(typedSolution);
        } else {
            fail();
        }
    }

    private void assertPerfectPitchSolution(final PerfectPitchSolution solution) {
        assertEquals(solution.exercise.name, ExerciseNames.PERFECT_PITCH);
        assertEquals(solution.keyNumber, PianoKeyNumber.valueOf(53));
    }

    @Test
    public void deserializeAudioPerfectPitchSolution() {
        var solution = readValue(AUDIO_PERFECT_PITCH_SOLUTION, AudioPerfectPitchSolution.class);

        assertAudioPerfectPitchSolution(solution);
    }

    // // TODO uncover after returning to visual perfect pitch
    // @Test
    // public void deserializeVisualPerfectPitchPuzzleWithVC() {
    //     var puzzle = readValue(VISUAL_PERFECT_PITCH_PUZZLE, VisualPerfectPitchPuzzle.class);

    //     assertVisualPerfectPitchPuzzle(puzzle);
    // }
}
