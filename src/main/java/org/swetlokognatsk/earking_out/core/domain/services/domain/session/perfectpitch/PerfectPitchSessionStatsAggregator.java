package org.swetlokognatsk.earking_out.core.domain.services.domain.session.perfectpitch;

import static org.swetlokognatsk.earking_out.core.domain.model.piano.key.PianoKeyNumber.FIRST_NOTE_NUMBER;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.swetlokognatsk.earking_out.core.domain.events.DomainEvent;
import org.swetlokognatsk.earking_out.core.domain.events.EventStream;
import org.swetlokognatsk.earking_out.core.domain.events.session.NewPuzzleCreatedEvent;
import org.swetlokognatsk.earking_out.core.domain.events.session.UserTriedToGuessPuzzleEvent;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.PerfectPitchExercise;
import org.swetlokognatsk.earking_out.core.domain.model.piano.key.PianoKeyNumber;
import org.swetlokognatsk.earking_out.core.domain.model.session.SessionId;
import org.swetlokognatsk.earking_out.core.domain.model.session.perfectpitch.PerfectPitchNoteStats;
import org.swetlokognatsk.earking_out.core.domain.model.session.perfectpitch.PerfectPitchSessionStats;
import org.swetlokognatsk.earking_out.core.domain.model.solutions.perfectpitch.PerfectPitchSolution;
import org.swetlokognatsk.earking_out.core.domain.services.domain.session.ExtendedSessionStatsAggregator;

public final class PerfectPitchSessionStatsAggregator<E extends PerfectPitchExercise> extends ExtendedSessionStatsAggregator<E, PerfectPitchSessionStats<E>> {

    public PerfectPitchSessionStats<E> aggregate(final EventStream<SessionId> eventStream) {
        var notesStats = new HashMap<PianoKeyNumber, PerfectPitchNoteStats>();
        for (var event : eventStream) {
            apply(notesStats, event);
        }
        var notesStatsArray = notesStats.values()
                .toArray(PerfectPitchNoteStats[]::new);
        return new PerfectPitchSessionStats<E>(eventStream.id(), notesStatsArray);
    }

    private void apply(final Map<PianoKeyNumber, PerfectPitchNoteStats> notesStats, final DomainEvent domainEvent) {
        switch (domainEvent) {
        case UserTriedToGuessPuzzleEvent guessEvent:
            applyGuessEvent(notesStats, guessEvent);
            break;
        case NewPuzzleCreatedEvent newPuzzleEvent:
            applyNewPuzzleEvent(notesStats, newPuzzleEvent);
            break;
        default:
            break;
        }
    }

    private void applyGuessEvent(final Map<PianoKeyNumber, PerfectPitchNoteStats> notesStats, final UserTriedToGuessPuzzleEvent guessEvent) {
        var pianoKeyNumber = ((PerfectPitchSolution) guessEvent.guess).keyNumber;
        var oldNoteStats = getOrCreateNoteStats(notesStats, pianoKeyNumber);
        var newNoteStats = withGuessEvent(oldNoteStats, guessEvent);
        notesStats.put(pianoKeyNumber, newNoteStats);
    }

    private PerfectPitchNoteStats withGuessEvent(final PerfectPitchNoteStats noteStats, final UserTriedToGuessPuzzleEvent guessEvent) {
        // TODO encapsulate isPerfectGuess somewhere in static method, use it everywhere
        var newNumberOfPerfectGuesses = noteStats.numberOfPerfectGuesses;
        if (isPerfectGuess(guessEvent)) {
            newNumberOfPerfectGuesses += 1;
        }
        return new PerfectPitchNoteStats(noteStats.note, noteStats.numberOfAppearances, noteStats.numberOfAllGuesses + 1, newNumberOfPerfectGuesses);
    }

    private void applyNewPuzzleEvent(final Map<PianoKeyNumber, PerfectPitchNoteStats> notesStats, final NewPuzzleCreatedEvent newPuzzleEvent) {
        var pianoKeyNumber = ((PerfectPitchSolution) newPuzzleEvent.puzzle.solution).keyNumber;
        var oldNoteStats = getOrCreateNoteStats(notesStats, pianoKeyNumber);
        var newNoteStats = withNewPuzzleEvent(oldNoteStats, newPuzzleEvent);
        notesStats.put(pianoKeyNumber, newNoteStats);
    }

    private PerfectPitchNoteStats withNewPuzzleEvent(final PerfectPitchNoteStats noteStats, final NewPuzzleCreatedEvent newPuzzleEvent) {
        
    }

    private PerfectPitchNoteStats getOrCreateNoteStats(final Map<PianoKeyNumber, PerfectPitchNoteStats> notesStats, final PianoKeyNumber note) {
        var oldNoteStats = notesStats.get(note);
        return oldNoteStats == null
                ? new PerfectPitchNoteStats(note)
                : oldNoteStats;
    }

    private boolean isPerfectGuess(final UserTriedToGuessPuzzleEvent guessEvent) {
        return guessEvent.attempt == 1;
    }

}
