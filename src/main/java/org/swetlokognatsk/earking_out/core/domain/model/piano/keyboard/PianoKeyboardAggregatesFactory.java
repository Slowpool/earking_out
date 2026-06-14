package org.swetlokognatsk.earking_out.core.domain.model.piano.keyboard;

import java.util.Arrays;
import org.apache.commons.lang3.ArrayUtils;
import org.swetlokognatsk.earking_out.core.domain.model.base.Factory;

public final class PianoKeyboardAggregatesFactory implements Factory<PianoKeyboardAggregate> {

    public PianoKeyboardAggregate createDefault() {
        throw new RuntimeException("not implemented");
    }

    public PianoKeyboardAggregate createDeepCopy(final PianoKeyboardAggregate pianoKeyboardAggregate) {
        byte[] oldSelectedKeyNumbers = pianoKeyboardAggregate.getSelectedKeyNumbers();
        byte[] selectedKeyNumbersCopy = Arrays.copyOf(oldSelectedKeyNumbers, pianoKeyboardAggregate.selectedKeys.size());
        var copy = new PianoKeyboardAggregate(pianoKeyboardAggregate.getId(), selectedKeyNumbersCopy);
        return copy;
    }

}
