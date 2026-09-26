package org.swetlokognatsk.earking_out.core.domain.services.domain.session.perfectpitch;

import static org.junit.Assert.*;
import org.junit.*;
import org.junit.Assert.*;
import org.swetlokognatsk.earking_out.core.domain.events.DomainEvent;
import org.swetlokognatsk.earking_out.core.domain.events.DomainEventsFactory;
import org.swetlokognatsk.earking_out.core.domain.events.EventStream;
import org.swetlokognatsk.earking_out.core.domain.events.session.NewPuzzleCreatedEvent;
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
import java.util.LinkedList;
import java.util.List;

public final class PerfectPitchSessionStatsAggregatorPBTTest {

    private PerfectPitchSessionStatsAggregator<?> statsAggregator = DI.get(PerfectPitchSessionStatsAggregator.class);
    private DomainEventsFactory eventsFactory = DI.get(DomainEventsFactory.class);
    private PuzzlesFactory puzzlesFactory = DI.get(PuzzlesFactory.class);

    @Property
    public void test(@ForAll int number) {
        if (number == Integer.MIN_VALUE) {
            return;
        }
        assertTrue(Math.abs(number) >= 0);
    }

}

class SessionState {
    boolean puzzleIsActive = false;
    // init fake data
    boolean puzzleIsGuessed = true;
    private List<DomainEvent> domainEvents = new LinkedList<>();

    void addDomainEvent(final DomainEvent domainEvent) {
        domainEvents.add(domainEvent);
    }

    DomainEvent[] getDomainEvents() {
        return domainEvents.toArray(DomainEvent[]::new);
    }

    void goToSuccessfulGuessState() {
        puzzleIsActive = false;
        puzzleIsGuessed = true;
    }

    void goToNewPuzzleCreatedState() {
        puzzleIsActive = true;
        puzzleIsGuessed = false;
    }
}

class CreateNewPuzzleEventAction implements Action<SessionState> {

    public boolean precondition(final SessionState state) {
        return state.puzzleIsGuessed;
    }

    public SessionState run(final SessionState state) {
        // TODO so, where is the new event generated?

        state.goToNewPuzzleCreatedState();
        return state;
    }
}

class CreateUserTriedToGuessPuzzleEventAction implements Action<SessionState> {

    public boolean precondition(final SessionState state) {
        return state.puzzleIsActive;
    }

    public SessionState run(final SessionState state) {
        // TODO so, where is the new event generated?

        state.goToSuccessfulGuessState();
        return state;
    }
}