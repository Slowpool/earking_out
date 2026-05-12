package org.swetlokognatsk.earking_out.app.desktop;

import org.swetlokognatsk.earking_out.core.domain.events.exercises.ExerciseStarted;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.Exercise;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.ExerciseNames;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.ExerciseTypes;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfect_pitch.typed.AudioPerfectPitchExercise;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.PuzzleConfig;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.perfectpitch.PerfectPitchConfig;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.perfectpitch.typed.AudioPerfectPitchConfig;

import javafx.event.ActionEvent;
import javafx.event.EventType;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

class PerfectPitchConfigPane extends VBox {
    // TODO generalize into abstract class
    static final EventType<ExerciseStartedEvent> EXERCISE_STARTED = new EventType<ExerciseStartedEvent>("EXERCISE_STARTED");

    final HBox numberOfPuzzles;
    final VBox notes;
    final VBox inputMode;
    final CheckBox statisticsRecording;
    final Button start;

    {
        var numberOfPuzzlesLabel = new Label("number of puzzles");
        var numberOfPuzzlesTextField = new TextField();
        numberOfPuzzles = new HBox(numberOfPuzzlesLabel, numberOfPuzzlesTextField);
        numberOfPuzzles.setAlignment(Pos.CENTER);
        numberOfPuzzles.setSpacing(EarkingOutApplication.LABEL_FIELD_SPACING);

        var notesLabel = new Label("notes");
        // TODO replace with interactive piano keys box
        var notesBox = new Label("notes are here");
        notes = new VBox(notesLabel, notesBox);
        notes.setAlignment(Pos.CENTER);

        var inputModeLabel = new Label("input mode");
        var inputModeToggleGroup = new ToggleGroup();

        var keyboardAsPianoRadio = new RadioButton("keyboard as piano");
        keyboardAsPianoRadio.setToggleGroup(inputModeToggleGroup);

        var notesAsCharactersRadio = new RadioButton("notes as characters");
        notesAsCharactersRadio.setToggleGroup(inputModeToggleGroup);

        var inputModeOptions = new VBox(keyboardAsPianoRadio, notesAsCharactersRadio);
        inputModeOptions.setAlignment(Pos.CENTER);

        inputMode = new VBox(inputModeLabel, inputModeOptions);
        inputMode.setAlignment(Pos.CENTER);

        statisticsRecording = new CheckBox("statistics recording");
        statisticsRecording.setSelected(true);

        start = new Button("start");
        start.setOnAction(this::startClicked);
    }

    public PerfectPitchConfigPane() {
        super();
        getChildren().add(numberOfPuzzles);
        getChildren().add(notes);
        getChildren().add(inputMode);
        getChildren().add(statisticsRecording);
        getChildren().add(start);

        setSpacing(20);
        setAlignment(Pos.CENTER);
    }

    private void startClicked(ActionEvent e) {
        // TODO validate
        var puzzleConfig = mapToDomainConfig();
        var exercise = mapToExercise();
        var exerciseStartedEvent = new ExerciseStartedEvent(EXERCISE_STARTED, puzzleConfig, exercise);
        fireEvent(exerciseStartedEvent);
    }

    private PuzzleConfig mapToDomainConfig() {
        // TODO
        return new AudioPerfectPitchConfig();
    }

    private Exercise mapToExercise() {
        // TODO
        return new AudioPerfectPitchExercise();
    }
}
