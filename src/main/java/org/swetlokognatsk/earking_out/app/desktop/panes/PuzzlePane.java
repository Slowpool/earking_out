package org.swetlokognatsk.earking_out.app.desktop.panes;

import java.util.UUID;
import org.swetlokognatsk.earking_out.app.desktop.events.exercises.ExerciseFinishedEvent;
import org.swetlokognatsk.earking_out.core.domain.services.app.dto.puzzles.configs.PuzzleConfigDTO;
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

    protected final Pane innerPuzzlePane;
    protected final ProgressBar puzzlesProgressBar;
    protected final Button abortButton;
    protected final Label puzzleProgressLabel;
    protected final VBox puzzlesProgress;

    protected abstract Pane buildInnerPuzzlePane(final PCDTO puzzleConfigDto);

    public abstract void resetStateForNewPuzzle();

    public PuzzlePane(final UUID sessionId, final PCDTO puzzleConfigDto, final double width, final double height) {
        this.sessionId = sessionId;

        setWidth(width);
        setHeight(height);

        puzzleProgressLabel = buildPuzzleProgressLabel(puzzleConfigDto);
        puzzlesProgressBar = new ProgressBar(0.0);
        puzzlesProgress = buildPuzzlesProgress(puzzleProgressLabel, puzzlesProgressBar);
        setTop(puzzlesProgress);

        innerPuzzlePane = buildInnerPuzzlePane(puzzleConfigDto);
        setCenter(innerPuzzlePane);

        abortButton = buildAbortButton();
        // frontend hack to align button
        var abortButtonBox = new VBox(abortButton);
        abortButtonBox.setAlignment(Pos.CENTER);
        setBottom(abortButtonBox);
    }

    protected Label buildPuzzleProgressLabel(final PCDTO puzzleConfigDto) {
        var formattedCaption = interpolatePuzzleProgress(0, puzzleConfigDto.targetNumberOfPuzzles);
        return new Label(formattedCaption);
    }

    private static String interpolatePuzzleProgress(final int numberOfPuzzles, final int targetNumberOfPuzzles) {
        return String.format("%d of %d are completed", numberOfPuzzles, targetNumberOfPuzzles);
    }

    protected VBox buildPuzzlesProgress(final Label puzzleProgressLabel, final ProgressBar puzzlesProgressBar) {
        var puzzlesProgress = new VBox(puzzleProgressLabel, puzzlesProgressBar);
        puzzlesProgress.setAlignment(Pos.CENTER);
        return puzzlesProgress;
    }

    protected Button buildAbortButton() {
        var abortButton = new Button("finish");
        abortButton.setOnAction(this::abortExercise);
        return abortButton;
    }

    protected void abortExercise(ActionEvent e) {
        var exerciseFinishedEvent = new ExerciseFinishedEvent(ExerciseFinishedEvent.EXERCISE_FINISHED, sessionId);
        fireEvent(exerciseFinishedEvent);
    }
}
