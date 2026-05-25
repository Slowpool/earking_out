package org.swetlokognatsk.earking_out.app.desktop.panes.perfectpitch.puzzle;

import org.swetlokognatsk.earking_out.app.desktop.components.PianoKeyboard;
import org.swetlokognatsk.earking_out.app.desktop.components.PianoKeyboardMode;
import org.swetlokognatsk.earking_out.app.desktop.services.AudioHintPlayer;
import org.swetlokognatsk.earking_out.core.domain.model.Session;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.puzzles.AudioPerfectPitchPuzzle;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.typed.AudioPerfectPitchExercise;
import org.swetlokognatsk.earking_out.core.domain.model.hints.UsualHint;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.PuzzlesFactory;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.perfectpitch.typed.AudioPerfectPitchConfig;
import org.swetlokognatsk.earking_out.core.ports.puzzles.generators.perfectpitch.AudioPerfectPitchPuzzleGenerator;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class AudioPerfectPitchPane extends PerfectPitchPane<AudioPerfectPitchExercise, AudioPerfectPitchConfig, UsualHint, AudioPerfectPitchPuzzleGenerator, AudioPerfectPitchPuzzle> {

    protected final AudioHintPlayer<UsualHint> audioHintPlayer;
    protected final PianoKeyboard pianoKeyboardForGuessing;

    public AudioPerfectPitchPane(final Session<AudioPerfectPitchConfig> session, final double width, final double height, final AudioHintPlayer<UsualHint> audioHintPlayer) {
        super(session, width, height);
        this.audioHintPlayer = audioHintPlayer;

        // TODO it's awkward, but dunno how to do it in different way
        this.pianoKeyboardForGuessing = (PianoKeyboard)puzzlePane.getChildren().get(1);

        nextPuzzle();
    }

    protected Pane buildPuzzlePane() {
        var hearAgainButton = new Button("hear again");

        var pianoKeyboardWidth = getWidth();
        var pianoKeyboardHeight = getHeight() / 4;
        var pianoKeyboardForGuessing = new PianoKeyboard(PianoKeyboardMode.ONE_KEY_TOUCH, pianoKeyboardWidth, pianoKeyboardHeight, new byte[0]);
        var pane = new VBox(hearAgainButton, pianoKeyboardForGuessing);

        pane.setAlignment(Pos.CENTER);
        pane.setSpacing(20);

        return pane;
    }

    // TODO refactoring. idea: servicesLocator is injected into each PuzzlePane, then it defines what type of exercise and finds the required service to demonstrate hints.
    protected void demonstrateHint() {
        audioHintPlayer.play(puzzle.hint);
    }
}
