package org.swetlokognatsk.earking_out.app.desktop.panes.perfectpitch.puzzle;

import org.swetlokognatsk.earking_out.app.desktop.components.PianoKeyboard;
import org.swetlokognatsk.earking_out.app.desktop.components.PianoKeyboardMode;
import org.swetlokognatsk.earking_out.app.desktop.services.AudioHintPlayer;
import org.swetlokognatsk.earking_out.core.domain.model.Session;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.puzzles.AudioPerfectPitchPuzzle;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.typed.AudioPerfectPitchExercise;
import org.swetlokognatsk.earking_out.core.domain.model.hints.UsualHint;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.perfectpitch.typed.AudioPerfectPitchConfig;
import org.swetlokognatsk.earking_out.core.ports.puzzles.generators.perfectpitch.AudioPerfectPitchPuzzleGenerator;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public final class AudioPerfectPitchPane extends PerfectPitchPane<AudioPerfectPitchExercise, AudioPerfectPitchConfig, UsualHint, AudioPerfectPitchPuzzleGenerator, AudioPerfectPitchPuzzle> {

    protected final AudioHintPlayer<UsualHint> audioHintPlayer;
    protected final PianoKeyboard pianoKeyboardForGuessing;

    // it is executed in super()
    protected Pane buildPuzzlePane() {
        var hearAgainButton = buildHintReplayButton();

        var pianoKeyboardForGuessing = buildPianoKeyboardForGuessing();
        var pane = new VBox(hearAgainButton, pianoKeyboardForGuessing);

        pane.setAlignment(Pos.CENTER);
        pane.setSpacing(20);

        return pane;
    }

    public AudioPerfectPitchPane(final Session<AudioPerfectPitchConfig> session, final double width, final double height, final AudioHintPlayer<UsualHint> audioHintPlayer) {
        super(session, width, height);

        this.audioHintPlayer = audioHintPlayer;
        pianoKeyboardForGuessing = (PianoKeyboard) puzzlePane.getChildren().get(1);

        nextPuzzle();
    }

    protected Button buildHintReplayButton() {
        var button = new Button("hear again");
        button.setOnAction(e -> {
            demonstrateNewHint();
        });
        return button;
    }

    protected PianoKeyboard buildPianoKeyboardForGuessing() {
        var pianoKeyboardWidth = getWidth();
        var pianoKeyboardHeight = getHeight() / 4;
        var pianoKeyboard = new PianoKeyboard(PianoKeyboardMode.ONE_KEY_TOUCH, pianoKeyboardWidth, pianoKeyboardHeight, new byte[0]);
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
