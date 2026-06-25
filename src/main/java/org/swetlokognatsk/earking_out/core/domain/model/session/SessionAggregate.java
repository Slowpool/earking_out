package org.swetlokognatsk.earking_out.core.domain.model.session;

import java.util.Objects;
import java.util.UUID;
import org.swetlokognatsk.earking_out.core.domain.model.Guess;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.Exercise;
import org.swetlokognatsk.earking_out.core.domain.model.hints.Hint;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.Puzzle;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.PuzzlesFactory;
import org.swetlokognatsk.earking_out.core.domain.services.app.dto.puzzles.configs.PuzzleConfigDTO;
import org.swetlokognatsk.earking_out.core.ports.DI;

public final class SessionAggregate<E extends Exercise, P extends Puzzle<E, ?>, PCDTO extends PuzzleConfigDTO<E>> {
    private final UUID id;
    private final PCDTO puzzleConfigDto;
    private SessionStats stats;

    private SessionStates state;
    private P puzzle;
    private boolean prevGuessIsSuccessful;
    private int numberOfGuessesOfCurrentPuzzle;

    private final PuzzlesFactory puzzlesFactory;

    public UUID getId() {
        return id;
    }

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
        return stats.puzzlesCompleted;
    }

    public P getPuzzle() {
        return puzzle;
    }

    protected void setPuzzle(final P puzzle) {
        this.puzzle = puzzle;
    }

    // // TODO seems awkward
    // public Hint getHint() {
    //     return puzzle.hint;
    // }

    public boolean getPrevGuessIsSuccessful() {
        // TODO return it back when PositiveNumber VO is used instead
        // if (stats.puzzlesCompleted.equals(Integer.valueOf(0))) {
        if (yetNoAnyGuessesInSession()) {
            throw new IllegalStateException("");
        }
        return prevGuessIsSuccessful;
    }

    protected boolean yetNoAnyGuessesInSession() {
        return stats.puzzlesCompleted == 0 && numberOfGuessesOfCurrentPuzzle == 0;
    }

    public int getNumberOfGuessesOfCurrentPuzzle() {
        return numberOfGuessesOfCurrentPuzzle;
    }

    protected void setNumberOfGuessesOfCurrentPuzzle(final int numberOfGuessesOfCurrentPuzzle) {
        this.numberOfGuessesOfCurrentPuzzle = numberOfGuessesOfCurrentPuzzle;
    }

    protected void setPrevGuessIsSuccessful(final boolean prevGuessIsSuccessful) {
        this.prevGuessIsSuccessful = prevGuessIsSuccessful;
    }

    public SessionAggregate(final UUID id, final PCDTO puzzleConfigDto, final SessionStats stats) {
        Objects.nonNull(puzzleConfigDto);
        Objects.nonNull(stats);

        this.id = id;
        this.puzzleConfigDto = puzzleConfigDto;
        setStats(stats);

        setState(SessionStates.IN_PROGRESS);
        puzzlesFactory = DI.get(PuzzlesFactory.class);
        nextPuzzle();
    }

    protected void nextPuzzle() {
        setPuzzle(puzzlesFactory.create(puzzleConfigDto.exercise));
        setNumberOfGuessesOfCurrentPuzzle(0);
    }

    public void guess(final Guess guess) {
        var success = puzzle.guess(guess);
        if (success) {
            handleSuccessfulGuess();
        } else {
            handleWrongGuess();
        }
        setPrevGuessIsSuccessful(success);
    }

    protected void handleSuccessfulGuess() {
        var newStats = isCorrectlyGuessedPuzzle() ? stats.incrementCorrectlyCompletedPuzzle() : stats.incrementCompletedPuzzle();
        setStats(newStats);

        if (stats.puzzlesCompleted == puzzleConfigDto.targetNumberOfPuzzles) {
            setState(SessionStates.COMPLETED);
        }

    }

    protected boolean isCorrectlyGuessedPuzzle() {
        return numberOfGuessesOfCurrentPuzzle == 0;
    }

    protected void handleWrongGuess() {
        setNumberOfGuessesOfCurrentPuzzle(numberOfGuessesOfCurrentPuzzle + 1);
    }

}
