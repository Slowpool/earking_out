package org.swetlokognatsk.earking_out.app.web.services.renderers;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import org.swetlokognatsk.earking_out.SpringApp;
import org.swetlokognatsk.earking_out.infrastructure.annotations.WebComponent;
import org.thymeleaf.spring6.SpringTemplateEngine;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.thymeleaf.context.Context;

@WebComponent
@AllArgsConstructor
public class TemplateRenderer {

    private final SpringTemplateEngine templateEngine;
    private final HttpSession session;

    public final String renderTemplate(final String template, final Map<String, Object> variables) {
        var locale = (Locale) session.getAttribute(SpringApp.LOCALE_SESSION_ATTR);
        var context = new Context(locale, variables);
        return templateEngine.process(template, context);
    }
}
