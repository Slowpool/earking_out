package org.swetlokognatsk.earking_out.inftrastructure.adapters.puzzles.generators.perfectpitch;

import static org.junit.Assert.*;
import java.util.function.IntFunction;
import java.util.stream.Stream;
import org.apache.commons.lang3.ArrayUtils;
import org.junit.*;
import org.swetlokognatsk.earking_out.core.domain.model.Solution;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.perfectpitch.typed.AudioPerfectPitchConfig;

public class RandomPerfectPitchPuzzleGeneratorTest {
    protected static int ITERATIONS_NUMBER = 100;

    @Test
    public void generateSolutionTest() {
        var notes = new byte[] { 4, 5 };
        var puzzleConfig = new AudioPerfectPitchConfig(0, false, notes, null, null);
        // TODO why it cannot be cast?
        var generator = new RandomPerfectPitchPuzzleGenerator<AudioPerfectPitchConfig>(puzzleConfig) {
        };

        var possibleSolutions = Stream.of(notes).map(noteNumber -> new Solution(String.valueOf(noteNumber))).toArray();
        Solution generatedSolution;
        for (int i = 0; i < ITERATIONS_NUMBER; i++) {
            generatedSolution = generator.generateSolution();
            assertTrue(ArrayUtils.contains(possibleSolutions, generatedSolution));
        }
    }
}
