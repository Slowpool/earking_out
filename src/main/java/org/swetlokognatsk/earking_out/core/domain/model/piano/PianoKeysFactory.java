package org.swetlokognatsk.earking_out.core.domain.model.piano;

import org.swetlokognatsk.earking_out.app.desktop.services.SoundPlayerService;
import org.swetlokognatsk.earking_out.core.domain.services.piano.PianoKeyColorService;
import org.swetlokognatsk.earking_out.core.domain.services.piano.PianoKeyColorServiceImpl;
import org.swetlokognatsk.earking_out.core.ports.DI;

public final class PianoKeysFactory {

    private PianoKeysFactory() {
    }

    public static PianoKey create(byte keyNumber, PianoKeyMode mode) {
        var keyColorService = DI.get(PianoKeyColorService.class);
        // TODO use hints? this logic is already implemented somewhere
        var soundPlayerLatch = new SoundPlayerService() {
            public void play() {
            }

            public void stopAndPlay() {
            }

            public void stop() {
            }
        };
        var pianoKey = new PianoKey(keyNumber, mode, keyColorService, soundPlayerLatch);
        return pianoKey;
    }

}
