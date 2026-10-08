package org.swetlokognatsk.earking_out.infrastructure.sounds;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Component;
import org.swetlokognatsk.earking_out.core.domain.model.piano.key.PianoKeyNumber;
import org.swetlokognatsk.earking_out.core.ports.piano.PianoKeySoundFilesResolver;
import org.swetlokognatsk.earking_out.infrastructure.annotations.DesktopComponent;

@DesktopComponent
public final class PianoKeySoundFilesBuilder {
    private Map<PianoKeyNumber, File> files = new HashMap<>();

    public PianoKeySoundFilesBuilder(final PianoKeySoundFilesResolver pianoKeySoundFilesResolver) {
        PianoKeyNumber.forEachKey((PianoKeyNumber keyNumber) -> {
            var soundFile = pianoKeySoundFilesResolver.resolveFile(keyNumber);
            files.put(keyNumber, soundFile);
        });
    }

    public Map<PianoKeyNumber, File> getFiles() {
        return files;
    }
}
