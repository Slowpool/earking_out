package org.swetlokognatsk.earking_out.core.domain.services.domain.session.perfectpitch;

import static org.junit.Assert.*;
import org.junit.*;
import org.junit.Assert.*;
import org.swetlokognatsk.earking_out.core.domain.events.DomainEvent;
import org.swetlokognatsk.earking_out.core.domain.events.DomainEventsFactory;
import org.swetlokognatsk.earking_out.core.domain.events.EventStream;
import org.swetlokognatsk.earking_out.core.domain.events.session.NewPuzzleCreatedEvent;
import org.swetlokognatsk.earking_out.core.domain.events.session.UserTriedToGuessPuzzleEvent;
import org.swetlokognatsk.earking_out.core.domain.helpers.SessionRepositoryDelegator;
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

    private static final SessionId SESSION_ID = SessionId.random();

    private PerfectPitchSessionStatsAggregator<?> statsAggregator = DI.get(PerfectPitchSessionStatsAggregator.class);
    private DomainEventsFactory eventsFactory = DI.get(DomainEventsFactory.class);
    private PuzzlesFactory puzzlesFactory = DI.get(PuzzlesFactory.class);

    DomainEvent[] buildDomainEventsTimeline(final List<PianoKeyNumber> possibleSolutions, final List<Boolean> guesses) {
        var domainEventsBuilder = new DomainEventsTimelineBuilder();
        return domainEventsBuilder.build(possibleSolutions, guesses);
    }

    PerfectPitchSessionStats<?> aggregate(final DomainEvent[] domainEvents) {
        var eventStream = new EventStream<SessionId>(SESSION_ID, domainEvents);
        return statsAggregator.aggregate(eventStream);
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
    
    @Property
    public void allNotesAreDistinct(@ForAll("randomPianoKeyNumbers") final List<PianoKeyNumber> possibleSolutions, @ForAll @Size(min = 0, max = 500) final List<Boolean> guesses) {
        var domainEvents = buildDomainEventsTimeline(possibleSolutions, guesses);

        var stats = aggregate(domainEvents);

        var distinctNotesStream = Arrays.stream(stats.notesStats)
                .map(noteStats -> noteStats.note)
                .distinct();
        assertEquals(stats.notesStats.length, distinctNotesStream.count());
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