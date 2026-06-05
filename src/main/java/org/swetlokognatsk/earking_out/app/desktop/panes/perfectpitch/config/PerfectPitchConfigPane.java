package org.swetlokognatsk.earking_out.app.desktop.panes.perfectpitch.config;

import org.apache.commons.lang3.ArrayUtils;
import org.swetlokognatsk.earking_out.app.desktop.components.PianoKeyboard;
import org.swetlokognatsk.earking_out.app.desktop.helpers.PianoKeyboardHelper;
import org.swetlokognatsk.earking_out.app.desktop.helpers.RadioButtonHelper;
import org.swetlokognatsk.earking_out.app.desktop.panes.ConfigPane;
import org.swetlokognatsk.earking_out.app.desktop.panes.factories.PianoKeyboardsFactory;
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

abstract class PerfectPitchConfigPane<E extends PerfectPitchExercise, PC extends PerfectPitchConfig<E>> extends ConfigPane<E, PC> {
    protected final PianoKeyboard pianoKeyboard;
    protected final VBox pianoKeyboardBox;

    protected final PianoKeyboard rootNotePicker;
    protected final VBox rootNoteBox;

    protected final ToggleGroup inputModeToggleGroup;
    protected final VBox inputModeBox;

    protected double getPianoKeyboardHeight() {
        return getHeight() / 4;
    }

    protected double getPianoKeyboardWidth() {
        return getWidth();
    }

    public PerfectPitchConfigPane(final PC puzzleConfig, double width, double height) {
        super(puzzleConfig, width, height);

        pianoKeyboard = buildPianoKeyboard(puzzleConfig.normalizedNotesForPuzzle);
        pianoKeyboardBox = buildPianoKeyboardBox(pianoKeyboard);

        rootNotePicker = buildRootNotePicker(puzzleConfig.normalizedRootNote);
        rootNoteBox = buildRootNotePickerBox(rootNotePicker, puzzleConfig.inputMode);

        inputModeToggleGroup = new ToggleGroup();
        var inputModeRadioButtons = buildInputModeRadioButtons(inputModeToggleGroup, puzzleConfig.inputMode);
        inputModeBox = buildInputModeBox(inputModeRadioButtons);

        addCustomFields();
        addStartButton();

        setSpacing(20);
        setAlignment(Pos.CENTER);
    }

    protected PianoKeyboard buildPianoKeyboard(final byte[] selectedKeys) {
        var pianoKeyboard = PianoKeyboardsFactory.createPerfectPitchNotesPicker(getPianoKeyboardWidth(), getPianoKeyboardHeight(), selectedKeys);

        PianoKeyboardHelper.addPianoKeyEventsHandlers(pianoKeyboard);

        // TODO this listener should be added to domain model???
        // pianoKeyboard.selectedKeysProperty().addListener(createConfigPropertyUpadtingEvent(PerfectPitchConfig.NORMALIZED_NOTES_FOR_PUZZLE_PROP));
        return pianoKeyboard;
    }

    protected static VBox buildPianoKeyboardBox(PianoKeyboard pianoKeyboard) {
        var notesLabel = new Label("notes");
        var pianoKeyboardBox = new VBox(notesLabel, pianoKeyboard);
        pianoKeyboardBox.setAlignment(Pos.CENTER);
        return pianoKeyboardBox;
    }

    protected PianoKeyboard buildRootNotePicker(Byte selectedRootNote) {
        var wrappedSelectedRootNote = selectedRootNote == null ? new byte[0] : new byte[] { selectedRootNote };
        var rootNotePicker = PianoKeyboardsFactory.createRootNotePicker(getPianoKeyboardWidth(), getPianoKeyboardHeight(), wrappedSelectedRootNote);

        PianoKeyboardHelper.addPianoKeyEventsHandlers(rootNotePicker);

        // TODO this listener should be added to domain model???
        // rootNotePicker.selectedKeysProperty().addListener(createConfigPropertyUpadtingEvent(PerfectPitchConfig.NORMALIZED_ROOT_NOTE));
        return rootNotePicker;
    }

    protected static VBox buildRootNotePickerBox(PianoKeyboard rootNotePicker, PerfectPitchInputMode inputMode) {
        var rootNoteLabel = new Label("root note picking");

        var rootNoteBox = new VBox(rootNoteLabel, rootNotePicker);
        rootNoteBox.setAlignment(Pos.CENTER);
        var shouldDisplay = inputMode == PerfectPitchInputMode.KEYBOARD_AS_PIANO;
        rootNoteBox.setVisible(shouldDisplay);

        return rootNoteBox;
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
        case PerfectPitchConfig.NORMALIZED_NOTES_FOR_PUZZLE_PROP -> {
            var set = (ObservableSet<Byte>) newValue;
            var objArray = set.toArray(new Byte[0]);
            var primitiveArray = ArrayUtils.toPrimitive(objArray);
            yield primitiveArray;
        }
        case PerfectPitchConfig.INPUT_MODE_PROP -> {
            var radioButton = (RadioButton) newValue;
            var enumValue = radioButton.getId();
            var enumElement = PerfectPitchInputMode.valueOf(enumValue);
            yield enumElement;
        }
        case PerfectPitchConfig.NORMALIZED_ROOT_NOTE -> {
            // TODO remaking
            var set = (ObservableSet<Byte>) newValue;
            var numberOfSelectedKeys = set.size();
            // TODO DRY violation, copy-pasted from PianoKeyboard
            if (numberOfSelectedKeys == 0) {
                yield null;
            } else if (numberOfSelectedKeys > 1) {
                throw new IllegalStateException("several keys were selected, although only one key was supposed to be selected");
            }
            yield set.iterator().next();
        }
        default -> throw new IllegalArgumentException("unknown custom property: " + configProperty);
        };
    }
}
