package org.swetlokognatsk.earking_out.app.desktop.panes;

import org.swetlokognatsk.earking_out.app.desktop.events.exercises.ExerciseStartedOverEvent;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.Exercise;
import org.swetlokognatsk.earking_out.core.domain.model.session.GeneralSessionStats;
import org.swetlokognatsk.earking_out.core.domain.services.app.dto.session.SessionAggregateDTO;
import org.swetlokognatsk.earking_out.core.domain.services.app.session.ExtendedSessionStats;
import org.swetlokognatsk.earking_out.core.domain.services.app.session.SessionStatsService;

import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public abstract class SessionStatsPane<E extends Exercise, SADTO extends SessionAggregateDTO<E, ?, ?, ?>, ESSA extends SessionStatsService<E, ? extends ExtendedSessionStats<E>>> extends BorderPane {
    protected final SADTO sessionDto;

    protected abstract Pane buildStatsPane();

    public SessionStatsPane(final SADTO sessionDto) {
        this.sessionDto = sessionDto;

        var titleLabel = new Label("finished");
        var titleLabelBox = new VBox(titleLabel);
        titleLabelBox.setAlignment(Pos.CENTER);

        var stats = sessionDto.stats;
        var briefResultsText = interpolateBriefResult(stats);
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

    private static String interpolateBriefResult(final GeneralSessionStats stats) {
        return "%d of %d or %.2f are guessed correctly".formatted(stats.puzzlesCompletedPerfectly, stats.puzzlesCompleted, stats.getPerfectlyCompletedPuzzlesRate());
    }

    private void fireExerciseStartOverEvent(ActionEvent e) {
        e.consume();

        var puzzleConfigDto = sessionDto.puzzleConfigDto;
        var exerciseStartedOverEvent = new ExerciseStartedOverEvent<>(ExerciseStartedOverEvent.EXERCISE_STARTED_OVER, puzzleConfigDto);
        fireEvent(exerciseStartedOverEvent);
    }
}
