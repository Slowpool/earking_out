package org.swetlokognatsk.earking_out.app.desktop.services;

import java.util.Map;

import org.swetlokognatsk.earking_out.core.domain.model.piano.key.PianoKeyNumber;

public interface KeySoundsService {
    /**
     * 2nd param (String) - absolute path to sound file
     */
    Map<PianoKeyNumber, String> getMap();
}
