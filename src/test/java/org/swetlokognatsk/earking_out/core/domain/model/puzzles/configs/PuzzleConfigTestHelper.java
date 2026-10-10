package org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs;

import org.swetlokognatsk.earking_out.core.domain.model.piano.key.PianoKeyNumber;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.factories.perfectpitch.AudioPerfectPitchConfigAggregatesFactory;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.perfectpitch.PerfectPitchConfigAggregate;
import org.swetlokognatsk.earking_out.core.ports.config.PuzzleConfigRepository;
import org.swetlokognatsk.earking_out.infrastructure.annotations.TestComponent;
import lombok.AllArgsConstructor;
import static org.swetlokognatsk.earking_out.core.domain.model.piano.key.PianoKeyNumber.*;

@TestComponent
@AllArgsConstructor
public class PuzzleConfigTestHelper {

    private final AudioPerfectPitchConfigAggregatesFactory puzzleConfigsFactory;
    private final PuzzleConfigRepository puzzleConfigRepository;

    public void configureSomeValidPuzzleConfig() {
        var puzzleConfig = puzzleConfigsFactory.createDefault();
        // TODO use common SOME_PIANO_KEYS
        var somePianoKeys = new PianoKeyNumber[] { FIRST_NOTE_NUMBER, LAST_NOTE_NUMBER };
        puzzleConfig.updateProperty(PerfectPitchConfigAggregate.NORMALIZED_NOTES_FOR_PUZZLE_PROP, somePianoKeys);
        puzzleConfigRepository.save(puzzleConfig);
    }
}
