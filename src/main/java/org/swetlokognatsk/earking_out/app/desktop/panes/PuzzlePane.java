package org.swetlokognatsk.earking_out.app.desktop.panes;

import java.util.UUID;
import org.swetlokognatsk.earking_out.app.desktop.events.exercises.ExerciseFinishedEvent;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.Exercise;
import org.swetlokognatsk.earking_out.core.domain.model.hints.Hint;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.Puzzle;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.PuzzlesFactory;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.PuzzleConfigAggregate;
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

public abstract class PuzzlePane<PCDTO extends PuzzleConfigDTO<?>> extends BorderPane {
    protected final UUID sessionId;

    protected final Pane puzzlePane;
    protected final ProgressBar puzzlesProgressBar;
    protected final Button finishButton;
    protected final Label puzzleProgressLabel;

    protected abstract Pane buildPuzzlePane(final PCDTO puzzleConfigDto);

    public PuzzlePane(final UUID sessionId, final PCDTO puzzleConfigDto, final double width, final double height) {
        this.sessionId = sessionId;

        setWidth(width);
        setHeight(height);

        puzzleProgressLabel = buildPuzzleProgressLabel(puzzleConfigDto);
        puzzlesProgressBar = new ProgressBar(0.0);
        var puzzlesProgress = new VBox(puzzleProgressLabel, puzzlesProgressBar);
        puzzlesProgress.setAlignment(Pos.CENTER);
        setTop(puzzlesProgress);

        puzzlePane = buildPuzzlePane(puzzleConfigDto);
        setCenter(puzzlePane);

        finishButton = new Button("finish");
        finishButton.setOnAction(this::finishExercise);
        // frontend hack to align button
        var finishButtonBox = new VBox(finishButton);
        finishButtonBox.setAlignment(Pos.CENTER);
        setBottom(finishButtonBox);

    }

    protected Label buildPuzzleProgressLabel(final PCDTO puzzleConfigDto) {
        var formattedCaption = interpolatePuzzleProgress(0, puzzleConfigDto.targetNumberOfPuzzles);
        return new Label(formattedCaption);
    }

    private static String interpolatePuzzleProgress(int numberOfPuzzles, int targetNumberOfPuzzles) {
        return String.format("%d of %d are guessed", numberOfPuzzles, targetNumberOfPuzzles);
    }

    protected void finishExercise(ActionEvent e) {
        var exerciseFinishedEvent = new ExerciseFinishedEvent(ExerciseFinishedEvent.EXERCISE_FINISHED, sessionId);
        fireEvent(exerciseFinishedEvent);
    }

    protected void nextPuzzle() {
        createNextPuzzle();
        demonstrateNewHint();
    }

}
