package org.swetlokognatsk.earking_out.app.web.views.models;

import java.util.ArrayList;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.swetlokognatsk.earking_out.core.domain.model.piano.key.PianoKeyNumber;
import org.swetlokognatsk.earking_out.core.domain.model.piano.keyboard.PianoKeyboardId;
import org.swetlokognatsk.earking_out.core.domain.services.domain.piano.PianoKeyColorService;
import org.swetlokognatsk.earking_out.core.domain.services.domain.piano.PianoSoundPolicyService;
import org.swetlokognatsk.earking_out.core.ports.di.DI;
import org.swetlokognatsk.earking_out.core.ports.piano.PianoKeyboardRepository;
import lombok.AllArgsConstructor;
import static org.swetlokognatsk.earking_out.core.domain.model.music.Constants.PIANO_KEYS_NUMBER;
import static org.swetlokognatsk.earking_out.core.domain.model.piano.key.PianoKeyNumber.FIRST_NOTE_NUMBER;

@Component
@AllArgsConstructor
public class PianoKeyboardViewModelsProjector {

    private final PianoKeyboardRepository pianoKeyboardRepository;
    private final PianoKeyColorService colorService;
    private final PianoSoundPolicyService pianoSoundPolicyService;

    // TODO rename to project
    public PianoKeyboardViewModel build(final PianoKeyboardId pianoKeyboardId) {
        var pianoKeys = buildPianoKeyViewModels(pianoKeyboardId);
        var areKeySoundsEnabled = pianoSoundPolicyService.shouldPlaySound(pianoKeyboardId);
        return new PianoKeyboardViewModel(pianoKeyboardId, getHtmlClass(pianoKeyboardId), areKeySoundsEnabled, pianoKeys);
    }

    private String getHtmlClass(final PianoKeyboardId pianoKeyboardId) {
        return switch (pianoKeyboardId) {
            case AUDIO_PERFECT_PITCH_NOTES_PICKER, AUDIO_PERFECT_PITCH_ROOT_NOTE_PICKER -> "puzzle-config";
            case AUDIO_PERFECT_PITCH_NOTES_GUESSING -> "perfect-pitch-guessing";
            default -> throw new IllegalArgumentException("unkown piano keyboard id: %s".formatted(pianoKeyboardId.toString()));
        };
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
