package org.swetlokognatsk.earking_out.inftrastructure.adapters.hints.perfectPitch;

import org.swetlokognatsk.earking_out.core.domain.model.Hint;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.ExerciseNames;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.Puzzle;
import org.swetlokognatsk.earking_out.core.ports.hints.perfectPitch.IPerfectPitchHintMapper;

public class PerfectPitchHintMapper implements IPerfectPitchHintMapper {
    public <T extends Hint> T map(Puzzle<?, ?, ?> puzzle) {
        // TODO refactoring
        if (puzzle.exercise.name == ExerciseNames.PERFECT_PITCH) {
            return findPerfectPitchMapper(puzzle);
        }
        else {
            throw new RuntimeException("unknown exercise");
        }
    }

    private IPerfectPitchHintMapper findPerfectPitchMapper(Puzzle<?, ?, ?> puzzle) {
        // TODO here i go
        return null;
    }

}
