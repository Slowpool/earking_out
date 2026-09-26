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
import static org.swetlokognatsk.earking_out.core.domain.model.exercises.ExercisesFactory.*;
import static org.swetlokognatsk.earking_out.core.domain.model.piano.key.PianoKeyNumber.*;

@Deprecated
public final class PerfectPitchSessionStatsAggregatorTest {

    private PerfectPitchSessionStatsAggregator<?> statsAggregator = DI.get(PerfectPitchSessionStatsAggregator.class);
    private DomainEventsFactory eventsFactory = DI.get(DomainEventsFactory.class);
    private PuzzlesFactory puzzlesFactory = DI.get(PuzzlesFactory.class);

    private PerfectPitchSessionStats<?> aggregate(DomainEvent... domainEvents) {
        return aggregate(SessionId.random(), domainEvents);
    }

    private PerfectPitchSessionStats<?> aggregate(final SessionId sessionId, final DomainEvent... domainEvents) {
        return statsAggregator.aggregate(new EventStream<>(sessionId, domainEvents));
    }

    private void assertNoteEquals(final Note expectedNote, final PerfectPitchNoteStats noteStats) {
        assertEquals(expectedNote, noteStats.note);
    }

    private void assertNumberOfAppearancesEquals(final int expectedNumberOfAppearances, final PerfectPitchNoteStats noteStats) {
        assertEquals(expectedNumberOfAppearances, noteStats.numberOfAppearances);
    }

    private void assertNumberOfAllGuessesEquals(final int expectedNumberOfAllGuesses, final PerfectPitchNoteStats noteStats) {
        assertEquals(expectedNumberOfAllGuesses, noteStats.numberOfAllGuesses);
    }

    private void assertNumberOfPerfectGuessesEquals(final int expectedNumberOfPerfectGuesses, final PerfectPitchNoteStats noteStats) {
        assertEquals(expectedNumberOfPerfectGuesses, noteStats.numberOfPerfectGuesses);
    }

    private void assertPerfectGuessesRatioEquals(final double expectedPerfectGuessesRatio, final PerfectPitchNoteStats noteStats) {
        assertEquals(expectedPerfectGuessesRatio, noteStats.perfectGuessesRatio, 0.01);
    }

    private NewPuzzleCreatedEvent createNewPuzzleCreatedEvent(final PianoKeyNumber keyNumber) {
        var solution = new AudioPerfectPitchSolution(keyNumber);
        var puzzle = new AudioPerfectPitchPuzzle(AUDIO_PERFECT_PITCH_EXERCISE, solution);
        return eventsFactory.createNewPuzzleCreatedEvent(SessionId.random(), puzzle);
    }

    @Test
    public void sessionId() {
        var sessionId = SessionId.random();

        var stats = aggregate(sessionId);

        assertEquals(sessionId, stats.sessionId);
    }

    @Test
    public void noEvents() {
        var stats = aggregate();

        assertEquals(0, stats.notesStats.length);
    }

    @Test
    public void noteForOneCreatedPuzzle() {
        var puzzleCreatedEvent = createNewPuzzleCreatedEvent(FIRST_NOTE_NUMBER);

        var stats = aggregate(puzzleCreatedEvent);

        assertEquals(1, stats.notesStats.length);
        assertNoteEquals(new Note(C, Accidentals.NATURAL, Octaves.FOURTH), stats.notesStats[0]);
    }

    @Test
    public void numberOfAppearancesForOneCreatedPuzzle() {
        var puzzleCreatedEvent = createNewPuzzleCreatedEvent(FIRST_NOTE_NUMBER);

        var stats = aggregate(puzzleCreatedEvent);

        assertEquals(1, stats.notesStats.length);
        assertNumberOfAppearancesEquals(1, stats.notesStats[0]);
    }

    @Test
    public void numberOfAllGuessesForOneCreatedPuzzle() {
        var puzzleCreatedEvent = createNewPuzzleCreatedEvent(FIRST_NOTE_NUMBER);

        var stats = aggregate(puzzleCreatedEvent);

        assertEquals(1, stats.notesStats.length);
        assertNumberOfAllGuessesEquals(0, stats.notesStats[0]);
    }

    @Test
    public void numberOfPerfectGuessesForOneCreatedPuzzle() {
        var puzzleCreatedEvent = createNewPuzzleCreatedEvent(FIRST_NOTE_NUMBER);

        var stats = aggregate(puzzleCreatedEvent);

        assertEquals(1, stats.notesStats.length);
        assertNumberOfPerfectGuessesEquals(0, stats.notesStats[0]);
    }

    @Test
    public void perfectGuessesRatioForOneCreatedPuzzle() {
        var puzzleCreatedEvent = createNewPuzzleCreatedEvent(FIRST_NOTE_NUMBER);

        var stats = aggregate(puzzleCreatedEvent);

        assertEquals(1, stats.notesStats.length);
        assertPerfectGuessesRatioEquals(0, stats.notesStats[0]);
    }

    @Test
    public void onePerfectGuess() {

    }

}
