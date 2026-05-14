package org.swetlokognatsk.earking_out.app.desktop.panes.perfectpitch;

import org.swetlokognatsk.earking_out.core.domain.model.Session;
import org.swetlokognatsk.earking_out.core.domain.model.puzzles.configs.perfectpitch.typed.AudioPerfectPitchConfig;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class AudioPerfectPitchPane extends PerfectPitchPane<AudioPerfectPitchConfig> {

    public AudioPerfectPitchPane(Session<AudioPerfectPitchConfig> session) {
        super(session);

    }

    protected Pane buildPuzzlePane() {
        var hearAgainButton = new Button("hear again");
        // TODO replace with real piano
        var temporaryLabel = new Label("notes are here");
        var pane = new VBox(hearAgainButton, temporaryLabel);
        pane.setAlignment(Pos.CENTER);
        return pane;
    }
}

// TODO check that all project package names do not contain _ (those that were added by me)