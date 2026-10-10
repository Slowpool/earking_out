package org.swetlokognatsk.earking_out.core.domain.services.domain.session.perfectpitch;

import org.swetlokognatsk.earking_out.core.domain.events.DomainEvent;
import org.swetlokognatsk.earking_out.core.domain.model.piano.key.PianoKeyNumber;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.perfectpitch.PerfectPitchConfigAggregate;
import org.swetlokognatsk.earking_out.core.domain.model.session.factories.SessionAggregatesFactory;
import org.swetlokognatsk.earking_out.core.domain.model.session.perfectpitch.AudioPerfectPitchSessionAggregate;
import org.swetlokognatsk.earking_out.core.domain.model.solutions.perfectpitch.AudioPerfectPitchSolution;
import org.swetlokognatsk.earking_out.core.ports.config.PuzzleConfigRepository;
import org.swetlokognatsk.earking_out.infrastructure.annotations.TestComponent;
import lombok.AllArgsConstructor;
import static org.swetlokognatsk.earking_out.core.domain.model.exercises.ExercisesFactory.*;
import static org.swetlokognatsk.earking_out.core.domain.model.piano.key.PianoKeyNumber.*;
import java.util.List;

@AllArgsConstructor
@TestComponent
public class DomainEventsTimelineBuilder {
    private final SessionAggregatesFactory sessionAggregatesFactory;
    private final PuzzleConfigRepository puzzleConfigRepository;

    public DomainEvent[] build(final List<PianoKeyNumber> possibleSolutions, final List<Boolean> guesses) {
        // TODO figure out the cause of quirk with f10 acting like f5 in this method
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
        var puzzleConfig = puzzleConfigRepository.getPuzzleConfig(AUDIO_PERFECT_PITCH_EXERCISE);
        puzzleConfig.updateProperty(PerfectPitchConfigAggregate.NORMALIZED_NOTES_FOR_PUZZLE_PROP, possibleSolutions.toArray(PianoKeyNumber[]::new));
        puzzleConfigRepository.save(puzzleConfig);
    }

    private void setMaxNumberOfPuzzles() {
        var puzzleConfig = puzzleConfigRepository.getPuzzleConfig(AUDIO_PERFECT_PITCH_EXERCISE);
        puzzleConfig.updateProperty(PerfectPitchConfigAggregate.TARGET_NUMBER_OF_PUZZLES_PROP, Integer.MAX_VALUE);
        puzzleConfigRepository.save(puzzleConfig);
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