package org.swetlokognatsk.earking_out.app.desktop.panes.perfectpitch.config;

import org.apache.commons.lang3.ArrayUtils;
import org.swetlokognatsk.earking_out.app.desktop.components.PianoKeyboard;
import org.swetlokognatsk.earking_out.app.desktop.components.PianoKeyboardMode;
import org.swetlokognatsk.earking_out.app.desktop.helpers.RadioButtonHelper;
import org.swetlokognatsk.earking_out.app.desktop.panes.ConfigPane;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.PerfectPitchExercise;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.perfectpitch.PerfectPitchConfig;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.perfectpitch.PerfectPitchInputMode;
import javafx.collections.ObservableSet;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;

// TODO generalize into abstract class
abstract class PerfectPitchConfigPane<E extends PerfectPitchExercise, PC extends PerfectPitchConfig<E>> extends ConfigPane<E, PC> {
    protected final PianoKeyboard pianoKeyboard;

    protected final VBox pianoKeyboardBox;
    protected final VBox rootNoteBox;
    protected final ToggleGroup inputModeToggleGroup;
    protected final VBox inputModeBox;

    {
        var rootNoteLabel = new Label("root note");
        var oneKeyChoicePiano = new Label("notes are here, but only one key can be chosen");
        rootNoteBox = new VBox(rootNoteLabel, oneKeyChoicePiano);
        rootNoteBox.setAlignment(Pos.CENTER);
        rootNoteBox.setVisible(false);
    }

    public PerfectPitchConfigPane(final PC puzzleConfig, double width, double height) {
        super(puzzleConfig, width, height);

        pianoKeyboard = buildPianoKeyboard(puzzleConfig.normalizedNotesForPuzzle);
        pianoKeyboardBox = buildPianoKeyboardBox(pianoKeyboard);

        inputModeToggleGroup = new ToggleGroup();
        var inputModeRadioButtons = buildInputModeRadioButtons(inputModeToggleGroup, puzzleConfig.inputMode);
        inputModeBox = buildInputModeBox(inputModeRadioButtons);

        addCustomFields();
        setFieldsValuesFromConfig(puzzleConfig);
        addStartButton();

        setSpacing(20);
        setAlignment(Pos.CENTER);
    }

    protected RadioButton[] buildInputModeRadioButtons(ToggleGroup inputModeToggleGroup, PerfectPitchInputMode selectedInputMode) {
        var inputModeRadioButtons = RadioButtonHelper.makeList(PerfectPitchInputMode.class, inputModeToggleGroup, selectedInputMode, this::handleRadioButtonSelected);
        inputModeToggleGroup.selectedToggleProperty().addListener(createConfigPropertyUpadtingEvent(PerfectPitchConfig.INPUT_MODE_PROP));

        return inputModeRadioButtons;
    }

    protected static VBox buildInputModeBox(RadioButton[] inputModeRadioButtons) {
        var inputModeLabel = new Label("input mode");

        var inputModeOptions = new VBox(inputModeRadioButtons);
        inputModeOptions.setAlignment(Pos.CENTER);

        var inputModeBox = new VBox(inputModeLabel, inputModeOptions);
        inputModeBox.setAlignment(Pos.CENTER);
        return inputModeBox;
    }

    protected void addCustomFields() {
        getChildren().addAll(pianoKeyboardBox, rootNoteBox, inputModeBox);
    }

    protected PianoKeyboard buildPianoKeyboard(byte[] selectedKeys) {
        var pianoKeyboard = new PianoKeyboard(PianoKeyboardMode.SEVERAL_KEYS_SELECT, getWidth(), getHeight() / 4, selectedKeys);
        pianoKeyboard.selectedKeysProperty().addListener(createConfigPropertyUpadtingEvent(PerfectPitchConfig.NORMALIZED_NOTES_FOR_PUZZLE_PROP));
        return pianoKeyboard;
    }

    protected static VBox buildPianoKeyboardBox(PianoKeyboard pianoKeyboard) {
        var notesLabel = new Label("notes");
        var pianoKeyboardBox = new VBox(notesLabel, pianoKeyboard);
        pianoKeyboardBox.setAlignment(Pos.CENTER);
        return pianoKeyboardBox;
    }

    protected final void setFieldsValuesFromConfig(PerfectPitchConfig<?> puzzleConfig) {
        setInputMode(puzzleConfig.inputMode);
        if (puzzleConfig.inputMode == PerfectPitchInputMode.KEYBOARD_AS_PIANO) {
            setRootNote(puzzleConfig.normalizedRootNote);
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

    protected void setRootNote(Byte normalizedRootNote) {
        // TODO
    }

    protected void chagneInputMode() {
        // TODO
        var selectedInputMode = (RadioButton) inputModeToggleGroup.getSelectedToggle();
        var id = selectedInputMode.getId();
        var inputMode = PerfectPitchInputMode.valueOf(id);
    }

    protected void handleRadioButtonSelected(ActionEvent e) {
        var selectedRadioButton = (RadioButton) inputModeToggleGroup.getSelectedToggle();
        var selectedRadioButtonId = selectedRadioButton.getId();
        if (selectedRadioButtonId == PerfectPitchInputMode.KEYBOARD_AS_PIANO.name()) {
            rootNoteBox.setVisible(true);
        } else if (selectedRadioButtonId == PerfectPitchInputMode.NOTES_AS_CHARACTERS.name()) {
            rootNoteBox.setVisible(false);
        }
    }

    protected Object castCustomConfigPropertyNewValue(String configProperty, Object newValue) {
        return switch (configProperty) {
        // TODO why (Integer) or (Byte) is fine whereas (SetProperty<Byte>) gives unchecked cast warning?
        case PerfectPitchConfig.NORMALIZED_NOTES_FOR_PUZZLE_PROP -> {
            var set = (ObservableSet<Byte>) newValue;
            var objArray = set.toArray(new Byte[0]);
            var primitiveArray = ArrayUtils.toPrimitive(objArray);
            yield primitiveArray;
        }
        case PerfectPitchConfig.INPUT_MODE_PROP -> {
            var radioButton = (RadioButton)newValue;
            var enumValue = radioButton.getId();
            var enumElement = PerfectPitchInputMode.valueOf(enumValue);
            yield enumElement;
        }
        default -> throw new IllegalArgumentException();
        };
    }

    // protected void fireSelectedNotesUpdated(SelectedNotesUpdatedEvent e) {

    //     var configPropertyUpdatedEvent = new ConfigPropertyUpdatingEvent(ConfigPropertyUpdatingEvent.CONFIG_PROPERTY_UPDATING, exercise, PerfectPitchConfig.NOTES_FOR_PUZZLE_PROP, );
    //     fireEvent(configPropertyUpdatedEvent);
    // }

}
