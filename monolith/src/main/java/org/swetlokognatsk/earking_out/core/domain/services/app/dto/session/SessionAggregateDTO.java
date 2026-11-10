package org.swetlokognatsk.earking_out.core.domain.services.app.dto.session;

import org.swetlokognatsk.earking_out.core.domain.model.exercises.Exercise;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.Puzzle;
import org.swetlokognatsk.earking_out.core.domain.model.session.SessionAggregate;
import org.swetlokognatsk.earking_out.core.domain.model.session.SessionId;
import org.swetlokognatsk.earking_out.core.domain.model.session.SessionStates;
import org.swetlokognatsk.earking_out.core.domain.model.session.GeneralSessionStats;
import org.swetlokognatsk.earking_out.core.domain.services.app.dto.puzzles.configs.PuzzleConfigDTO;

public abstract class SessionAggregateDTO<E extends Exercise, P extends Puzzle<E, ?>, PCDTO extends PuzzleConfigDTO<E>, SA extends SessionAggregate<E, ?, P, PCDTO>> {
    public final SessionId sessionId;
    public final PCDTO puzzleConfigDto;
    public final GeneralSessionStats stats;
    public final SessionStates state;
    public final P puzzle;
    public final Boolean prevGuessIsSuccessful;
    public final Integer numberOfGuessesOfCurrentPuzzle;

    public SessionAggregateDTO(final SessionId sessionId, final PCDTO puzzleConfigDto, final GeneralSessionStats stats, final SessionStates state, final P puzzle, final Boolean prevGuessIsSuccessful, final Integer numberOfGuessesOfCurrentPuzzle) {
        this.sessionId = sessionId;
        this.puzzleConfigDto = puzzleConfigDto;
        this.stats = stats;
        this.state = state;
        this.puzzle = puzzle;
        this.prevGuessIsSuccessful = prevGuessIsSuccessful;
        this.numberOfGuessesOfCurrentPuzzle = numberOfGuessesOfCurrentPuzzle;
    }
}
