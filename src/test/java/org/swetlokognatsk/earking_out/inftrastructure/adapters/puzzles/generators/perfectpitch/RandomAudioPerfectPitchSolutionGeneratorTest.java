package org.swetlokognatsk.earking_out.inftrastructure.adapters.puzzles.generators.perfectpitch;

import static org.junit.Assert.*;
import static org.swetlokognatsk.earking_out.core.domain.model.music.Constants.*;
import java.util.Arrays;
import java.util.stream.Stream;
import org.apache.commons.lang3.ArrayUtils;
import org.junit.*;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.AudioPerfectPitchExercise;
import org.swetlokognatsk.earking_out.core.domain.model.piano.key.PianoKeyNumber;
import org.swetlokognatsk.earking_out.core.domain.model.piano.keyboard.PianoKeyboardAggregate;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.factories.AbstractPuzzleConfigAggregatesFactory;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.factories.perfectpitch.AudioPerfectPitchConfigAggregatesFactory;
import org.swetlokognatsk.earking_out.core.domain.model.solutions.Solution;
import org.swetlokognatsk.earking_out.core.domain.model.solutions.perfectpitch.AudioPerfectPitchSolution;
import org.swetlokognatsk.earking_out.core.domain.services.app.dto.puzzles.configs.PuzzleConfigDTOAssembler;
import org.swetlokognatsk.earking_out.core.domain.services.app.dto.puzzles.configs.perfectpitch.AudioPerfectPitchConfigDTO;

public class RandomAudioPerfectPitchSolutionGeneratorTest {
    protected static int ITERATIONS_NUMBER = 100;

    protected static AudioPerfectPitchConfigAggregatesFactory factory = AbstractPuzzleConfigAggregatesFactory.createFactory(new AudioPerfectPitchExercise());

    @Test
    public void generateSolutionTest() {
        var notes = new PianoKeyNumber[] { FIRST_NOTE_NUMBER, FIRST_NOTE_NUMBER.increment() };
        var generator = createPuzzleGenerator(notes);

        Stream<PianoKeyNumber> stream = Arrays.stream(notes);
        var map = stream.map((PianoKeyNumber keyNumber) -> new AudioPerfectPitchSolution(keyNumber));
        Solution[] possibleSolutions = map.toArray(Solution[]::new);
        Solution generatedSolution;
        for (int i = 0; i < ITERATIONS_NUMBER; i++) {
            generatedSolution = generator.generate();
            assertTrue(ArrayUtils.contains(possibleSolutions, generatedSolution));
        }
    }

    protected static RandomAudioPerfectPitchSolutionGenerator createPuzzleGenerator(final PianoKeyNumber[] normalizedNotesForPuzzle) {
        // TODO how to validate aggregate?
        // TODO can it be in invalid state at all?
        // firstly creating puzzleConfig for validation
        var puzzleConfig = factory.create(0, false, normalizedNotesForPuzzle, null, null, new PianoKeyboardAggregate[0]);
        AudioPerfectPitchConfigDTO puzzleConfigDto = PuzzleConfigDTOAssembler.assemble(puzzleConfig);
        var generator = new RandomAudioPerfectPitchSolutionGenerator(puzzleConfigDto);
        return generator;
    }
}
