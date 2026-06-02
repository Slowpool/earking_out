package org.swetlokognatsk.earking_out.app.desktop.services;

import org.swetlokognatsk.earking_out.core.domain.services.piano.PianoKeyColorService;
import org.swetlokognatsk.earking_out.core.ports.DI;

public class PianoKeysBuildersFactory {
    public static PianoKeysBuilder create(final double width, final double height, final byte[] selectedKeys) {
        var keySoundsService = DI.get(KeySoundsService.class);
        var keySounds = keySoundsService.getMap();
        var colorService = DI.get(PianoKeyColorService.class);
        var pianoKeysBuilder = new PianoKeysBuilder(width, height, selectedKeys, keySounds, colorService);
        return pianoKeysBuilder;
    }
}
