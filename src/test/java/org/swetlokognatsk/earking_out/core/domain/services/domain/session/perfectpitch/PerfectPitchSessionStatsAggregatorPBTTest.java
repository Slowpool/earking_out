package org.swetlokognatsk.earking_out.core.domain.services.domain.session.perfectpitch;

import static org.junit.Assert.*;
import org.junit.*;
import org.junit.Assert.*;
import org.swetlokognatsk.earking_out.core.domain.events.DomainEvent;
import org.swetlokognatsk.earking_out.core.domain.events.DomainEventsFactory;
import org.swetlokognatsk.earking_out.core.domain.events.EventStream;
import org.swetlokognatsk.earking_out.core.domain.events.session.NewPuzzleCreatedEvent;
import org.swetlokognatsk.earking_out.core.domain.events.session.UserTriedToGuessPuzzleEvent;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.AudioPerfectPitchExercise;
import static org.swetlokognatsk.earking_out.core.domain.model.music.NoteNames.*;
import static org.swetlokognatsk.earking_out.core.domain.model.music.sounds.Note.*;
import org.swetlokognatsk.earking_out.core.domain.model.music.Accidentals;
import org.swetlokognatsk.earking_out.core.domain.model.music.Octaves;
import org.swetlokognatsk.earking_out.core.domain.model.music.sounds.Note;
import org.swetlokognatsk.earking_out.core.domain.model.piano.key.PianoKeyNumber;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.PuzzlesFactory;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.perfectpitch.PerfectPitchConfigAggregate;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.perfectpitch.AudioPerfectPitchPuzzle;
import org.swetlokognatsk.earking_out.core.domain.model.session.SessionId;
import org.swetlokognatsk.earking_out.core.domain.model.session.factories.SessionAggregatesFactory;
import org.swetlokognatsk.earking_out.core.domain.model.session.perfectpitch.AudioPerfectPitchSessionAggregate;
import org.swetlokognatsk.earking_out.core.domain.model.session.perfectpitch.PerfectPitchNoteStats;
import org.swetlokognatsk.earking_out.core.domain.model.session.perfectpitch.PerfectPitchSessionStats;
import org.swetlokognatsk.earking_out.core.domain.model.solutions.perfectpitch.AudioPerfectPitchSolution;
import org.swetlokognatsk.earking_out.core.ports.config.PuzzleConfigRepository;
import org.swetlokognatsk.earking_out.core.ports.di.DI;
import org.swetlokognatsk.earking_out.core.ports.session.perfectpitch.AudioPerfectPitchSessionRepository;
import net.jqwik.api.Arbitraries;
import net.jqwik.api.Arbitrary;
import net.jqwik.api.ForAll;
import net.jqwik.api.Property;
import net.jqwik.api.Provide;
import net.jqwik.api.constraints.Size;
import net.jqwik.api.state.Action;
import static org.swetlokognatsk.earking_out.core.domain.model.exercises.ExercisesFactory.*;
import static org.swetlokognatsk.earking_out.core.domain.model.piano.key.PianoKeyNumber.*;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public final class PerfectPitchSessionStatsAggregatorPBTTest {

    private static final SessionId ANY_SESSION_ID = SessionId.random();

    private PerfectPitchSessionStatsAggregator<?> statsAggregator = DI.get(PerfectPitchSessionStatsAggregator.class);

    DomainEvent[] buildDomainEventsTimeline(final List<PianoKeyNumber> possibleSolutions, final List<Boolean> guesses) {
        var domainEventsBuilder = new DomainEventsTimelineBuilder();
        return domainEventsBuilder.build(possibleSolutions, guesses);
    }

    PerfectPitchSessionStats<?> aggregate(final DomainEvent[] domainEvents) {
        var eventStream = new EventStream<SessionId>(ANY_SESSION_ID, domainEvents);
        return statsAggregator.aggregate(eventStream);
    }

    // TODO optimization
    // ensuring that the set of stats notes is subset of possible solutions
    private void assertAllNotesExistInPossibleSolutions(final PerfectPitchSessionStats<?> stats, final List<PianoKeyNumber> possibleSolutions) {
        var statsNotes = Arrays.stream(stats.notesStats)
                .map(noteStats -> noteStats.note)
                .toList();
        for (var note : statsNotes) {
            assertTrue(possibleSolutions.contains(note));
        }
    }

    private void assertNumberOfNoteApperancesEqual(final DomainEvent[] domainEvents, final PerfectPitchSessionStats<?> stats) {
        for (var noteStats : stats.notesStats) {
            var expectedNumberOfAppearances = gatherNumberOfNoteAppearances(domainEvents, noteStats.note);
            assertEquals(expectedNumberOfAppearances, noteStats.numberOfAppearances);
        }
    }

    private void assertNumberOfAllGuessesEqual(final DomainEvent[] domainEvents, final PerfectPitchSessionStats<?> stats) {
        for (var noteStats : stats.notesStats) {
            var expectedNumberOfAllGuesses = gatherNumberOfAllGuesses(domainEvents, noteStats.note);
            assertEquals(expectedNumberOfAllGuesses, noteStats.numberOfAllGuesses);
        }
    }

    private void assertNumberOfPerfectGuessesEqual(final DomainEvent[] domainEvents, final PerfectPitchSessionStats<?> stats) {
        for (var noteStats : stats.notesStats) {
            var expectedNumberOfPerfectGuesses = gatherNumberOfPerfectGuesses(domainEvents, noteStats.note);
            assertEquals(expectedNumberOfPerfectGuesses, noteStats.numberOfPerfectGuesses);
        }
    }

    private void assertPerfectGuessesRatioEqual(final DomainEvent[] domainEvents, final PerfectPitchSessionStats<?> stats) {
        for (var noteStats : stats.notesStats) {
            var expectedNumberOfPerfectGuesses = gatherNumberOfPerfectGuesses(domainEvents, noteStats.note);
            var expectedNumberOfAllGuesses = gatherNumberOfAllGuesses(domainEvents, noteStats.note);
            var expectedPerfectGuessesRatio = expectedNumberOfAllGuesses == 0.0
                ? 0.0
                : ((double) expectedNumberOfPerfectGuesses) / expectedNumberOfAllGuesses;
            assertEquals(expectedPerfectGuessesRatio, noteStats.perfectGuessesRatio, 0.01);
        }
    }

    // TODO optimization of all gather*() methods
    private long gatherNumberOfNoteAppearances(final DomainEvent[] domainEvents, final PianoKeyNumber note) {
        return Arrays.stream(domainEvents)
                .filter(event -> {
                    if (event instanceof NewPuzzleCreatedEvent newPuzzleCreatedEvent) {
                        return newPuzzleCreatedEvent.puzzle.solution.equals(new AudioPerfectPitchSolution(note));
                    } else {
                        return false;
                    }
                })
                .count();
    }

    private long gatherNumberOfAllGuesses(final DomainEvent[] domainEvents, final PianoKeyNumber note) {
        // this approach ignores the ending series of guesses unclosed with successful guess, like this:
        // successful guess(attempt 1), wrong guess (attempt 1), wrong guess (attempt 2) - this series would return `1` from the single successful guess
        // but this series:
        // successful guess(attempt 1), wrong guess (attempt 1), wrong guess (attempt 2), successful guess (attempt 3)
        // would return `4` (1 + 3 from successful guesses)
        return Arrays.stream(domainEvents)
                .filter(event -> {
                    if (event instanceof UserTriedToGuessPuzzleEvent guessEvent) {
                        return guessEvent.guess.equals(new AudioPerfectPitchSolution(note))
                                && guessEvent.success;
                    } else {
                        return false;
                    }
                })
                .mapToInt(domainEvent -> ((UserTriedToGuessPuzzleEvent) domainEvent).attempt)
                .sum();
    }

    private long gatherNumberOfPerfectGuesses(final DomainEvent[] domainEvents, final PianoKeyNumber note) {
        return Arrays.stream(domainEvents)
                .filter(event -> {
                    if (event instanceof UserTriedToGuessPuzzleEvent guessEvent) {
                        return guessEvent.guess.equals(new AudioPerfectPitchSolution(note))
                                && guessEvent.success
                                && isPerfectGuess(guessEvent);
                    } else {
                        return false;
                    }
                })
                .count();
    }

    private boolean isPerfectGuess(final UserTriedToGuessPuzzleEvent guessEvent) {
        return guessEvent.attempt == 1;
    }

    @Provide
    Arbitrary<List<PianoKeyNumber>> randomPianoKeyNumbers() {
        var allPianoKeyNumbers = PianoKeyNumber.getAll();
        return Arbitraries.of(allPianoKeyNumbers)
                .list()
                .uniqueElements()
                .ofMinSize(1)
                .ofMaxSize(allPianoKeyNumbers.length);
    }

    @Provide
    Arbitrary<List<Boolean>> randomGuesses() {
        return Arbitraries.of(Boolean.TRUE, Boolean.FALSE)
                .list()
                .ofMinSize(0)
                .ofMaxSize(100);
    }

    @Property
    public void allNotesAreDistinct(@ForAll("randomPianoKeyNumbers") final List<PianoKeyNumber> possibleSolutions, @ForAll("randomGuesses") final List<Boolean> guesses) {
        var domainEvents = buildDomainEventsTimeline(possibleSolutions, guesses);

        var stats = aggregate(domainEvents);

        var distinctNotesStream = Arrays.stream(stats.notesStats)
                .map(noteStats -> noteStats.note)
                .distinct();
        assertEquals(stats.notesStats.length, distinctNotesStream.count());
    }

    @Property
    public void allNotesAreFromPossibleSolutions(@ForAll("randomPianoKeyNumbers") final List<PianoKeyNumber> possibleSolutions, @ForAll("randomGuesses") final List<Boolean> guesses) {
        var domainEvents = buildDomainEventsTimeline(possibleSolutions, guesses);

        var stats = aggregate(domainEvents);

        assertTrue(stats.notesStats.length <= possibleSolutions.size());
        assertAllNotesExistInPossibleSolutions(stats, possibleSolutions);
    }

    @Property
    public void numberOfNoteAppearances(@ForAll("randomPianoKeyNumbers") final List<PianoKeyNumber> possibleSolutions, @ForAll("randomGuesses") final List<Boolean> guesses) {
        var domainEvents = buildDomainEventsTimeline(possibleSolutions, guesses);

        var stats = aggregate(domainEvents);

        assertNumberOfNoteApperancesEqual(domainEvents, stats);
    }

    @Property
    public void numberOfEachNoteAllGuesses(@ForAll("randomPianoKeyNumbers") final List<PianoKeyNumber> possibleSolutions, @ForAll("randomGuesses") final List<Boolean> guesses) {
        var domainEvents = buildDomainEventsTimeline(possibleSolutions, guesses);

        var stats = aggregate(domainEvents);

        assertNumberOfAllGuessesEqual(domainEvents, stats);
    }

    @Property
    public void numberOfNotePerfectGuesses(@ForAll("randomPianoKeyNumbers") final List<PianoKeyNumber> possibleSolutions, @ForAll("randomGuesses") final List<Boolean> guesses) {
        var domainEvents = buildDomainEventsTimeline(possibleSolutions, guesses);

        var stats = aggregate(domainEvents);

        assertNumberOfPerfectGuessesEqual(domainEvents, stats);
    }

    @Property
    public void perfectGuessesRatio(@ForAll("randomPianoKeyNumbers") final List<PianoKeyNumber> possibleSolutions, @ForAll("randomGuesses") final List<Boolean> guesses) {
        var domainEvents = buildDomainEventsTimeline(possibleSolutions, guesses);

        var stats = aggregate(domainEvents);

        assertPerfectGuessesRatioEqual(domainEvents, stats);
    }

}

