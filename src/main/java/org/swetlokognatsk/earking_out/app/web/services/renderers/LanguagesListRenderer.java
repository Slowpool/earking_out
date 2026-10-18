package org.swetlokognatsk.earking_out.app.web.services.renderers;

import java.util.HashMap;
import org.swetlokognatsk.earking_out.core.domain.model.identity.Languages;
import org.swetlokognatsk.earking_out.infrastructure.annotations.WebComponent;
import lombok.AllArgsConstructor;

@WebComponent
@AllArgsConstructor
public class LanguagesListRenderer {

    private final TemplateRenderer templateRenderer;

    public String renderPage(final Languages newLanguage) {
        var variables = new HashMap<String, Object>();

        variables.put("languages", Languages.values());
        variables.put("selectedLanguage", newLanguage);

        return templateRenderer.renderTemplate("language_picker", variables);
    }

}
