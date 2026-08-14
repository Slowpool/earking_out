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
import org.swetlokognatsk.earking_out.core.domain.services.app.dto.session.SessionDTO;
import org.swetlokognatsk.earking_out.core.domain.services.app.dto.session.SessionDTOAssembler;
import org.swetlokognatsk.earking_out.core.ports.di.DI;

// TODO use event sourcing for this aggregate?
public abstract class SessionAggregate<E extends Exercise, S extends Solution, P extends Puzzle<E, S>, PCDTO extends PuzzleConfigDTO<E>> extends AggregateRoot<SessionId> {
    private static final long serialVersionUID = 1L;

    private transient PuzzlesFactory puzzlesFactory;

    private final PCDTO puzzleConfigDto;
    private SessionStats stats;

    private SessionStates state;
    private P puzzle;
    private boolean prevGuessIsSuccessful;
    private int numberOfGuessesOfCurrentPuzzle;

    public final PCDTO getPuzzleConfig() {
        return puzzleConfigDto;
    }

    public final SessionStats getStats() {
        return stats;
    }

    protected final void setStats(final SessionStats stats) {
        this.stats = stats;
    }

    public final SessionStates getState() {
        return state;
    }

    public final boolean isInProgress() {
        return getState() == SessionStates.IN_PROGRESS;
    }

    protected final void setState(final SessionStates state) {
        this.state = state;
    }

    public final int getPuzzlesCompleted() {
        return getStats().puzzlesCompleted;
    }

    public final int getPuzzlesCompletedPerfectly() {
        return getStats().puzzlesCompletedPerfectly;
    }

    public final P getPuzzle() {
        if (getState() != SessionStates.IN_PROGRESS) {
            throw new IllegalStateException("session cannot have a puzzle if it is not in progress");
        }
        return puzzle;
    }

    protected final void setPuzzle(final P puzzle) {
        this.puzzle = puzzle;
    }

    public final boolean getPrevGuessIsSuccessful() {
        // TODO return it back when PositiveNumber VO is used instead
        // if (stats.puzzlesCompleted.equals(Integer.valueOf(0))) {
        if (thereAreNoAnyGuessesInSession()) {
            throw new IllegalStateException("");
        }
        return prevGuessIsSuccessful;
    }

    protected final boolean thereAreNoAnyGuessesInSession() {
        return getPuzzlesCompleted() == 0 && getNumberOfGuessesOfCurrentPuzzle() == 0;
    }

    protected final void setPrevGuessIsSuccessful(final boolean prevGuessIsSuccessful) {
        this.prevGuessIsSuccessful = prevGuessIsSuccessful;
    }

    public final int getNumberOfGuessesOfCurrentPuzzle() {
        try {
            getPuzzle();
        } catch (IllegalStateException e) {
            throw new IllegalStateException("puzzle guessing is already finished and there's no current puzzle");
        }
        return numberOfGuessesOfCurrentPuzzle;
    }

    protected final void setNumberOfGuessesOfCurrentPuzzle(final int numberOfGuessesOfCurrentPuzzle) {
        this.numberOfGuessesOfCurrentPuzzle = numberOfGuessesOfCurrentPuzzle;
    }

    public SessionAggregate(final PuzzlesFactory puzzlesFactory, final SessionId id, final PCDTO puzzleConfigDto, final SessionStats stats) {
        super(id);

        this.puzzlesFactory = Objects.requireNonNull(puzzlesFactory);

        Objects.requireNonNull(stats);

        this.puzzleConfigDto = Objects.requireNonNull(puzzleConfigDto);
        setStats(stats);

        setState(SessionStates.IN_PROGRESS);
        addSessionStartedEvent();
        nextPuzzle();
    }

    private void addSessionStartedEvent() {
        var event = getDomainEventsFactory().createSessionStartedEvent(assembleOwnDto());
        addEvent(event);
    }

    protected final void nextPuzzle() {
        P puzzle = puzzlesFactory.create(getPuzzleConfig().exercise);
        setPuzzle(puzzle);
        setNumberOfGuessesOfCurrentPuzzle(0);

        addNewPuzzleCreatedEvent(puzzle);
    }

    private void addNewPuzzleCreatedEvent(final P puzzle) {
        var newPuzzleEvent = getDomainEventsFactory().createNewPuzzleCreatedEvent(assembleOwnDto(), puzzle);
        addEvent(newPuzzleEvent);
    }

    private SessionDTO<E, P, PCDTO, ?> assembleOwnDto() {
        return SessionDTOAssembler.assemble(this);
    }

    public final void guess(final S guess) {
        validateGuessing();

        var success = getPuzzle().guess(guess);
        incrementNumberOfGuessesOfCurrentPuzzle();

        if (success) {
            handleSuccessfulGuess(guess);
        } else {
            handleWrongGuess(guess);
        }

        setPrevGuessIsSuccessful(success);
    }

    private void addUserTriedToGuessPuzzleEvent(final int puzzleNumber, final S guess, final int attempt, final boolean success) {
        var event = getDomainEventsFactory().createUserTriedToGuessPuzzleEvent(assembleOwnDto(), puzzleNumber, guess, attempt, success);
        addEvent(event);
    }

    protected void validateGuessing() {
        if (getState() != SessionStates.IN_PROGRESS) {
            throw new IllegalStateException("session is not in progress");
        }
        if (getPuzzlesCompleted() == getPuzzleConfig().targetNumberOfPuzzles) {
            throw new IllegalStateException("all puzzles are already guessed for this session");
        }
    }

    protected void handleSuccessfulGuess(final S guess) {
        var newStats = isPerfectlyGuessedPuzzle() ? getStats().incrementPerfectlyCompletedPuzzles() : getStats().incrementCompletedPuzzles();
        setStats(newStats);

        addUserTriedToGuessPuzzleEvent(getPuzzlesCompleted(), guess, getNumberOfGuessesOfCurrentPuzzle(), true);

        if (isLastPuzzle()) {
            setState(SessionStates.COMPLETED);
            addSessionFinishedEvent();
            setPuzzle(null);
        } else {
            nextPuzzle();
        }
    }

    protected final boolean isPerfectlyGuessedPuzzle() {
        return getNumberOfGuessesOfCurrentPuzzle() == 1;
    }

    protected final boolean isLastPuzzle() {
        return getPuzzlesCompleted() == getPuzzleConfig().targetNumberOfPuzzles;
    }

    private void addSessionFinishedEvent() {
        var event = getDomainEventsFactory().createSessionFinishedEvent(assembleOwnDto());
        addEvent(event);
    }

    protected final void incrementNumberOfGuessesOfCurrentPuzzle() {
        setNumberOfGuessesOfCurrentPuzzle(getNumberOfGuessesOfCurrentPuzzle() + 1);
    }

    protected void handleWrongGuess(final S guess) {
        addUserTriedToGuessPuzzleEvent(getPuzzlesCompleted() + 1, guess, getNumberOfGuessesOfCurrentPuzzle(), false);
    }

    public final void abort() {
        setState(SessionStates.ABORTED);
    }

    public final void demonstrateHintAgain() {
        addHintRepeatingRequestedEvent();
    }

    private void addHintRepeatingRequestedEvent() {
        var hearAgainEvent = getDomainEventsFactory().createHintRepeatingRequestedEvent(assembleOwnDto(), getPuzzle());
        addEvent(hearAgainEvent);
    }

    private void readObject(ObjectInputStream inputStream) throws IOException, ClassNotFoundException {
        inputStream.defaultReadObject();
        puzzlesFactory = DI.get(PuzzlesFactory.class);
    }
}
