package org.swetlokognatsk.earking_out.inftrastructure.adapters.hints;

import org.swetlokognatsk.earking_out.core.domain.model.Hint;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.Puzzle;
import org.swetlokognatsk.earking_out.core.ports.DI;
import org.swetlokognatsk.earking_out.core.ports.hints.IHintMapper;
import org.swetlokognatsk.earking_out.core.ports.hints.perfectPitch.IPerfectPitchHintMapper;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfect_pitch.puzzles.PerfectPitchPuzzle;

public class HintMapper implements IHintMapper {    
    public <T extends Hint> T map(Puzzle<?, ?, ?> puzzle) {
        var specificHintMapper = findSpecificHintMapper(puzzle);
        return specificHintMapper.map(puzzle);
    }

    private IHintMapper findSpecificHintMapper(Puzzle<?, ?, ?> puzzle) {
        var iSpecificHintMapper = switch (puzzle) {
            case PerfectPitchPuzzle perfectPitchPuzzle -> IPerfectPitchHintMapper.class;
            default -> throw new RuntimeException("unknown puzzle on looking for specificHintMapper");
        };
        return DI.get(iSpecificHintMapper);
    }
}
