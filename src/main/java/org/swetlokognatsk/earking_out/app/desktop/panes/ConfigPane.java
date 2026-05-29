package org.swetlokognatsk.earking_out.app.desktop.panes;

import org.swetlokognatsk.earking_out.app.desktop.EarkingOutApplication;
import org.swetlokognatsk.earking_out.app.desktop.events.configs.ConfigPropertyUpdatingEvent;
import org.swetlokognatsk.earking_out.app.desktop.events.exercises.ExerciseStartedEvent;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.Exercise;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.PuzzleConfig;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
    protected abstract Object castCustomConfigPropertyNewValue(String configProperty, Object newValue);

    {
        start = new Button("start");
        start.setOnAction(this::fireExerciseStartedEvent);
    }

    public ConfigPane(final PC puzzleConfig, double width, double height) {
        exercise = puzzleConfig.exercise;

        numberOfPuzzlesField = buildNumberOfPuzzlesField(puzzleConfig.targetNumberOfPuzzles);
        numberOfPuzzlesBox = buildNumberOfPuzzlesBox(numberOfPuzzlesField);

        statisticsRecordingField = initStatisticsRecordingField(puzzleConfig.statsRecording);

        setWidth(width);
        setHeight(height);

        addCommonFields(puzzleConfig);
    }

    private TextField buildNumberOfPuzzlesField(int numberOfPuzzles) {
        var numberOfPuzzlesField = new TextField(String.valueOf(numberOfPuzzles));
        var numberOfPuzzlesProperty = numberOfPuzzlesField.textProperty();
        numberOfPuzzlesProperty.addListener(this::restrictInputToNumbers);
        numberOfPuzzlesProperty.addListener(createConfigPropertyUpadtingEvent(PuzzleConfig.TARGET_NUMBER_OF_PUZZLES_PROP));
        return numberOfPuzzlesField;
    }

    protected void restrictInputToNumbers(ObservableValue<?> observable, String oldValue, String newValue) {
        var containsOnlyDigits = newValue.matches("\\d*");
        if (!containsOnlyDigits) {
            numberOfPuzzlesField.setText(oldValue);
        }
    }

    private HBox buildNumberOfPuzzlesBox(TextField numberOfPuzzlesField) {
        var numberOfPuzzlesLabel = new Label("number of puzzles");
        var numberOfPuzzlesBox = new HBox(numberOfPuzzlesLabel, numberOfPuzzlesField);
        numberOfPuzzlesBox.setAlignment(Pos.CENTER);
        numberOfPuzzlesBox.setSpacing(EarkingOutApplication.LABEL_FIELD_SPACING);
        return numberOfPuzzlesBox;
    }

    private CheckBox initStatisticsRecordingField(boolean isSelected) {
        var statisticsRecordingField = new CheckBox("statistics recording");
        statisticsRecordingField.setSelected(isSelected);
        statisticsRecordingField.selectedProperty().addListener(createConfigPropertyUpadtingEvent(PuzzleConfig.STATS_RECORDING_PROP));
        return statisticsRecordingField;
    }

    protected ChangeListener<Object> createConfigPropertyUpadtingEvent(String configProperty) {
        return (prop, oldValue, newValue) -> {
            var castedNewValue = castConfigPropertyNewValue(configProperty, newValue);
            var configPropertyUpdatingEvent = new ConfigPropertyUpdatingEvent(ConfigPropertyUpdatingEvent.CONFIG_PROPERTY_UPDATING, exercise, configProperty, castedNewValue);
            fireEvent(configPropertyUpdatingEvent);
        };
    }

    private Object castConfigPropertyNewValue(String configProperty, Object newValue) {
        // here the casts are just for the sake of explicitness, actually they aren't necessary
        return switch (configProperty) {
        case PuzzleConfig.TARGET_NUMBER_OF_PUZZLES_PROP -> {
            int intNewValue;
            try {
                intNewValue = Integer.valueOf((String) newValue);
            } catch (NumberFormatException e) {
                intNewValue = 0;
            }
            yield intNewValue;
        }
        case PuzzleConfig.STATS_RECORDING_PROP -> (boolean) newValue;
        default -> castCustomConfigPropertyNewValue(configProperty, newValue);
        };
    }

    private void addCommonFields(PC puzzleConfig) {
        setCommonFieldsValuesFromConfig(puzzleConfig);
        getChildren().addAll(numberOfPuzzlesBox, statisticsRecordingField);
    }

    // TODO it's being set in constructor, do we need it?
    private void setCommonFieldsValuesFromConfig(PC puzzleConfig) {
    }

    protected void addStartButton() {
        getChildren().add(start);
    }

    private void fireExerciseStartedEvent(ActionEvent e) {
        e.consume();

        var exerciseStartedEvent = new ExerciseStartedEvent<>(ExerciseStartedEvent.EXERCISE_STARTED, exercise);
        fireEvent(exerciseStartedEvent);
    }
}
