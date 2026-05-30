package org.swetlokognatsk.earking_out.app.desktop.services;

import org.swetlokognatsk.earking_out.core.domain.model.hints.UsualHint;
import javafx.scene.media.AudioClip;

public final class AudioClipHintPlayer implements AudioHintPlayer<UsualHint> {
    // TODO cache
    protected AudioClip audioClip;

    public void prepareHint(UsualHint hint) {
        audioClip = new AudioClip("file://" + hint.getValue());
    }

    public void play() {
        audioClip.play();
    }

    public void stopAndPlay() {
        tryStop();
        play();
    }

    protected void tryStop() {
        if (!audioClipIsNull()) {
            stop();
        }
    }

    public void stop() throws IllegalStateException {
        if (audioClipIsNull()) {
            throw new IllegalStateException("sound is not defined");
        }
        audioClip.stop();
    }

    protected boolean audioClipIsNull() {
        return audioClip == null;
    }
}
