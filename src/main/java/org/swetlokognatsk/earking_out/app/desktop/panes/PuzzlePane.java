package org.swetlokognatsk.earking_out.app.desktop.panes;

import org.swetlokognatsk.earking_out.app.desktop.events.exercises.ExerciseFinishedEvent;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.Exercise;
import org.swetlokognatsk.earking_out.core.domain.model.hints.Hint;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.Puzzle;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.PuzzlesFactory;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.PuzzleConfigAggregate;
import org.swetlokognatsk.earking_out.core.domain.model.session.Session;
import org.swetlokognatsk.earking_out.core.domain.services.app.dto.puzzles.configs.PuzzleConfigDTO;
import org.swetlokognatsk.earking_out.core.domain.services.domain.puzzles.generators.SolutionGeneratorsFactory;
import org.swetlokognatsk.earking_out.core.ports.puzzles.SolutionGenerator;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

// TODO full revision to comply with srp and ddd principles
public abstract class PuzzlePane<E extends Exercise, PCDTO extends PuzzleConfigDTO<E>, H extends Hint, PG extends SolutionGenerator, P extends Puzzle<E, PCDTO, H, PG>> extends BorderPane {
    protected final Session<PCDTO> session;
    protected final PCDTO puzzleConfigDto;
    protected final PG solutionGenerator;
    protected P puzzle;

    protected final Pane puzzlePane;
    protected final ProgressBar puzzlesProgressBar;
    protected final Button finishButton;

    protected abstract Pane buildPuzzlePane();

    protected abstract void demonstrateNewHint();

    protected abstract void demonstrateHint();

    public PuzzlePane(final Session<PCDTO> session, double width, double height) {
        setWidth(width);
        setHeight(height);

        this.session = session;
        this.puzzleConfigDto = session.puzzleConfigDto();
        this.solutionGenerator = SolutionGeneratorsFactory.create(puzzleConfigDto);

        var puzzleProgressLabel = new Label(interpolatePuzzleProgress(0, puzzleConfigDto.targetNumberOfPuzzles));
        puzzlesProgressBar = new ProgressBar(0.0);
        var puzzlesProgress = new VBox(puzzleProgressLabel, puzzlesProgressBar);
        puzzlesProgress.setAlignment(Pos.CENTER);
        setTop(puzzlesProgress);

        puzzlePane = buildPuzzlePane();
        setCenter(puzzlePane);

        finishButton = new Button("finish");
        finishButton.setOnAction(this::finishExercise);
        // frontend hack to align button
        var finishButtonBox = new VBox(finishButton);
        finishButtonBox.setAlignment(Pos.CENTER);
        setBottom(finishButtonBox);
    }

    private static String interpolatePuzzleProgress(int numberOfPuzzles, int targetNumberOfPuzzles) {
        return String.format("%d of %d are guessed", numberOfPuzzles, targetNumberOfPuzzles);
    }

    protected void finishExercise(ActionEvent e) {
        var exerciseFinishedEvent = new ExerciseFinishedEvent(ExerciseFinishedEvent.EXERCISE_FINISHED, session);
        fireEvent(exerciseFinishedEvent);
    }

    protected void nextPuzzle() {
        createNextPuzzle();
        demonstrateNewHint();
    }

    protected void createNextPuzzle() {
        puzzle = (new PuzzlesFactory()).create(puzzleConfigDto, solutionGenerator);
    }

}
