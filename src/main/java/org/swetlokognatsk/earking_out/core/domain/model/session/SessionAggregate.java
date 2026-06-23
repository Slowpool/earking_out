package org.swetlokognatsk.earking_out.core.domain.model.session;

import java.util.Objects;
import java.util.UUID;
import org.swetlokognatsk.earking_out.core.domain.model.Guess;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.Exercise;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.Puzzle;
import org.swetlokognatsk.earking_out.core.domain.services.app.dto.puzzles.configs.PuzzleConfigDTO;

public final class SessionAggregate<E extends Exercise, P extends Puzzle<E> PCDTO extends PuzzleConfigDTO<?>> {
    private final UUID id;
    private final PCDTO puzzleConfigDto;
    private SessionStats stats;

    private SessionStates state;
    private P puzzle;

    public UUID getId() {
        return id;
    }

    public PCDTO getPuzzleConfig() {
        return puzzleConfigDto;
    }

    public SessionStats getStats() {
        return stats;
    }

    public SessionStates getState() {
        return state;
    }

    public int getPuzzlesCompleted() {
        return stats.puzzlesCompleted;
    }

    public P getPuzzle() {
        return puzzle;
    }

    public SessionAggregate(final UUID id, final PCDTO puzzleConfigDto, final SessionStats stats) {
        Objects.nonNull(puzzleConfigDto);
        Objects.nonNull(stats);

        this.id = id;
        this.puzzleConfigDto = puzzleConfigDto;
        this.stats = stats;

        state = SessionStates.IN_PROGRESS;
        puzzle
    }

    public void guess(final Guess guess) {

    }

}
