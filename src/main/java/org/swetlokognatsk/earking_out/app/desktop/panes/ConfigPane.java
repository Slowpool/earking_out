package org.swetlokognatsk.earking_out.app.desktop.panes;

import org.swetlokognatsk.earking_out.app.desktop.EarkingOutApplication;
import org.swetlokognatsk.earking_out.app.desktop.events.ExerciseStartedEvent;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.Exercise;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.PuzzleConfig;
import javafx.event.ActionEvent;
import javafx.event.EventType;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public abstract class ConfigPane<PC extends PuzzleConfig<? extends Exercise>> extends VBox {
    // TODO where it should be?
    public static final EventType<ExerciseStartedEvent> EXERCISE_STARTED = new EventType<ExerciseStartedEvent>("EXERCISE_STARTED");

    protected final PC puzzleConfig;

    protected final HBox numberOfPuzzles;
    protected final TextField numberOfPuzzlesField;
    protected final CheckBox statisticsRecording;
    protected final Button start;

    protected abstract PC mapToDomainConfig();

    protected abstract void addCustomSettings();

    {
        var numberOfPuzzlesLabel = new Label("number of puzzles");
        var numberOfPuzzlesTextField = new TextField();
        numberOfPuzzlesField = numberOfPuzzlesTextField;
        numberOfPuzzles = new HBox(numberOfPuzzlesLabel, numberOfPuzzlesTextField);
        numberOfPuzzles.setAlignment(Pos.CENTER);
        numberOfPuzzles.setSpacing(EarkingOutApplication.LABEL_FIELD_SPACING);

        statisticsRecording = new CheckBox("statistics recording");
        statisticsRecording.setSelected(true);

        start = new Button("start");
        start.setOnAction(this::fireExerciseStartedEvent);
    }

    public ConfigPane(final PC puzzleConfig) {
        // TODO what's the difference between calling super() and not doing so here?
        super();
        this.puzzleConfig = puzzleConfig;

        addCommonComponents();
    }

    private void addCommonComponents() {
        getChildren().addAll(numberOfPuzzles, statisticsRecording);
    }

    protected void addStartButton() {
        getChildren().add(start);
    }

    private void fireExerciseStartedEvent(ActionEvent e) {
        // TODO should it be here or after fireEvent()? how it works at all, i mean events flow - like middleware in both directions?
        e.consume();
        // TODO validate
        var puzzleConfig = mapToDomainConfig();
        var exerciseStartedEvent = new ExerciseStartedEvent(EXERCISE_STARTED, puzzleConfig);
        fireEvent(exerciseStartedEvent);
    }

}
