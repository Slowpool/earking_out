package org.swetlokognatsk.earking_out.app.desktop.services;

import java.util.Map;

public interface KeySoundsService {
    // TODO how to add clickable class? (markdown?)
    /**
     * Byte - key number (@see Invariants.FIRST_NOTE_NUMBER)
     * String - absolute path to sound file
     */
    Map<Byte, String> getMap();
}
