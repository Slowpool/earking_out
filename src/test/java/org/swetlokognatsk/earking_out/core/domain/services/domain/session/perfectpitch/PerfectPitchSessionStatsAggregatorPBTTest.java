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
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.perfectpitch.AudioPerfectPitchPuzzle;
import org.swetlokognatsk.earking_out.core.domain.model.session.SessionId;
import org.swetlokognatsk.earking_out.core.domain.model.session.perfectpitch.PerfectPitchNoteStats;
import org.swetlokognatsk.earking_out.core.domain.model.session.perfectpitch.PerfectPitchSessionStats;
import org.swetlokognatsk.earking_out.core.domain.model.solutions.perfectpitch.AudioPerfectPitchSolution;
import org.swetlokognatsk.earking_out.core.ports.di.DI;
import net.jqwik.api.ForAll;
import net.jqwik.api.Property;
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
        List<DomainEvent> domainEvents = new LinkedList<>();
        var puzzleCreatedEvent = createNewPuzzleCreatedEvent();
        domainEvents.add(puzzleCreatedEvent);

        UserTriedToGuessPuzzleEvent guessEvent;
        for (var guess : guesses) {
            guessEvent = createUserTriedToGuessPuzzleEvent(guess);
        }
        // TODO
        return null;
    }

    NewPuzzleCreatedEvent createNewPuzzleCreatedEvent() {
        // TODO put possible solutions to puzzle config dto somehow
        var puzzle = puzzlesFactory.create(AUDIO_PERFECT_PITCH_EXERCISE);
        return eventsFactory.createNewPuzzleCreatedEvent(SESSION_ID, puzzle);
    }

    UserTriedToGuessPuzzleEvent createUserTriedToGuessPuzzleEvent(final Boolean guess) {
        eventsFactory.createUserTriedToGuessPuzzleEvent(SESSION_ID, , , );
    }

    PerfectPitchSessionStats<?> aggregate(final DomainEvent[] domainEvents) {
        var eventStream = new EventStream<SessionId>(SessionId.random(), domainEvents);
        return statsAggregator.aggregate(eventStream);
    }

    PerfectPitchSessionStats<?> generateStats(final List<PianoKeyNumber> possibleSolutions, final List<Boolean> guesses) {
        var domainEvents = buildDomainEventsTimeline(possibleSolutions, guesses);
        return aggregate(domainEvents);
    }

    @Property
    public void allNotesAreDistinct(@ForAll final List<PianoKeyNumber> possibleSolutions, @ForAll final List<Boolean> guesses) {
        var domainEvents = buildDomainEventsTimeline(possibleSolutions, guesses);

        var stats = aggregate(domainEvents);

        var distinctNotesStream = Arrays.stream(stats.notesStats)
                .map(noteStats -> noteStats.note)
                .distinct();
        assertEquals(stats.notesStats.length, distinctNotesStream.count());
    }

}
