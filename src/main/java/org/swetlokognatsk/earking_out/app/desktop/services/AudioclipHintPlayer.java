package org.swetlokognatsk.earking_out.app.desktop.services;

import org.swetlokognatsk.earking_out.core.domain.model.hints.UsualHint;

import javafx.scene.media.AudioClip;

public class AudioclipHintPlayer implements AudioHintPlayer<UsualHint> {
    // TODO cache
    protected AudioClip audioClip;
    
    public void play(UsualHint hint) {
        audioClip = new AudioClip("file://" + hint.getValue());
        audioClip.play();
    }

    public void stop() {
        if (audioClip == null) {
            // TODO how 'bout picking another exception?
            throw new RuntimeException();
        }
        audioClip.stop();
    }
}
