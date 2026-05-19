package org.swetlokognatsk.earking_out.core.ports.config;

import org.swetlokognatsk.earking_out.core.domain.model.exercises.Exercise;

public interface WritePuzzleConfigService {
    void updateProperty(Exercise e, String configProperty, Object newValue);
}
