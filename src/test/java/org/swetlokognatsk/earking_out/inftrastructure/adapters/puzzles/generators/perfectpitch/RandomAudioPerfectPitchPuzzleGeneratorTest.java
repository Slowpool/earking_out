package org.swetlokognatsk.earking_out.inftrastructure.adapters.puzzles.generators.perfectpitch;

import static org.junit.Assert.*;
import java.util.Arrays;
import java.util.stream.Stream;
import org.apache.commons.lang3.ArrayUtils;
import org.junit.*;
import org.swetlokognatsk.earking_out.core.domain.model.Solution;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.AudioPerfectPitchExercise;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.factories.AbstractPuzzleConfigAggregatesFactory;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.factories.perfectpitch.AudioPerfectPitchConfigAggregatesFactory;

public class RandomAudioPerfectPitchPuzzleGeneratorTest {
    protected static int ITERATIONS_NUMBER = 100;

    protected static AudioPerfectPitchConfigAggregatesFactory factory = AbstractPuzzleConfigAggregatesFactory.createFactory(new AudioPerfectPitchExercise());

    @Test
    public void generateSolutionTest() {
        var notes = new byte[] { 4, 5 };
        var puzzleConfig = factory.create(0, false, notes, null, null);
        var generator = new RandomAudioPerfectPitchPuzzleGenerator(puzzleConfig);

        Byte[] ByteNotes = ArrayUtils.toObject(notes);
        Stream<Byte> stream = Arrays.stream(ByteNotes);
        var map = stream.map((Byte noteNumber) -> new Solution(String.valueOf(noteNumber)));
        Solution[] possibleSolutions = map.toArray(Solution[]::new);
        Solution generatedSolution;
        for (int i = 0; i < ITERATIONS_NUMBER; i++) {
            generatedSolution = generator.generateSolution();
            assertTrue(ArrayUtils.contains(possibleSolutions, generatedSolution));
        }
    }
}
