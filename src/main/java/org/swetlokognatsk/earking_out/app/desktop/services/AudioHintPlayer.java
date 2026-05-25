package org.swetlokognatsk.earking_out.app.desktop.services;

import org.swetlokognatsk.earking_out.core.domain.model.hints.Hint;

public interface AudioHintPlayer<H extends Hint> {
    void play(H hint);
}