class DomainEventsTimelineBuilder {
    private final SessionAggregatesFactory sessionAggregatesFactory = DI.get(SessionAggregatesFactory.class);
    private final PuzzleConfigRepository puzzleConfigRepository = DI.get(PuzzleConfigRepository.class);

    DomainEvent[] build(final List<PianoKeyNumber> possibleSolutions, final List<Boolean> guesses) {
        setPossibleSolutionsToConfig(possibleSolutions);
        // to avoid premature ending of session. upper boundary does not matter, session.abort() can be done at any moment, and it does not affect the stats
        setMaxNumberOfPuzzles();
        AudioPerfectPitchSessionAggregate sessionAggregate = sessionAggregatesFactory.create(AUDIO_PERFECT_PITCH_EXERCISE);

        for (var guess : guesses) {
            makeGuess(sessionAggregate, guess);
        }

        return sessionAggregate.flushEvents()
                .toArray(DomainEvent[]::new);
    }

    private void setPossibleSolutionsToConfig(final List<PianoKeyNumber> possibleSolutions) {
        var puzzleConfig = puzzleConfigRepository.genericGet(AUDIO_PERFECT_PITCH_EXERCISE);
        puzzleConfig.updateProperty(PerfectPitchConfigAggregate.NORMALIZED_NOTES_FOR_PUZZLE_PROP, possibleSolutions.toArray(PianoKeyNumber[]::new));
        puzzleConfigRepository.genericSave(puzzleConfig);
    }

