package org.swetlokognatsk.earking_out.core.domain.model.session;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Objects;
import org.swetlokognatsk.earking_out.core.domain.model.base.AggregateRoot;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.Exercise;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.Puzzle;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.PuzzlesFactory;
import org.swetlokognatsk.earking_out.core.domain.model.solutions.Solution;
import org.swetlokognatsk.earking_out.core.domain.services.app.dto.puzzles.configs.PuzzleConfigDTO;
import org.swetlokognatsk.earking_out.core.ports.di.DI;

public abstract class SessionAggregate<E extends Exercise, S extends Solution, P extends Puzzle<E, S>, PCDTO extends PuzzleConfigDTO<E>> extends AggregateRoot<SessionId> {
    private static final long serialVersionUID = 1L;

    private transient PuzzlesFactory puzzlesFactory;

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

    public SessionAggregate(final PuzzlesFactory puzzlesFactory, final SessionId id, final PCDTO puzzleConfigDto, final SessionStats stats) {
        super(id);

        this.puzzlesFactory = Objects.requireNonNull(puzzlesFactory);

        Objects.requireNonNull(stats);

        this.puzzleConfigDto = Objects.requireNonNull(puzzleConfigDto);
        setStats(stats);

        setState(SessionStates.IN_PROGRESS);
        nextPuzzle();
    }

    protected void nextPuzzle() {
        P puzzle = puzzlesFactory.create(puzzleConfigDto.exercise);
        setPuzzle(puzzle);
        setNumberOfGuessesOfCurrentPuzzle(0);

        var newPuzzleEvent = getDomainEventsFactory().createNewpuzzleCreatedEvent(puzzle);
        addEvent(newPuzzleEvent);
    }

    public void guess(final S guess) {
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
        if (state != SessionStates.IN_PROGRESS) {
            throw new IllegalStateException("session is not in progress");
        }
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

    public void demonstrateHintAgain() {
        var puzzle = getPuzzle();
        var hearAgainEvent = getDomainEventsFactory().createHintRepeatingRequestedEvent(puzzle);
        addEvent(hearAgainEvent);
    }

    private void readObject(ObjectInputStream inputStream) throws IOException, ClassNotFoundException {
        inputStream.defaultReadObject();
        puzzlesFactory = DI.get(PuzzlesFactory.class);
    }
}
