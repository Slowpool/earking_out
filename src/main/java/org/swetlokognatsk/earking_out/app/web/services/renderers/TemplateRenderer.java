package org.swetlokognatsk.earking_out.app.web.services.renderers;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.swetlokognatsk.earking_out.infrastructure.annotations.WebComponent;
import org.thymeleaf.spring6.SpringTemplateEngine;
import lombok.AllArgsConstructor;
import org.thymeleaf.context.Context;

@WebComponent
@AllArgsConstructor
public class TemplateRenderer {

    private final SpringTemplateEngine templateEngine;

    public final String renderTemplate(final String template, final Map<String, Object> variables) {
        var context = new Context(Locale.getDefault(), variables);
        return templateEngine.process(template, context);
    }
}
