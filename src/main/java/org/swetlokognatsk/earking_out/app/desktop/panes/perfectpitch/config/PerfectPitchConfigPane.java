package org.swetlokognatsk.earking_out.app.desktop.panes.perfectpitch.config;

import org.swetlokognatsk.earking_out.app.desktop.panes.ConfigPane;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfect_pitch.PerfectPitchExercise;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.perfectpitch.PerfectPitchConfig;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.perfectpitch.typed.AudioPerfectPitchConfig;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;

// TODO generalize into abstract class
abstract class PerfectPitchConfigPane<PC extends PerfectPitchConfig<? extends PerfectPitchExercise>> extends ConfigPane<PC> {
    protected final VBox notes;
    protected final VBox inputMode;

    {
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
    }

    public PerfectPitchConfigPane(final PC puzzleConfig) {
        super(puzzleConfig);
        addCustomSettings();
        addStartButton();

        setSpacing(20);
        setAlignment(Pos.CENTER);
    }

    protected void addCustomSettings() {
        getChildren().addAll(notes, inputMode);
    }
}
