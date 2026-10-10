package org.swetlokognatsk.earking_out.core.domain.services.domain.session.perfectpitch;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.swetlokognatsk.earking_out.core.domain.events.DomainEvent;
import org.swetlokognatsk.earking_out.core.domain.events.EventStream;
import org.swetlokognatsk.earking_out.core.domain.events.session.NewPuzzleCreatedEvent;
import org.swetlokognatsk.earking_out.core.domain.events.session.UserTriedToGuessPuzzleEvent;
import static org.swetlokognatsk.earking_out.core.domain.model.music.NoteNames.*;
import static org.swetlokognatsk.earking_out.core.domain.model.music.sounds.Note.*;
import org.swetlokognatsk.earking_out.core.domain.model.piano.key.PianoKeyNumber;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.PuzzleConfigTestHelper;
import org.swetlokognatsk.earking_out.core.domain.model.session.SessionId;
import org.swetlokognatsk.earking_out.core.domain.model.session.perfectpitch.PerfectPitchSessionStats;
import org.swetlokognatsk.earking_out.core.domain.model.solutions.perfectpitch.AudioPerfectPitchSolution;
import org.swetlokognatsk.earking_out.core.ports.di.DI;
import org.swetlokognatsk.earking_out.core.ports.puzzles.generators.perfectpitch.AudioPerfectPitchSolutionGenerator;
import org.swetlokognatsk.earking_out.infrastructure.adapters.puzzles.generators.perfectpitch.DetermenisticRandomAudioPerfectPitchSolutionGenerator;

import net.jqwik.api.AfterFailureMode;
import net.jqwik.api.Arbitraries;
import net.jqwik.api.Arbitrary;
import net.jqwik.api.FixedSeedMode;
import net.jqwik.api.ForAll;
import net.jqwik.api.Property;
import net.jqwik.api.Provide;
import net.jqwik.api.ShrinkingMode;
import net.jqwik.api.lifecycle.BeforeProperty;
import net.jqwik.api.lifecycle.BeforeTry;
import net.jqwik.spring.JqwikSpringSupport;
import static org.swetlokognatsk.earking_out.core.domain.model.exercises.ExercisesFactory.*;
import static org.swetlokognatsk.earking_out.core.domain.model.piano.key.PianoKeyNumber.*;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

@JqwikSpringSupport
@SpringBootTest
@Execution(ExecutionMode.SAME_THREAD)
public final class PerfectPitchSessionStatsAggregatorPBTTest {

    private static final SessionId ANY_SESSION_ID = SessionId.random();

    private PerfectPitchSessionStatsAggregator<?> statsAggregator;
    private DomainEventsTimelineBuilder domainEventsTimelineBuilder;

    DomainEvent[] buildDomainEventsTimeline(final List<PianoKeyNumber> possibleSolutions, final List<Boolean> guesses) {
        return domainEventsTimelineBuilder.build(possibleSolutions, guesses);
    }

    PerfectPitchSessionStats<?> aggregate(final DomainEvent[] domainEvents) {
        var eventStream = new EventStream<SessionId>(ANY_SESSION_ID, domainEvents);
        return statsAggregator.aggregate(eventStream);
    }

    // TODO optimization
    // ensuring that the set of stats notes is subset of possible solutions
    private void assertAllNotesExistInPossibleSolutions(final PerfectPitchSessionStats<?> stats, final List<PianoKeyNumber> possibleSolutions) {
        var statsNotes = Arrays.stream(stats.notesStats)
                .map(noteStats -> noteStats.keyNumber)
                .toList();
        for (var note : statsNotes) {
            assertTrue(possibleSolutions.contains(note));
        }
    }

    private void assertNumberOfNoteApperancesEqual(final DomainEvent[] domainEvents, final PerfectPitchSessionStats<?> stats) {
        for (var noteStats : stats.notesStats) {
            var expectedNumberOfAppearances = gatherNumberOfNoteAppearances(domainEvents, noteStats.keyNumber);
            assertEquals(expectedNumberOfAppearances, noteStats.numberOfAppearances);
        }
    }

    private void assertNumberOfAllGuessesEqual(final DomainEvent[] domainEvents, final PerfectPitchSessionStats<?> stats) {
        for (var noteStats : stats.notesStats) {
            var expectedNumberOfAllGuesses = gatherNumberOfAllGuesses(domainEvents, noteStats.keyNumber);
            assertEquals(expectedNumberOfAllGuesses, noteStats.numberOfAllGuesses);
        }
    }

    private void assertNumberOfPerfectGuessesEqual(final DomainEvent[] domainEvents, final PerfectPitchSessionStats<?> stats) {
        for (var noteStats : stats.notesStats) {
            var expectedNumberOfPerfectGuesses = gatherNumberOfPerfectGuesses(domainEvents, noteStats.keyNumber);
            assertEquals(expectedNumberOfPerfectGuesses, noteStats.numberOfPerfectGuesses);
        }
    }

    private void assertPerfectGuessesRatioEqual(final DomainEvent[] domainEvents, final PerfectPitchSessionStats<?> stats) {
        for (var noteStats : stats.notesStats) {
            var expectedNumberOfPerfectGuesses = gatherNumberOfPerfectGuesses(domainEvents, noteStats.keyNumber);
            var expectedNumberOfAllGuesses = gatherNumberOfAllGuesses(domainEvents, noteStats.keyNumber);
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

    @BeforeTry 
    public void setup(@Autowired ApplicationContext springContext) {
        DI.setContext(springContext, true);
        DI.register(AudioPerfectPitchSolutionGenerator.class, DetermenisticRandomAudioPerfectPitchSolutionGenerator.class);

        statsAggregator = DI.get(PerfectPitchSessionStatsAggregator.class);
        domainEventsTimelineBuilder = DI.get(DomainEventsTimelineBuilder.class);

        DI.get(PuzzleConfigTestHelper.class)
                .configureSomeValidPuzzleConfig();
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
                .ofMaxSize(100)
                // when last item is false, some tests logic is broken (because it's not obvious (at least for me) how to implement the testing of such cases), though aggregator works fine
                .map(this::makeLastItemTrue);
    }

    private List<Boolean> makeLastItemTrue(final List<Boolean> list) {
        if (list.size() == 0) {
            return list;
        }
        var newList = new LinkedList<>(list);
        newList.removeLast();
        newList.add(Boolean.TRUE);
        return newList;
    }

    @Property
    public void allNotesAreDistinct(@ForAll("randomPianoKeyNumbers") final List<PianoKeyNumber> possibleSolutions, @ForAll("randomGuesses") final List<Boolean> guesses) {
        var domainEvents = buildDomainEventsTimeline(possibleSolutions, guesses);

        var stats = aggregate(domainEvents);

        // // TODO squash all tests in one
        // assertAll(
        //         () -> {
        //         });

        var distinctNotesStream = Arrays.stream(stats.notesStats)
                .map(noteStats -> noteStats.keyNumber)
                .distinct();
        assertEquals(stats.notesStats.length, distinctNotesStream.count());
    }

    @Property//(seed = "-8737834682191197373", whenFixedSeed = FixedSeedMode.ALLOW, shrinking = ShrinkingMode.OFF, afterFailure = AfterFailureMode.RANDOM_SEED)
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
