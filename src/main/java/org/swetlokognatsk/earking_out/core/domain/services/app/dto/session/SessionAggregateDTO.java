package org.swetlokognatsk.earking_out.core.domain.services.app.dto.session;

import org.swetlokognatsk.earking_out.core.domain.model.exercises.Exercise;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.Puzzle;
import org.swetlokognatsk.earking_out.core.domain.model.session.SessionAggregate;
import org.swetlokognatsk.earking_out.core.domain.model.session.SessionStates;
import org.swetlokognatsk.earking_out.core.domain.model.session.SessionStats;
import org.swetlokognatsk.earking_out.core.domain.services.app.dto.puzzles.configs.PuzzleConfigDTO;

public abstract class SessionAggregateDTO<E extends Exercise, P extends Puzzle<E, ?>, PCDTO extends PuzzleConfigDTO<E>, SA extends SessionAggregate<E, ?, P, PCDTO>> {
    public final PCDTO puzzleConfigDto;
    public final SessionStats stats;
    public final SessionStates state;
    public final P puzzle;
    public final Boolean prevGuessIsSuccessful;
    public final int numberOfGuessesOfCurrentPuzzle;

    public SessionAggregateDTO(final PCDTO puzzleConfigDto, final SessionStats stats, final SessionStates state, final P puzzle, final Boolean prevGuessIsSuccessful, final int numberOfGuessesOfCurrentPuzzle) {
        this.puzzleConfigDto = puzzleConfigDto;
        this.stats = stats;
        this.state = state;
        this.puzzle = puzzle;
        this.prevGuessIsSuccessful = prevGuessIsSuccessful;
        this.numberOfGuessesOfCurrentPuzzle = numberOfGuessesOfCurrentPuzzle;
    }
}
