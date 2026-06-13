package org.swetlokognatsk.earking_out.core.domain.services.app.dto.puzzles.configs;

import org.swetlokognatsk.earking_out.core.domain.model.exercises.Exercise;
import org.swetlokognatsk.earking_out.core.domain.services.app.dto.EndDTOAssembler;

public final class EndDTOAssemblersFactory {

    public static <E extends Exercise, EDTOA extends EndDTOAssembler<E, ?, ?>> EDTOA create(E exercise) {
        // // TODO
        // var endAssembler = switch (exercise) {

        // };
    }
}
