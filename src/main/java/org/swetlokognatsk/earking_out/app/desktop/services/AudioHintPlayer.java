package org.swetlokognatsk.earking_out.app.desktop.services;

import org.swetlokognatsk.earking_out.core.domain.model.hints.Hint;

public interface AudioHintPlayer<H extends Hint> extends SoundPlayerService {
    // this method is introduced for optimization - to not reconstruct AudioHintPlayer each time new hint is provided
    void prepareHint(H hint);
}
