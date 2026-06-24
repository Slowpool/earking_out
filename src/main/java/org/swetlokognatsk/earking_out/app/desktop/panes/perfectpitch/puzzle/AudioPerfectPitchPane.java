package org.swetlokognatsk.earking_out.app.desktop.panes.perfectpitch.puzzle;

import org.swetlokognatsk.earking_out.app.desktop.components.PianoKeyboard;
import org.swetlokognatsk.earking_out.app.desktop.events.session.HearAgainEvent;
import org.swetlokognatsk.earking_out.app.desktop.panes.factories.PianoKeyboardsFactory;
import org.swetlokognatsk.earking_out.app.desktop.services.AudioHintPlayer;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.AudioPerfectPitchExercise;
import org.swetlokognatsk.earking_out.core.domain.model.hints.UsualHint;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.perfectpitch.AudioPerfectPitchPuzzle;
import org.swetlokognatsk.earking_out.core.domain.model.session.Session;
import org.swetlokognatsk.earking_out.core.domain.services.app.dto.puzzles.configs.perfectpitch.AudioPerfectPitchConfigDTO;
import org.swetlokognatsk.earking_out.core.ports.puzzles.generators.perfectpitch.AudioPerfectPitchSolutionGenerator;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public final class AudioPerfectPitchPane extends PerfectPitchPane<AudioPerfectPitchExercise, AudioPerfectPitchConfigDTO, UsualHint, AudioPerfectPitchSolutionGenerator, AudioPerfectPitchPuzzle> {

    protected final AudioHintPlayer<UsualHint> audioHintPlayer;
    protected final PianoKeyboard pianoKeyboardForGuessing;

    // it is executed in super()
    protected Pane buildPuzzlePane() {
        var hintReplayButton = buildHintReplayButton();

        var pianoKeyboardForGuessing = buildPianoKeyboardForGuessing();
        var pane = new VBox(hintReplayButton, pianoKeyboardForGuessing);

        pane.setAlignment(Pos.CENTER);
        pane.setSpacing(20);

        return pane;
    }

    public AudioPerfectPitchPane(final Session<AudioPerfectPitchConfigDTO> session, final double width, final double height, final AudioHintPlayer<UsualHint> audioHintPlayer) {
        super(session, width, height);

        this.audioHintPlayer = audioHintPlayer;
        pianoKeyboardForGuessing = (PianoKeyboard) puzzlePane.getChildren().get(1);

        nextPuzzle();
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

    // TODO refactoring. idea: servicesLocator is injected into each PuzzlePane, then it defines what type of exercise and finds the required service to demonstrate hints.
    protected void demonstrateNewHint() {
        audioHintPlayer.prepareHint(puzzle.hint);
        demonstrateHint();
    }

    protected void demonstrateHint() {
        audioHintPlayer.stopAndPlay();
    }
}
