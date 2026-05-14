package org.swetlokognatsk.earking_out.app.desktop.panes;

import org.swetlokognatsk.earking_out.app.desktop.events.ExerciseStartedOverEvent;
import org.swetlokognatsk.earking_out.core.domain.model.Session;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.PuzzleConfig;

import javafx.event.ActionEvent;
import javafx.event.EventType;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public abstract class  SessionStatsPane<PC extends PuzzleConfig<?>> extends VBox {
    // TODO how 'bout inheritance from EXERCISE_STARTED?
    public static EventType<ExerciseStartedOverEvent<?>> EXERCISE_STARTED_OVER = new EventType<>("EXERCISE_STARTED_OVER");
    // TODO generics seem weird
    protected Session<PC> session;

    protected abstract Pane buildStatsPane();

    // TODO passing Session domain model is a crime against good code - use dto instead
    public SessionStatsPane(Session<PC> session) {
        super();
        this.session = session;

        var titleLabel = new Label("finished");
        var stats = session.stats();
        var briefResultsText = interpolateBriefResult(stats.puzzlesCompletedCorrectly, stats.puzzlesCompleted);
        var briefResultLabel = new Label(briefResultsText);
        var briefResultBox = new VBox(briefResultLabel);
        briefResultBox.setAlignment(Pos.CENTER);

        var titleLabelBox = new VBox(titleLabel);
        titleLabelBox.setAlignment(Pos.CENTER);

        var startOverButton = new Button("start over");
        startOverButton.setOnAction(this::fireExerciseStartOverEvent);
        var startOverBox = new VBox(startOverButton);
        startOverBox.setAlignment(Pos.CENTER);

        var statsPane = buildStatsPane();

        getChildren().addAll(titleLabelBox, briefResultBox, statsPane, startOverBox);
    }

    private static String interpolateBriefResult(int puzzlesCompleted, int targetNumberOfPuzzles) {
        return String.format("%d of %d are guessed correctly", puzzlesCompleted, targetNumberOfPuzzles);
    }

    private void fireExerciseStartOverEvent(ActionEvent e) {
        // TODO correct?
        e.consume();

        var config = session.puzzleConfig();
        var exerciseStartedOverEvent = new ExerciseStartedOverEvent<>(EXERCISE_STARTED_OVER, config);
        fireEvent(exerciseStartedOverEvent);
    }
}
