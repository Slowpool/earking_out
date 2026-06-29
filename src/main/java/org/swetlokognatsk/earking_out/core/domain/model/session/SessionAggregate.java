package org.swetlokognatsk.earking_out.core.domain.model.session;

import java.util.Objects;
import java.util.UUID;
import org.swetlokognatsk.earking_out.app.desktop.services.AudioHintPlayer;
import org.swetlokognatsk.earking_out.core.domain.model.base.Aggregate;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.Exercise;
import org.swetlokognatsk.earking_out.core.domain.model.guesses.Guess;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.Puzzle;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.PuzzlesFactory;
import org.swetlokognatsk.earking_out.core.domain.services.app.dto.puzzles.configs.PuzzleConfigDTO;
import org.swetlokognatsk.earking_out.core.ports.DI;
import org.swetlokognatsk.earking_out.core.ports.hints.demonstrators.HintDemonstrator;

public final class SessionAggregate<E extends Exercise, P extends Puzzle<E, ?>, PCDTO extends PuzzleConfigDTO<E>> extends Aggregate<UUID> {
    private final PCDTO puzzleConfigDto;
    private SessionStats stats;

    private SessionStates state;
    private P puzzle;
    private boolean prevGuessIsSuccessful;
    private int numberOfGuessesOfCurrentPuzzle;

    public PCDTO getPuzzleConfig() {
        return puzzleConfigDto;
    }

    public SessionStats getStats() {
        return stats;
    }

    protected void setStats(final SessionStats stats) {
        this.stats = stats;
    }

    public SessionStates getState() {
        return state;
    }

    protected void setState(final SessionStates state) {
        this.state = state;
    }

    public int getPuzzlesCompleted() {
        return stats.puzzlesCompleted;
    }

    public int getPuzzlesCompletedCorrectly() {
        return stats.puzzlesCompletedCorrectly;
    }

    public P getPuzzle() {
        if (state != SessionStates.IN_PROGRESS) {
            throw new IllegalStateException("session cannot have a puzzle if it is not in progress");
        }
        return puzzle;
    }

    protected void setPuzzle(final P puzzle) {
        this.puzzle = puzzle;
    }

    public boolean getPrevGuessIsSuccessful() {
        // TODO return it back when PositiveNumber VO is used instead
        // if (stats.puzzlesCompleted.equals(Integer.valueOf(0))) {
        if (thereAreNoAnyGuessesInSession()) {
            throw new IllegalStateException("");
        }
        return prevGuessIsSuccessful;
    }

    protected boolean thereAreNoAnyGuessesInSession() {
        return stats.puzzlesCompleted == 0 && numberOfGuessesOfCurrentPuzzle == 0;
    }

    public int getNumberOfGuessesOfCurrentPuzzle() {
        try {
            getPuzzle();
        } catch (IllegalStateException e) {
            throw new IllegalStateException("puzzle guessing is already finished and there's no current puzzle");
        }
        return numberOfGuessesOfCurrentPuzzle;
    }

    protected void setNumberOfGuessesOfCurrentPuzzle(final int numberOfGuessesOfCurrentPuzzle) {
        this.numberOfGuessesOfCurrentPuzzle = numberOfGuessesOfCurrentPuzzle;
    }

    protected void setPrevGuessIsSuccessful(final boolean prevGuessIsSuccessful) {
        this.prevGuessIsSuccessful = prevGuessIsSuccessful;
    }

    public SessionAggregate(final UUID id, final PCDTO puzzleConfigDto, final SessionStats stats) {
        super(id);
        Objects.nonNull(puzzleConfigDto);
        Objects.nonNull(stats);

        this.puzzleConfigDto = puzzleConfigDto;
        setStats(stats);

        setState(SessionStates.IN_PROGRESS);
        nextPuzzle();
    }

    protected PuzzlesFactory getPuzzlesFactory() {
        return DI.get(PuzzlesFactory.class);
    }

    protected void nextPuzzle() {
        P puzzle = getPuzzlesFactory().create(puzzleConfigDto.exercise);
        setPuzzle(puzzle);
        setNumberOfGuessesOfCurrentPuzzle(0);
        // TODO refactoring via domain event NewPuzzleDisplayed
        demonstrateHint();
    }

    public void guess(final Guess guess) {
        validateGuessing();
        var success = puzzle.guess(guess);
        if (success) {
            handleSuccessfulGuess();
        } else {
            handleWrongGuess();
        }
        setPrevGuessIsSuccessful(success);
    }

    protected void validateGuessing() {
        if (getPuzzlesCompleted() == puzzleConfigDto.targetNumberOfPuzzles) {
            throw new IllegalStateException("all puzzles are already guessed for this session");
        }
    }

    protected void handleSuccessfulGuess() {
        var newStats = isCorrectlyGuessedPuzzle() ? stats.incrementCorrectlyCompletedPuzzle() : stats.incrementCompletedPuzzle();
        setStats(newStats);

        if (isLastPuzzle()) {
            setState(SessionStates.COMPLETED);
            setPuzzle(null);
        } else {
            nextPuzzle();
        }
    }

    protected boolean isLastPuzzle() {
        return stats.puzzlesCompleted == puzzleConfigDto.targetNumberOfPuzzles;
    }

    protected boolean isCorrectlyGuessedPuzzle() {
        return numberOfGuessesOfCurrentPuzzle == 0;
    }

    protected void handleWrongGuess() {
        setNumberOfGuessesOfCurrentPuzzle(numberOfGuessesOfCurrentPuzzle + 1);
    }

    public void abort() {
        setState(SessionStates.ABORTED);
    }

    protected void demonstrateHint() {
        var hintDemonstrator = DI.get(HintDemonstrator.class);
        hintDemonstrator.demonstrateHint(puzzle.hint);
    }
}
