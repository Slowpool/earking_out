package org.swetlokognatsk.earking_out.app.desktop.services;

import java.util.Map;

public interface KeySoundsService {
    /**
     * Byte - key number
     * {@link org.swetlokognatsk.earking_out.core.domain.model.music.Invariants#FIRST_NOTE_NUMBER}
     * String - absolute path to sound file
     */
    Map<Byte, String> getMap();
}
