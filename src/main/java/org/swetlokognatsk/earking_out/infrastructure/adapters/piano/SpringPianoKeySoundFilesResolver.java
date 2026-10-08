package org.swetlokognatsk.earking_out.infrastructure.adapters.piano;

import java.io.File;
import java.nio.file.Path;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.Resource;
import org.swetlokognatsk.earking_out.core.domain.model.piano.key.PianoKeyNumber;
import org.swetlokognatsk.earking_out.core.ports.piano.PianoKeySoundFilesResolver;
import org.swetlokognatsk.earking_out.infrastructure.annotations.WebComponent;

@WebComponent
@Primary
public final class SpringPianoKeySoundFilesResolver implements PianoKeySoundFilesResolver, ApplicationContextAware {

    private static final Path RESOURCE_DIR = Path.of(System.getProperty("user.dir"), "src/main/resources");
    private static final String PIANO_KEY_LOCATION_TEMPLATE = "file:///org/swetlokognatsk/sounds/piano_keys/key%s.wav";

    private ApplicationContext ctx;

    public void setApplicationContext(final ApplicationContext applicationContext) throws BeansException {
        this.ctx = applicationContext;
    }

    public File resolveFile(final PianoKeyNumber keyNumber) {
        try {
            var resource = getResource(keyNumber);
            var file = resource.getFile();
            var osAbsolutePath = RESOURCE_DIR.resolve(file.getAbsolutePath().substring(1));
            return new File(osAbsolutePath.toString());
        } catch (Throwable e) {
            // TODO catch (Throwable e)
        }
        return null;
    }

    private Resource getResource(final PianoKeyNumber keyNumber) {
        var resourceLocation = toResourceLocation(keyNumber);
        var resource = ctx.getResource(resourceLocation);
        return resource;
    }

    private static String toResourceLocation(final PianoKeyNumber keyNumber) {
        String key = String.valueOf(keyNumber.value);
        return PIANO_KEY_LOCATION_TEMPLATE.formatted(key);
    }
}
