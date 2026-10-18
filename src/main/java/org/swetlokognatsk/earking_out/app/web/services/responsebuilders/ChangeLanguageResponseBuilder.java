package org.swetlokognatsk.earking_out.app.web.services.responsebuilders;

import org.springframework.http.ResponseEntity;
import org.swetlokognatsk.earking_out.app.web.services.renderers.LanguagesListRenderer;
import org.swetlokognatsk.earking_out.core.domain.model.identity.Languages;
import org.swetlokognatsk.earking_out.infrastructure.annotations.WebComponent;
import lombok.AllArgsConstructor;

@WebComponent
@AllArgsConstructor
public class ChangeLanguageResponseBuilder {

    private final LanguagesListRenderer languagesListRenderer;

    public ResponseEntity<?> build(final Languages newLanguage) {
        var renderedLanguagesList = languagesListRenderer.renderPage(newLanguage);
        return ResponseEntity.ok(renderedLanguagesList);
    }
}
