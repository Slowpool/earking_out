package org.swetlokognatsk.earking_out.core.domain.model.puzzles;

import static org.junit.Assert.assertNotNull;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.Exercise;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.ExerciseNames;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.ExerciseTypes;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.ExercisesFactory;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.AudioPerfectPitchExercise;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.Puzzle;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.PuzzlesFactory;
import org.swetlokognatsk.earking_out.core.domain.model.solutions.Solution;
import org.swetlokognatsk.earking_out.core.domain.model.solutions.sound.SingleSoundSolution;
import org.swetlokognatsk.earking_out.core.domain.services.app.dto.puzzles.configs.PuzzleConfigDTOAssembler;
import org.swetlokognatsk.earking_out.core.ports.DI;
import org.swetlokognatsk.earking_out.core.ports.config.PuzzleConfigRepository;
import org.swetlokognatsk.earking_out.core.ports.puzzles.SolutionGenerator;
import org.swetlokognatsk.earking_out.inftrastructure.adapters.puzzles.generators.FakeSolutionGenerator;
import org.swetlokognatsk.earking_out.inftrastructure.adapters.puzzles.generators.perfectpitch.FakeAudioPerfectPitchSolutionGenerator;
import org.swetlokognatsk.earking_out.inftrastructure.adapters.puzzles.generators.perfectpitch.FakeVisualPerfectPitchSolutionGenerator;

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
            FakeAudioPerfectPitchSolutionGenerator.fakeSolution = (SingleSoundSolution) fakeSolution;
            break;
        default:
            throw new IllegalArgumentException("unknown exercise: " + exercise);
        }
        return createPuzzle(exercise);
    }
}
