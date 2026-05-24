package org.swetlokognatsk.earking_out.app.desktop.panes.perfectpitch.puzzle;

import org.swetlokognatsk.earking_out.app.desktop.components.PianoKeyboard;
import org.swetlokognatsk.earking_out.app.desktop.components.PianoKeyboardMode;
import org.swetlokognatsk.earking_out.core.domain.model.Session;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.puzzles.AudioPerfectPitchPuzzle;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.perfectpitch.typed.AudioPerfectPitchExercise;
import org.swetlokognatsk.earking_out.core.domain.model.hints.UsualHint;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.perfectpitch.typed.AudioPerfectPitchConfig;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.generators.perfectpitch.AudioPerfectPitchPuzzleGenerator;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class AudioPerfectPitchPane extends PerfectPitchPane<AudioPerfectPitchExercise, AudioPerfectPitchConfig, UsualHint, AudioPerfectPitchPuzzleGenerator, AudioPerfectPitchPuzzle> {

    public AudioPerfectPitchPane(Session<AudioPerfectPitchConfig> session, double width, double height) {
        super(session, width, height);

    }

    protected Pane buildPuzzlePane() {
        var hearAgainButton = new Button("hear again");

        var pianoKeyboardWidth = getWidth();
        var pianoKeyboardHeight = getHeight() / 4;
        var pianoKeyboard = new PianoKeyboard(PianoKeyboardMode.ONE_KEY_TOUCH, pianoKeyboardWidth, pianoKeyboardHeight, new byte[0]);
        var pane = new VBox(hearAgainButton, pianoKeyboard);

        pane.setAlignment(Pos.CENTER);
        pane.setSpacing(20);

        return pane;
    }
}
