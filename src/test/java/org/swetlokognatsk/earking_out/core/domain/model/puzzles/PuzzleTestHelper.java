package org.swetlokognatsk.earking_out.core.domain.model.puzzles;

import static org.junit.Assert.*;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.Exercise;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.AudioPerfectPitchExercise;
import org.swetlokognatsk.earking_out.core.domain.model.solutions.Solution;
import org.swetlokognatsk.earking_out.core.domain.model.solutions.perfectpitch.AudioPerfectPitchSolution;
import org.swetlokognatsk.earking_out.core.domain.model.solutions.sound.PianoKeyNumberSolution;
import org.swetlokognatsk.earking_out.core.ports.config.PuzzleConfigRepository;
import org.swetlokognatsk.earking_out.core.ports.di.DI;
import org.swetlokognatsk.earking_out.inftrastructure.adapters.puzzles.generators.perfectpitch.FakeAudioPerfectPitchSolutionGenerator;

// TODO review the domain layer to make sure it does not contain a concepts the domain expert wouldn't understand
public final class PuzzleTestHelper {
    protected final PuzzleConfigRepository puzzleConfigRepository;
    protected final PuzzlesFactory puzzlesFactory;

    public PuzzleTestHelper(final PuzzleConfigRepository puzzleConfigRepository) {
        this.puzzleConfigRepository = puzzleConfigRepository;
        this.puzzlesFactory = DI.get(PuzzlesFactory.class);
    }

    public <E extends Exercise, P extends Puzzle<E, ?>> P createPuzzle(final E exercise) {
        return (P) puzzlesFactory.create(exercise);
    }

    public <E extends Exercise, S extends Solution, P extends Puzzle<E, S>> P createPuzzle(final E exercise, final S fakeSolution) {
        switch (exercise) {
        case AudioPerfectPitchExercise e:
            FakeAudioPerfectPitchSolutionGenerator.fakeSolution = (AudioPerfectPitchSolution) fakeSolution;
            break;
        default:
            throw new IllegalArgumentException("unknown exercise: " + exercise);
        }
        return createPuzzle(exercise);
    }
}
