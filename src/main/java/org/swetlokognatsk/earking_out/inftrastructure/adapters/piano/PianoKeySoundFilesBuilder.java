package org.swetlokognatsk.earking_out.inftrastructure.adapters.piano;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import org.swetlokognatsk.earking_out.app.desktop.helpers.PianoKeysHelper;
import org.swetlokognatsk.earking_out.core.domain.model.piano.key.PianoKeyNumber;

public final class PianoKeySoundFilesBuilder {
    protected Map<PianoKeyNumber, File> files = new HashMap<>();

    // TODO Java resources - i think it solves the problem of direct access to file system
    public PianoKeySoundFilesBuilder() {
        PianoKeyNumber.forEachKey((PianoKeyNumber keyNumber) -> {
            String key = String.valueOf(keyNumber.value);
            // TODO path should be taken from config?
            String filePath = String.format("/piano_keys/key%s.wav", key);
            var hint = new File(filePath);
            files.put(keyNumber, hint);
        });
    }

    public Map<PianoKeyNumber, File> getFiles() {
        return files;
    }
}
