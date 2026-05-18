package org.swetlokognatsk.earking_out.app.desktop.panes;

import org.swetlokognatsk.earking_out.app.desktop.EarkingOutApplication;
import org.swetlokognatsk.earking_out.app.desktop.events.ConfigPropertyUpdatingEvent;
import org.swetlokognatsk.earking_out.app.desktop.events.ExerciseStartedEvent;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.Exercise;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.PuzzleConfig;
import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public abstract class ConfigPane<E extends Exercise, PC extends PuzzleConfig<E>> extends VBox {
    protected final E exercise;

    protected final HBox numberOfPuzzlesBox;
    protected final TextField numberOfPuzzlesField;
    protected final CheckBox statisticsRecordingField;
    protected final Button start;

    protected abstract void addCustomFields();

    protected abstract void setFieldsValuesFromConfig(PC puzzleConfig);

    protected abstract String castCustomConfigPropertyNewValue(String configProperty, Object newValue);

    {
        var numberOfPuzzlesLabel = new Label("number of puzzles");
        numberOfPuzzlesField = new TextField();
        var numberOfPuzzlesProperty = numberOfPuzzlesField.textProperty();
        numberOfPuzzlesProperty.addListener((prop, oldValue, newValue) -> {
            var containsOnlyDigits = newValue.matches("\\d*");
            if (!containsOnlyDigits) {
                numberOfPuzzlesField.setText(oldValue);
            }
        });
        numberOfPuzzlesProperty.addListener(createConfigPropertyUpadtingEvent(PuzzleConfig.TARGET_NUMBER_OF_PUZZLES_PROP));
        numberOfPuzzlesBox = new HBox(numberOfPuzzlesLabel, numberOfPuzzlesField);
        numberOfPuzzlesBox.setAlignment(Pos.CENTER);
        numberOfPuzzlesBox.setSpacing(EarkingOutApplication.LABEL_FIELD_SPACING);

        statisticsRecordingField = new CheckBox("statistics recording");
        statisticsRecordingField.setSelected(true);
        statisticsRecordingField.selectedProperty().addListener(createConfigPropertyUpadtingEvent(PuzzleConfig.STATS_RECORDING_PROP));

        start = new Button("start");
        start.setOnAction(this::fireExerciseStartedEvent);
    }

    public ConfigPane(final PC puzzleConfig) {
        this.exercise = puzzleConfig.exercise;

        addCommonFields(puzzleConfig);
    }

    protected ChangeListener<Object> createConfigPropertyUpadtingEvent(String configProperty) {
        return (prop, oldValue, newValue) -> {
            var castedNewValue = castConfigPropertyNewValue(configProperty, newValue);
            var configPropertyUpdatingEvent = new ConfigPropertyUpdatingEvent(ConfigPropertyUpdatingEvent.CONFIG_PROPERTY_UPDATING, exercise, configProperty, castedNewValue);
            fireEvent(configPropertyUpdatingEvent);
        };
    }

    private String castConfigPropertyNewValue(String configProperty, Object newValue) {
        return switch (configProperty) {
        // TODO what's the difference between `(String)obj` and `String.valueOf(obj)`?
        case PuzzleConfig.TARGET_NUMBER_OF_PUZZLES_PROP -> (String) newValue;
        case PuzzleConfig.STATS_RECORDING_PROP -> String.valueOf(newValue);
        default -> castCustomConfigPropertyNewValue(configProperty, newValue);
        };
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

        var exerciseStartedEvent = new ExerciseStartedEvent<>(ExerciseStartedEvent.EXERCISE_STARTED, exercise);
        fireEvent(exerciseStartedEvent);
    }
}
