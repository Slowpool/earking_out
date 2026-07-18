package org.swetlokognatsk.earking_out.app.desktop.panes;

import org.swetlokognatsk.earking_out.app.desktop.events.exercises.ExerciseFinishedEvent;
import org.swetlokognatsk.earking_out.app.desktop.helpers.TextHelper;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.Exercise;
import org.swetlokognatsk.earking_out.core.domain.model.session.SessionAggregate;
import org.swetlokognatsk.earking_out.core.domain.model.session.SessionId;
import org.swetlokognatsk.earking_out.core.domain.services.app.SessionService;
import org.swetlokognatsk.earking_out.core.domain.services.app.dto.puzzles.configs.PuzzleConfigDTO;
import org.swetlokognatsk.earking_out.core.ports.session.SessionRepository;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public abstract class PuzzlePane<E extends Exercise, PCDTO extends PuzzleConfigDTO<E>, SS extends SessionService<E, ? extends SessionAggregate<E, ?, ?, PCDTO>, ? extends SessionRepository<?>>> extends BorderPane {
    protected final SessionId sessionId;
    protected final E exercise;
    protected final int targetNumberOfPuzzles;
    protected final SS sessionService;

    protected final Pane innerPuzzlePane;
    protected final ProgressBar puzzlesProgressBar;
    protected final Button abortButton;
    protected final Label puzzleProgressLabel;
    protected final VBox puzzlesProgress;

    protected abstract Pane buildInnerPuzzlePane(final PCDTO puzzleConfigDto);

    public PuzzlePane(final SessionId sessionId, final PCDTO puzzleConfigDto, final double width, final double height, final SS sessionService) {
        this.sessionId = sessionId;
        this.exercise = puzzleConfigDto.exercise;
        this.targetNumberOfPuzzles = puzzleConfigDto.targetNumberOfPuzzles;
        this.sessionService = sessionService;

        setWidth(width);
        setHeight(height);

        puzzleProgressLabel = buildPuzzleProgressLabel();
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

    protected Label buildPuzzleProgressLabel() {
        var formattedCaption = TextHelper.interpolatePuzzleProgress(0, targetNumberOfPuzzles);
        return new Label(formattedCaption);
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

    // TODO rename to abortSession
    protected void abortExercise(final ActionEvent e) {
        sessionService.abort(sessionId);
        fireExerciseFinishedEvent();
    }

    protected void fireExerciseFinishedEvent() {
        var exerciseFinishedEvent = new ExerciseFinishedEvent<E>(ExerciseFinishedEvent.EXERCISE_FINISHED, sessionId, exercise);
        fireEvent(exerciseFinishedEvent);
    }

    protected void updateCompletedPuzzlesNumber(final int numberOfCompletedPuzzles) {
        double newProgress = (double) numberOfCompletedPuzzles / targetNumberOfPuzzles;
        puzzlesProgressBar.setProgress(newProgress);

        var newProgressText = TextHelper.interpolatePuzzleProgress(numberOfCompletedPuzzles, targetNumberOfPuzzles);
        puzzleProgressLabel.setText(newProgressText);
    }
}
