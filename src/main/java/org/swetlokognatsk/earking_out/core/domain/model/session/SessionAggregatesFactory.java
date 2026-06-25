package org.swetlokognatsk.earking_out.core.domain.model.session;

import java.util.UUID;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.Exercise;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.AudioPerfectPitchExercise;
import org.swetlokognatsk.earking_out.core.domain.services.app.dto.puzzles.configs.PuzzleConfigDTO;
import org.swetlokognatsk.earking_out.core.domain.services.app.dto.puzzles.configs.PuzzleConfigDTOAssembler;

public final class SessionAggregatesFactory {

    public SessionAggregatesFactory() {

    }

    public <E extends Exercise> SessionAggregate<E, ?, ? extends PuzzleConfigDTO<E>> create(final E exercise) {
        var puzzleConfigDto = PuzzleConfigDTOAssembler.getPuzzleConfigDTO(exercise);
        var sessionStats = new SessionStats(0, 0);
        var sessionAggregate = new SessionAggregate(UUID.randomUUID(), puzzleConfigDto, sessionStats);

        // // TODO do i need it?
        // var sessionAggregate = switch (exercise) {
        //     case AudioPerfectPitchExercise e -> {
        //         yield new SessionAggregate(UUID.randomUUID(), puzzleConfigDto, sessionStats);
        //     }
        //     default -> throw new IllegalArgumentException("unkown exercise");
        // }
        return sessionAggregate;
    }
}
