package org.swetlokognatsk.earking_out.inftrastructure.adapters.puzzles.generators.perfectpitch;

import static org.junit.Assert.*;
import java.util.Arrays;
import java.util.stream.Stream;
import org.apache.commons.lang3.ArrayUtils;
import org.junit.*;
import org.swetlokognatsk.earking_out.core.domain.model.Solution;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.AudioPerfectPitchExercise;
import org.swetlokognatsk.earking_out.core.domain.model.piano.keyboard.PianoKeyboardAggregate;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.factories.AbstractPuzzleConfigAggregatesFactory;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.factories.perfectpitch.AudioPerfectPitchConfigAggregatesFactory;
import org.swetlokognatsk.earking_out.core.domain.services.app.dto.puzzles.configs.PuzzleConfigDTOAssembler;
import org.swetlokognatsk.earking_out.core.domain.services.app.dto.puzzles.configs.perfectpitch.AudioPerfectPitchConfigDTO;
import org.swetlokognatsk.earking_out.inftrastructure.adapters.puzzles.generators.perfectpitch.RandomAudioPerfectPitchPuzzleGenerator;

public class RandomAudioPerfectPitchPuzzleGeneratorTest {
    protected static int ITERATIONS_NUMBER = 100;

    protected static AudioPerfectPitchConfigAggregatesFactory factory = AbstractPuzzleConfigAggregatesFactory.createFactory(new AudioPerfectPitchExercise());

    @Test
    public void generateSolutionTest() {
        var notes = new byte[] { 4, 5 };
        var generator = createPuzzleGenerator(notes);

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

    protected static RandomAudioPerfectPitchPuzzleGenerator createPuzzleGenerator(final byte[] normalizedNotesForPuzzle) {
        // TODO how to validate aggregate?
        // TODO can it be in invalid state at all?
        // firstly creating puzzleConfig for validation
        var puzzleConfig = factory.create(0, false, normalizedNotesForPuzzle, null, null, new PianoKeyboardAggregate[0]);
        AudioPerfectPitchConfigDTO puzzleConfigDto = PuzzleConfigDTOAssembler.assemble(puzzleConfig);
        var generator = new RandomAudioPerfectPitchPuzzleGenerator(puzzleConfigDto);
        return generator;
    }
}
