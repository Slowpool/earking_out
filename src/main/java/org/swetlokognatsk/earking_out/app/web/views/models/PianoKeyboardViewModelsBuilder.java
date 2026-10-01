package org.swetlokognatsk.earking_out.app.web.views.models;

import java.util.ArrayList;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.swetlokognatsk.earking_out.core.domain.model.piano.key.PianoKeyNumber;
import org.swetlokognatsk.earking_out.core.domain.model.piano.keyboard.PianoKeyboardId;
import org.swetlokognatsk.earking_out.core.domain.services.domain.piano.PianoKeyColorService;
import org.swetlokognatsk.earking_out.core.ports.di.DI;
import org.swetlokognatsk.earking_out.core.ports.piano.PianoKeyboardRepository;
import lombok.AllArgsConstructor;
import static org.swetlokognatsk.earking_out.core.domain.model.music.Constants.PIANO_KEYS_NUMBER;
import static org.swetlokognatsk.earking_out.core.domain.model.piano.key.PianoKeyNumber.FIRST_NOTE_NUMBER;

@Component
@Lazy
@AllArgsConstructor
public class PianoKeyboardViewModelsBuilder {

    private final PianoKeyboardRepository pianoKeyboardRepository;
    private final PianoKeyColorService colorService;

    public PianoKeyboardViewModel build(final PianoKeyboardId pianoKeyboardId) {
        var pianoKeys = buildPianoKeyViewModels(pianoKeyboardId);
        return new PianoKeyboardViewModel(pianoKeyboardId, pianoKeys);
    }

    private PianoKeyViewModel[] buildPianoKeyViewModels(final PianoKeyboardId pianoKeyboardId) {
        var pianoKeyDtos = pianoKeyboardRepository.getPianoKeyboardDTO(pianoKeyboardId)
                .pianoKeys();

        final var pianoKeys = new ArrayList<PianoKeyViewModel>(PIANO_KEYS_NUMBER);
        PianoKeyNumber.forEachKey((PianoKeyNumber keyNumber) -> {
            var pianoKeyDto = pianoKeyDtos.get(keyNumber);
            var pianoKeyModel = new PianoKeyViewModel(keyNumber, colorService.getColor(keyNumber), pianoKeyDto.isPressed(), pianoKeyDto.isSelected());
            pianoKeys.add(pianoKeyModel);
        });
        return pianoKeys.toArray(PianoKeyViewModel[]::new);
    }
}
