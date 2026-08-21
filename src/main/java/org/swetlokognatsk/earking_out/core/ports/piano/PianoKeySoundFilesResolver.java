package org.swetlokognatsk.earking_out.core.ports.piano;

import java.io.File;
import org.swetlokognatsk.earking_out.core.domain.model.piano.key.PianoKeyNumber;

public interface PianoKeySoundFilesResolver {
    File resolveFile(final PianoKeyNumber keyNumber);

}
