package org.swetlokognatsk.earking_out.core.domain.helpers;

import java.util.HashMap;
import java.util.Map;
import org.swetlokognatsk.earking_out.core.domain.model.piano.keyboard.PianoKeyboardAggregate;
import org.swetlokognatsk.earking_out.core.domain.model.piano.keyboard.PianoKeyboardId;

public final class PianoKeyboardHelper {

    public static final Map<PianoKeyboardId, PianoKeyboardAggregate> createPianoKeyboardsMap(final PianoKeyboardAggregate[] pianoKeyboardAggregates) {
        Map<PianoKeyboardId, PianoKeyboardAggregate> pianoKeyboardsMap = new HashMap<>();
        for (var pianoKeyboardAggregate : pianoKeyboardAggregates) {
            pianoKeyboardsMap.put(pianoKeyboardAggregate.getId(), pianoKeyboardAggregate);
        }
        return pianoKeyboardsMap;
    }
}
