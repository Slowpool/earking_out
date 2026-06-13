package org.swetlokognatsk.earking_out.app.desktop.panes;

import org.swetlokognatsk.earking_out.app.desktop.events.exercises.ExerciseStartedOverEvent;
import org.swetlokognatsk.earking_out.core.domain.model.Session;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.PuzzleConfigAggregate;
import org.swetlokognatsk.earking_out.core.domain.services.app.dto.puzzles.configs.PuzzleConfigDTO;

import javafx.event.ActionEvent;
import javafx.event.EventType;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public abstract class SessionStatsPane<PCDTO extends PuzzleConfigDTO<?>> extends BorderPane {
    protected final Session<PCDTO> session;

    protected abstract Pane buildStatsPane();

    // TODO passing Session domain model is a crime against good code - use dto instead
    public SessionStatsPane(Session<PCDTO> session) {
        this.session = session;

        var titleLabel = new Label("finished");
        var titleLabelBox = new VBox(titleLabel);
        titleLabelBox.setAlignment(Pos.CENTER);

        var stats = session.stats();
        var briefResultsText = interpolateBriefResult(stats.puzzlesCompletedCorrectly, stats.puzzlesCompleted);
        var briefResultLabel = new Label(briefResultsText);
        var briefResultBox = new VBox(briefResultLabel);
        briefResultBox.setAlignment(Pos.CENTER);

        var topPane = new VBox(titleLabelBox, briefResultBox);
        setTop(topPane);

        var startOverButton = new Button("start over");
        startOverButton.setOnAction(this::fireExerciseStartOverEvent);
        var startOverBox = new VBox(startOverButton);
        startOverBox.setAlignment(Pos.CENTER);
        setBottom(startOverBox);

        var statsPane = buildStatsPane();
        setCenter(statsPane);
    }

    private static String interpolateBriefResult(int puzzlesCompleted, int targetNumberOfPuzzles) {
        return String.format("%d of %d are guessed correctly", puzzlesCompleted, targetNumberOfPuzzles);
    }

    private void fireExerciseStartOverEvent(ActionEvent e) {
        e.consume();

        PCDTO puzzleConfigDto = session.puzzleConfigDto();
        var exerciseStartedOverEvent = new ExerciseStartedOverEvent<>(ExerciseStartedOverEvent.EXERCISE_STARTED_OVER, puzzleConfigDto);
        fireEvent(exerciseStartedOverEvent);
    }
}
