package org.swetlokognatsk.earking_out.app.desktop.panes.perfectpitch.puzzle;

import org.swetlokognatsk.earking_out.DebugUtils;
import org.swetlokognatsk.earking_out.app.desktop.components.PianoKeyboard;
import org.swetlokognatsk.earking_out.app.desktop.events.PopupRequestEvent;
import org.swetlokognatsk.earking_out.app.desktop.events.piano.PianoKeyPressedEvent;
import org.swetlokognatsk.earking_out.app.desktop.events.piano.PianoKeyReleasedEvent;
import org.swetlokognatsk.earking_out.app.desktop.helpers.PianoKeyboardHandlersRegister;
import org.swetlokognatsk.earking_out.app.desktop.panes.factories.PianoKeyboardsFactory;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.AudioPerfectPitchExercise;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.perfectpitch.PerfectPitchInputMode;
import org.swetlokognatsk.earking_out.core.domain.model.session.SessionAggregate;
import org.swetlokognatsk.earking_out.core.domain.model.session.SessionId;
import org.swetlokognatsk.earking_out.core.domain.model.session.SessionStates;
import org.swetlokognatsk.earking_out.core.domain.model.session.exceptions.InvalidTextNoteException;
import org.swetlokognatsk.earking_out.core.domain.model.session.exceptions.OutOfRangeTextNoteException;
import org.swetlokognatsk.earking_out.core.domain.model.session.perfectpitch.AudioPerfectPitchSessionAggregate;
import org.swetlokognatsk.earking_out.core.domain.services.app.PianoKeyboardService;
import org.swetlokognatsk.earking_out.core.domain.services.app.dto.puzzles.configs.perfectpitch.AudioPerfectPitchConfigDTO;
import org.swetlokognatsk.earking_out.core.domain.services.app.dto.session.SessionAggregateDTOAssembler;
import org.swetlokognatsk.earking_out.core.domain.services.app.dto.session.perfectpitch.AudioPerfectPitchSessionAggregateDTO;
import org.swetlokognatsk.earking_out.core.domain.services.app.session.AudioPerfectPitchSessionService;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;

public final class AudioPerfectPitchPane extends PerfectPitchPane<AudioPerfectPitchExercise, AudioPerfectPitchConfigDTO, AudioPerfectPitchSessionService> {

    private final PerfectPitchInputMode inputMode;
    private final Region guessingComponent;
    private final PianoKeyboardHandlersRegister pianoKeyboardHandlersRegister;

    // it is executed in super()
    protected Pane buildInnerPuzzlePane(final AudioPerfectPitchConfigDTO puzzleConfigDto) {
        var hintReplayButton = buildHintReplayButton();

        var guessingComponent = buildGuessingComponent(puzzleConfigDto.inputMode);
        var pane = new VBox(hintReplayButton, guessingComponent);

        pane.setAlignment(Pos.CENTER);
        pane.setSpacing(20);

        return pane;
    }

    public AudioPerfectPitchPane(final SessionId sessionId, final AudioPerfectPitchConfigDTO puzzleConfigDto, final double width, final double height, final AudioPerfectPitchSessionService sessionService, final PianoKeyboardHandlersRegister pianoKeyboardHandlersRegister, final PianoKeyboardService pianoKeyboardService) {
        super(sessionId, puzzleConfigDto, width, height, sessionService, pianoKeyboardService);
        inputMode = puzzleConfigDto.inputMode;
        this.pianoKeyboardHandlersRegister = pianoKeyboardHandlersRegister;

        guessingComponent = (Region) innerPuzzlePane.getChildren().get(1);
    }

    protected Button buildHintReplayButton() {
        var button = new Button("hear again");
        button.setOnAction(e -> {
            sessionService.hearAgain(sessionId);
        });
        return button;
    }

    private Region buildGuessingComponent(final PerfectPitchInputMode inputMode) {
        return switch (inputMode) {
        // TODO handle KEYBOARD_AS_PIANO differently
        case KEYBOARD_AS_PIANO, PIANO_ON_SCREEN -> buildPianoKeyboardForGuessing();
        case NOTES_AS_TEXT -> buildNotesTextFieldForGuessing();
        default -> throw new RuntimeException("unknown input mode: %s".formatted(inputMode));
        };
    }

    protected PianoKeyboard buildPianoKeyboardForGuessing() {
        var pianoKeyboardWidth = getWidth();
        var pianoKeyboardHeight = getHeight() / 4;

        var pianoKeyboard = PianoKeyboardsFactory.createPerfectPitchNotesGuessing(pianoKeyboardWidth, pianoKeyboardHeight);
        pianoKeyboard.addEventHandler(PianoKeyPressedEvent.PIANO_KEY_PRESSED, this::handlePianoKeyPressing);
        pianoKeyboard.addEventHandler(PianoKeyReleasedEvent.PIANO_KEY_RELEASED, this::releasePianoKey);

        return pianoKeyboard;
    }

    // this could be in PianoKeyboardHandlersRegister, but because this logic is polymorphic, it's here. also coupling the puzzlePane to sessionService seems wrong because it makes PuzzlePane generics much more difficult to understand
    protected void handlePianoKeyPressing(final PianoKeyPressedEvent e) {
        pianoKeyboardService.pressPianoKey(e.pianoKeyboardId, e.keyNumber);
        pianoKeyboardHandlersRegister.updatePianoKeyboardView((PianoKeyboard) guessingComponent);
        // TODO other ui updates
        applyPostGuessingUpdate();
    }

    private void applyPostGuessingUpdate() {
        var session = getCurrentSessionDTO();

        if (session.state != SessionStates.IN_PROGRESS) {
            fireExerciseFinishedEvent();
        } else if (session.prevGuessIsSuccessful.equals(Boolean.TRUE)) {
            updateCompletedPuzzlesNumber(session.stats.puzzlesCompleted);
        }
    }

    private AudioPerfectPitchSessionAggregateDTO getCurrentSessionDTO() {
        return (AudioPerfectPitchSessionAggregateDTO) SessionAggregateDTOAssembler.getSessionAggregateDTO(sessionId);
    }

    public void releasePianoKey(final PianoKeyReleasedEvent e) {
        var session = getCurrentSessionDTO();
        // in case the last piano key pressing was successful and it was the last puzzle, the session closes, so it's not allowed to edit piano keyboard state any more. ui will just show the stats page. guessing piano keyboard state will be reset on the starting of the following session. summarizing, without this state check it'll cause `NoActiveSessionException`
        if (session.state != SessionStates.IN_PROGRESS) {
            return;
        }

        pianoKeyboardService.releasePianoKey(e.pianoKeyboardId);
        // TODO what? eliminate crutch
        pianoKeyboardHandlersRegister.updatePianoKeyboardView((PianoKeyboard) guessingComponent);
        // TODO other ui updates
    }

    private Region buildNotesTextFieldForGuessing() {
        var notesTextField = new TextField();
        notesTextField.setOnAction(this::handleTextNoteInsert);
        return notesTextField;
    }

    private void handleTextNoteInsert(ActionEvent event) {
        var textNoteField = (TextField) guessingComponent;
        try {
            sessionService.guessViaTextNote(textNoteField.getText());

            textNoteField.clear();
            applyPostGuessingUpdate();
        } catch (InvalidTextNoteException e) {
            popup("Invalid note");
        } catch (OutOfRangeTextNoteException e) {
            popup("Note is out of range");
        }
    }

    // TODO make it general somehow
    private void popup(final String message) {
        var event = new PopupRequestEvent(PopupRequestEvent.POPUP_REQUEST_EVENT, message);
        fireEvent(event);
    }

}
