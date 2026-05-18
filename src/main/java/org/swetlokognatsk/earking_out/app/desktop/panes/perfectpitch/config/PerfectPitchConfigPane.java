package org.swetlokognatsk.earking_out.app.desktop.panes.perfectpitch.config;

import org.swetlokognatsk.earking_out.app.desktop.helpers.RadioButtonHelper;
import org.swetlokognatsk.earking_out.app.desktop.panes.ConfigPane;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.PerfectPitchExercise;
import org.swetlokognatsk.earking_out.core.domain.model.music.NoteWithAccidental;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.perfectpitch.PerfectPitchConfig;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.perfectpitch.PerfectPitchInputMode;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;

// TODO generalize into abstract class
abstract class PerfectPitchConfigPane<E extends PerfectPitchExercise, PC extends PerfectPitchConfig<E>> extends ConfigPane<E, PC> {
    // public final String 
    
    protected final VBox notesBox;
    protected final VBox rootNoteBox;
    protected final ToggleGroup inputModeToggleGroup;
    protected final VBox inputModeBox;

    {
        var notesLabel = new Label("notes");
        // TODO replace with interactive piano keys box
        var tempNotes = new Label("notes are here");
        notesBox = new VBox(notesLabel, tempNotes);
        notesBox.setAlignment(Pos.CENTER);

        var rootNoteLabel = new Label("root note");
        var oneKeyChoicePiano = new Label("notes are here, but only one key can be chosen");
        rootNoteBox = new VBox(rootNoteLabel, oneKeyChoicePiano);
        rootNoteBox.setAlignment(Pos.CENTER);
        rootNoteBox.setVisible(false);

        var inputModeLabel = new Label("input mode");
        inputModeToggleGroup = new ToggleGroup();

        var inputModeradioButtons = RadioButtonHelper.makeList(PerfectPitchInputMode.class, inputModeToggleGroup, this::handleRadioButtonSelected);
        var inputModeOptions = new VBox(inputModeradioButtons);
        inputModeOptions.setAlignment(Pos.CENTER);

        inputModeBox = new VBox(inputModeLabel, inputModeOptions);
        inputModeBox.setAlignment(Pos.CENTER);
    }

    public PerfectPitchConfigPane(final PC puzzleConfig) {
        super(puzzleConfig);

        addCustomFields();
        setFieldsValuesFromConfig(puzzleConfig);

        addStartButton();

        setSpacing(20);
        setAlignment(Pos.CENTER);
    }

    protected void addCustomFields() {
        getChildren().addAll(notesBox, rootNoteBox, inputModeBox);
    }

    protected final void setFieldsValuesFromConfig(PerfectPitchConfig<?> puzzleConfig) {
        setInputMode(puzzleConfig.inputMode);
        setNotes(puzzleConfig.notesForPuzzle);
        if (puzzleConfig.inputMode == PerfectPitchInputMode.KEYBOARD_AS_PIANO) {
            setRootNote(puzzleConfig.rootNote);
        }
    }

    protected void setInputMode(PerfectPitchInputMode inputMode) {
        var radioButtons = inputModeToggleGroup.getToggles();
        RadioButton radioButton;
        for (var toggle : radioButtons) {
            radioButton = (RadioButton) toggle;
            if (radioButton.getId() == inputMode.name()) {
                inputModeToggleGroup.selectToggle(toggle);
                break;
            }
        }
    }

    protected void setNotes(NoteWithAccidental[] notes) {
        // TODO
    }

    protected void setRootNote(NoteWithAccidental rootNote) {
        // TODO
    }

    protected void chagneInputMode() {
        var selectedInputMode = (RadioButton) inputModeToggleGroup.getSelectedToggle();
        var id = selectedInputMode.getId();
        var inputMode = PerfectPitchInputMode.valueOf(id);
    }

    protected void handleRadioButtonSelected(ActionEvent e) {
        var selectedRadioButton = (RadioButton) inputModeToggleGroup.getSelectedToggle();
        var selectedRadioButtonId = selectedRadioButton.getId();
        if (selectedRadioButtonId == PerfectPitchInputMode.KEYBOARD_AS_PIANO.name()) {
            rootNoteBox.setVisible(true);
        }
        else if (selectedRadioButtonId == PerfectPitchInputMode.NOTES_AS_CHARACTERS.name()) {
            rootNoteBox.setVisible(false);
        }
    }

    protected String castCustomConfigPropertyNewValue(String configProperty, Object newValue) {
        return switch (configProperty) {
            case "test" -> "";
        default -> throw new IllegalArgumentException();
        };
    }
}