    private void setMaxNumberOfPuzzles() {
        var puzzleConfig = puzzleConfigRepository.genericGet(AUDIO_PERFECT_PITCH_EXERCISE);
        puzzleConfig.updateProperty(PerfectPitchConfigAggregate.TARGET_NUMBER_OF_PUZZLES_PROP, Integer.MAX_VALUE);
        puzzleConfigRepository.genericSave(puzzleConfig);
    }

    private void makeGuess(final AudioPerfectPitchSessionAggregate sessionAggregate, final Boolean mustBeSuccessful) {
        var solution = getSolution(sessionAggregate);
        if (mustBeSuccessful.booleanValue()) {
            sessionAggregate.guess(solution);
        } else {
            var wrongSolution = getAnotherSolutionThan(solution);
            sessionAggregate.guess(wrongSolution);
        }
    }

    private AudioPerfectPitchSolution getSolution(final AudioPerfectPitchSessionAggregate sessionAggregate) {
        return sessionAggregate.getPuzzle().solution;
    }

    private AudioPerfectPitchSolution getAnotherSolutionThan(final AudioPerfectPitchSolution solution) {
        var anotherKeyNumber = solution.equals(new AudioPerfectPitchSolution(FIRST_NOTE_NUMBER))
                // one of them must be wrong
                ? LAST_NOTE_NUMBER
                : FIRST_NOTE_NUMBER;
        return new AudioPerfectPitchSolution(anotherKeyNumber);
    }
}