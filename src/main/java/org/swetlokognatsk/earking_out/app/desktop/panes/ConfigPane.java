package org.swetlokognatsk.earking_out.app.desktop.panes;

import org.swetlokognatsk.earking_out.app.desktop.EarkingOutApplication;
import org.swetlokognatsk.earking_out.app.desktop.events.ExerciseStartedEvent;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.Exercise;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.PuzzleConfig;
import org.swetlokognatsk.earking_out.core.ports.DI;
import org.swetlokognatsk.earking_out.core.ports.config.ReadPuzzleConfigService;
import org.swetlokognatsk.earking_out.core.ports.config.WritePuzzleConfigService;

import javafx.event.ActionEvent;
import javafx.event.EventType;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public abstract class ConfigPane<E extends Exercise, PC extends PuzzleConfig<E>> extends VBox {
    // TODO where it should be?
    public static final EventType<ExerciseStartedEvent> EXERCISE_STARTED = new EventType<ExerciseStartedEvent>("EXERCISE_STARTED");

    protected final E exercise;
    protected final WritePuzzleConfigService writePuzzleConfigService;
    protected final ReadPuzzleConfigService readPuzzleConfigService;

    protected final HBox numberOfPuzzlesBox;
    protected final TextField numberOfPuzzlesField;
    protected final CheckBox statisticsRecordingField;
    protected final Button start;

    protected abstract void addCustomFields();

    protected abstract void setFieldsValuesFromConfig(PC puzzleConfig);

    {
        var numberOfPuzzlesLabel = new Label("number of puzzles");
        var numberOfPuzzlesTextField = new TextField();
        numberOfPuzzlesTextField.textProperty().addListener((prop, oldValue, newValue) -> {
            var containsOnlyDigits = newValue.matches("\\d*");
            if (!containsOnlyDigits) {
                numberOfPuzzlesTextField.setText(oldValue);
            }
        });
        numberOfPuzzlesField = numberOfPuzzlesTextField;
        numberOfPuzzlesBox = new HBox(numberOfPuzzlesLabel, numberOfPuzzlesTextField);
        numberOfPuzzlesBox.setAlignment(Pos.CENTER);
        numberOfPuzzlesBox.setSpacing(EarkingOutApplication.LABEL_FIELD_SPACING);

        statisticsRecordingField = new CheckBox("statistics recording");
        statisticsRecordingField.setSelected(true);

        start = new Button("start");
        start.setOnAction(this::fireExerciseStartedEvent);
    }

    public ConfigPane(final PC puzzleConfig) {
        // TODO what's the difference between calling super() and not doing so here?
        super();
        this.exercise = puzzleConfig.exercise;
        this.readPuzzleConfigService = DI.get(ReadPuzzleConfigService.class);
        this.writePuzzleConfigService = DI.get(WritePuzzleConfigService.class);

        addCommonFields(puzzleConfig);
    }

    private void addCommonFields(PC puzzleConfig) {
        setCommonFieldsValuesFromConfig(puzzleConfig);
        getChildren().addAll(numberOfPuzzlesBox, statisticsRecordingField);
    }

    private void setCommonFieldsValuesFromConfig(PC puzzleConfig) {
        numberOfPuzzlesField.setText("" + puzzleConfig.targetNumberOfPuzzles);
        statisticsRecordingField.setSelected(puzzleConfig.statsRecording);
    }

    protected void addStartButton() {
        getChildren().add(start);
    }

    private void fireExerciseStartedEvent(ActionEvent e) {
        // TODO should it be here or after fireEvent()? how it works at all, i mean events flow - like middleware in both directions?
        e.consume();
        var puzzleConfig = readPuzzleConfigService.fetch(exercise);
        if (puzzleConfig.isValid()) {
            var exerciseStartedEvent = new ExerciseStartedEvent<>(EXERCISE_STARTED, puzzleConfig);
            fireEvent(exerciseStartedEvent);
        } else {
            // TODO message
            // DialogPane.
        }
    }
}
