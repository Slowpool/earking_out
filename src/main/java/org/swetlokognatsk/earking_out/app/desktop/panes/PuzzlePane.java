package org.swetlokognatsk.earking_out.app.desktop.panes;

import org.swetlokognatsk.earking_out.app.desktop.events.ExerciseFinishedEvent;
import org.swetlokognatsk.earking_out.core.domain.model.Session;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.Exercise;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.PuzzleConfig;
import javafx.event.ActionEvent;
import javafx.event.EventType;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public abstract class PuzzlePane<PC extends PuzzleConfig<? extends Exercise>> extends BorderPane {
    // TODO why it's so awkward? do i use it in a wrong way?
    public static final EventType<ExerciseFinishedEvent> EXERCISE_FINISHED = new EventType<ExerciseFinishedEvent>("EXERCISE_FINISHED");

    protected final Session<PC> session;
    protected final PC puzzleConfig;

    protected final Pane puzzlePane;
    protected final ProgressBar puzzlesProgressBar;
    protected final Button finishButton;

    protected abstract Pane buildPuzzlePane();

    public PuzzlePane(final Session<PC> session) {
        this.session = session;
        this.puzzleConfig = session.puzzleConfig();

        var puzzleProgressLabel = new Label(interpolatePuzzleProgress(0, puzzleConfig.targetNumberOfPuzzles));
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
        var exerciseFinishedEvent = new ExerciseFinishedEvent(EXERCISE_FINISHED, session);
        fireEvent(exerciseFinishedEvent);
    }
}
