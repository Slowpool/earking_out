package org.swetlokognatsk.earking_out.app.desktop.panes.perfectpitch.puzzle;

import org.swetlokognatsk.earking_out.app.desktop.components.PianoKeyboard;
import org.swetlokognatsk.earking_out.app.desktop.events.piano.PianoKeyPressedEvent;
import org.swetlokognatsk.earking_out.app.desktop.events.piano.PianoKeyReleasedEvent;
import org.swetlokognatsk.earking_out.app.desktop.helpers.PianoKeyboardHandlersRegister;
import org.swetlokognatsk.earking_out.app.desktop.panes.factories.PianoKeyboardsFactory;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.AudioPerfectPitchExercise;
import org.swetlokognatsk.earking_out.core.domain.model.session.SessionId;
import org.swetlokognatsk.earking_out.core.domain.model.session.SessionStates;
import org.swetlokognatsk.earking_out.core.domain.services.app.dto.puzzles.configs.perfectpitch.AudioPerfectPitchConfigDTO;
import org.swetlokognatsk.earking_out.core.domain.services.app.dto.session.SessionAggregateDTOAssembler;
import org.swetlokognatsk.earking_out.core.domain.services.app.session.AudioPerfectPitchSessionService;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public final class AudioPerfectPitchPane extends PerfectPitchPane<AudioPerfectPitchExercise, AudioPerfectPitchConfigDTO, AudioPerfectPitchSessionService> {

    protected final PianoKeyboard pianoKeyboardForGuessing;

    // it is executed in super()
    protected Pane buildInnerPuzzlePane(final AudioPerfectPitchConfigDTO puzzleConfigDto) {
        var hintReplayButton = buildHintReplayButton();

        var pianoKeyboardForGuessing = buildPianoKeyboardForGuessing();
        var pane = new VBox(hintReplayButton, pianoKeyboardForGuessing);

        pane.setAlignment(Pos.CENTER);
        pane.setSpacing(20);

        return pane;
    }

    public AudioPerfectPitchPane(final SessionId sessionId, final AudioPerfectPitchConfigDTO puzzleConfigDto, final double width, final double height, final AudioPerfectPitchSessionService sessionService) {
        super(sessionId, puzzleConfigDto, width, height, sessionService);

        pianoKeyboardForGuessing = (PianoKeyboard) innerPuzzlePane.getChildren().get(1);
    }

    protected Button buildHintReplayButton() {
        var button = new Button("hear again");
        button.setOnAction(e -> {
            sessionService.hearAgain(sessionId);
        });
        return button;
    }

    protected PianoKeyboard buildPianoKeyboardForGuessing() {
        var pianoKeyboardWidth = getWidth();
        var pianoKeyboardHeight = getHeight() / 4;

        var pianoKeyboard = PianoKeyboardsFactory.createPerfectPitchNotesGuessing(pianoKeyboardWidth, pianoKeyboardHeight);
        pianoKeyboard.addEventHandler(PianoKeyPressedEvent.PIANO_KEY_PRESSED, this::guessViaPianoKeyPressing);
        pianoKeyboard.addEventHandler(PianoKeyReleasedEvent.PIANO_KEY_RELEASED, this::releasePianoKey);

        return pianoKeyboard;
    }

    // this could be in PianoKeyboardHandlersRegister, but because this logic is polymorphic, it's here. also coupling the puzzlePane to sessionService seems wrong because it makes PuzzlePane generics much more difficult to understand
    protected void guessViaPianoKeyPressing(final PianoKeyPressedEvent e) {
        sessionService.guessViaPianoKeyPressing(sessionId, e.keyNumber);
        PianoKeyboardHandlersRegister.updatePianoKeyboardView(pianoKeyboardForGuessing);
        // TODO other ui updates
        var session = SessionAggregateDTOAssembler.getSessionAggregateDTO(sessionId);

        if (session.state == SessionStates.COMPLETED) {
            fireExerciseFinishedEvent();
        }
        else if (session.prevGuessIsSuccessful.equals(Boolean.TRUE)) {
            updateCompletedPuzzlesNumber(session.stats.puzzlesCompleted);
        } else {

        }
    }

    public void releasePianoKey(final PianoKeyReleasedEvent e) {
        sessionService.releasePianoKey(sessionId);
        PianoKeyboardHandlersRegister.updatePianoKeyboardView(pianoKeyboardForGuessing);
        // TODO other ui updates
    }

}
