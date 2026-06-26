package org.swetlokognatsk.earking_out.app.desktop.panes.perfectpitch.puzzle;

import java.util.UUID;
import org.swetlokognatsk.earking_out.app.desktop.components.PianoKeyboard;
import org.swetlokognatsk.earking_out.app.desktop.events.session.HearAgainEvent;
import org.swetlokognatsk.earking_out.app.desktop.panes.factories.PianoKeyboardsFactory;
import org.swetlokognatsk.earking_out.core.domain.services.app.dto.puzzles.configs.perfectpitch.AudioPerfectPitchConfigDTO;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public final class AudioPerfectPitchPane extends PerfectPitchPane<AudioPerfectPitchConfigDTO> {

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

    public AudioPerfectPitchPane(final UUID sessionId, final AudioPerfectPitchConfigDTO puzzleConfigDto, final double width, final double height) {
        super(sessionId, puzzleConfigDto, width, height);

        pianoKeyboardForGuessing = (PianoKeyboard) innerPuzzlePane.getChildren().get(1);
    }

    protected Button buildHintReplayButton() {
        var button = new Button("hear again");
        button.setOnAction(e -> {
            var hearAgainEvent = new HearAgainEvent(HearAgainEvent.HEAR_AGAIN_EVENT);
            fireEvent(hearAgainEvent);
        });
        return button;
    }

    protected PianoKeyboard buildPianoKeyboardForGuessing() {
        var pianoKeyboardWidth = getWidth();
        var pianoKeyboardHeight = getHeight() / 4;
        var pianoKeyboard = PianoKeyboardsFactory.createPerfectPitchNotesGuessing(pianoKeyboardWidth, pianoKeyboardHeight);
        return pianoKeyboard;
    }

    public void resetStateForNewPuzzle() {
        // TODO clear selected notes
    }

}
