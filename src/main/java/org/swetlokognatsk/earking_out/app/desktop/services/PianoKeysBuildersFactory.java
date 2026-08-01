package org.swetlokognatsk.earking_out.app.desktop.services;

import org.swetlokognatsk.earking_out.core.domain.model.piano.key.PianoKeyNumber;
import org.swetlokognatsk.earking_out.core.domain.services.domain.piano.PianoKeyColorService;
import org.swetlokognatsk.earking_out.core.ports.di.DI;

public class PianoKeysBuildersFactory {
    public static PianoKeysBuilder create(final double width, final double height, final PianoKeyNumber[] selectedKeys) {
        var colorService = DI.get(PianoKeyColorService.class);
        var pianoKeysBuilder = new PianoKeysBuilder(width, height, selectedKeys, colorService);
        return pianoKeysBuilder;
    }
}
