package org.swetlokognatsk.earking_out.infrastructure.adapters.puzzles.generators.perfectpitch;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.swetlokognatsk.earking_out.core.domain.model.solutions.perfectpitch.AudioPerfectPitchSolution;
import org.swetlokognatsk.earking_out.core.domain.services.app.dto.puzzles.configs.perfectpitch.AudioPerfectPitchConfigDTO;
import org.swetlokognatsk.earking_out.core.ports.puzzles.generators.ConfigBasedSolutionGenerator;
import org.swetlokognatsk.earking_out.core.ports.puzzles.generators.perfectpitch.AudioPerfectPitchSolutionGenerator;
import org.swetlokognatsk.earking_out.infrastructure.annotations.TestComponent;

@Primary
public class DetermenisticRandomAudioPerfectPitchSolutionGenerator extends ConfigBasedSolutionGenerator<AudioPerfectPitchSolution, AudioPerfectPitchConfigDTO> implements AudioPerfectPitchSolutionGenerator {

    public DetermenisticRandomAudioPerfectPitchSolutionGenerator(final AudioPerfectPitchConfigDTO puzzleConfigDto) {
        super(puzzleConfigDto);
    }

    /**
     * If number of puzzles is even, returns the most left note, otherwise - the
     * most right one.
     */
    public AudioPerfectPitchSolution generate() {
        var numberOfPossibleNotes = puzzleConfigDto.normalizedNotesForPuzzle.length;
        var pianoKeyNumberIndex = numberOfPossibleNotes % 2 == 0
                ? 0
                : numberOfPossibleNotes - 1;
        var pianoKeyNumber = puzzleConfigDto.normalizedNotesForPuzzle[pianoKeyNumberIndex];
        return new AudioPerfectPitchSolution(pianoKeyNumber);
    }

}
