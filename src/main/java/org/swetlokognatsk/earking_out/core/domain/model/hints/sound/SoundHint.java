package org.swetlokognatsk.earking_out.core.domain.model.hints.sound;

import org.swetlokognatsk.earking_out.core.domain.model.hints.Hint;
import org.swetlokognatsk.earking_out.core.domain.model.sounds.Sound;

public abstract class SoundHint<S extends Sound> extends Hint {
    public final S sound;

    public SoundHint(final S sound) {
        this.sound = sound;
    }
}
