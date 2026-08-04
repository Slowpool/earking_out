package org.swetlokognatsk.earking_out.inftrastructure.adapters.puzzles.generators.perfectpitch;

import static org.junit.Assert.*;
import static org.swetlokognatsk.earking_out.core.domain.model.music.Constants.*;
import static org.swetlokognatsk.earking_out.core.domain.model.piano.key.PianoKeyNumber.*;
import java.util.Arrays;
import java.util.stream.Stream;
import org.apache.commons.lang3.ArrayUtils;
import org.junit.*;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.AudioPerfectPitchExercise;
import org.swetlokognatsk.earking_out.core.domain.model.piano.key.PianoKeyNumber;
import org.swetlokognatsk.earking_out.core.domain.model.piano.key.PianoKeysFactory;
import org.swetlokognatsk.earking_out.core.domain.model.piano.keyboard.PianoKeyboardAggregate;
import org.swetlokognatsk.earking_out.core.domain.model.piano.keyboard.PianoKeyboardAggregatesFactory;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.factories.AbstractPuzzleConfigAggregatesFactory;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.factories.perfectpitch.AudioPerfectPitchConfigAggregatesFactory;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.perfectpitch.AudioPerfectPitchConfigAggregate;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.perfectpitch.PerfectPitchInputMode;
import org.swetlokognatsk.earking_out.core.domain.model.solutions.Solution;
import org.swetlokognatsk.earking_out.core.domain.model.solutions.perfectpitch.AudioPerfectPitchSolution;
import org.swetlokognatsk.earking_out.core.domain.services.app.dto.puzzles.configs.PuzzleConfigDTOAssembler;
import org.swetlokognatsk.earking_out.core.domain.services.app.dto.puzzles.configs.perfectpitch.AudioPerfectPitchConfigDTO;
import org.swetlokognatsk.earking_out.core.domain.services.domain.piano.PianoKeyColorService;
import org.swetlokognatsk.earking_out.core.ports.di.DI;
import org.swetlokognatsk.earking_out.inftrastructure.adapters.base.SerializationCloner;
import org.swetlokognatsk.earking_out.inftrastructure.adapters.piano.InMemoryPuzzleConfigPianoKeyboardRepository;
import org.swetlokognatsk.earking_out.inftrastructure.adapters.puzzles.configs.InMemoryPuzzleConfigRepository;

public class RandomAudioPerfectPitchSolutionGeneratorTest {
    private static int ITERATIONS_NUMBER = 100;

    private static AudioPerfectPitchConfigAggregatesFactory configFactory = new AbstractPuzzleConfigAggregatesFactory(new SerializationCloner()).createFactory(new AudioPerfectPitchExercise());

    private static RandomAudioPerfectPitchSolutionGenerator createPuzzleGenerator(final PianoKeyNumber[] normalizedNotesForPuzzle) {
        // TODO how to validate aggregate?
        // TODO can it be in invalid state at all?
        // firstly creating puzzleConfig for validation
        var puzzleConfig = createAnyPuzzleConfig(normalizedNotesForPuzzle);
        // TODO well, it's absolute mess
        var puzzleConfigDTOAssembler = DI.get(PuzzleConfigDTOAssembler.class);
        AudioPerfectPitchConfigDTO puzzleConfigDto = puzzleConfigDTOAssembler.assemble(puzzleConfig);
        var generator = new RandomAudioPerfectPitchSolutionGenerator(puzzleConfigDto);
        return generator;
    }

    private static AudioPerfectPitchConfigAggregate createAnyPuzzleConfig(final PianoKeyNumber[] normalizedNotesForPuzzle) {
        return configFactory.create(0, false, normalizedNotesForPuzzle, null, PerfectPitchInputMode.KEYBOARD_AS_PIANO, false, new PianoKeyboardAggregate[0]);
    }

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
}
