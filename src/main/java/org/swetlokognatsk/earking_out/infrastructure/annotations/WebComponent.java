package org.swetlokognatsk.earking_out.infrastructure.annotations;

import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Component;
import org.swetlokognatsk.earking_out.SpringProfiles;
import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Component
@Profile(SpringProfiles.WEB)
public @interface WebComponent {
    @AliasFor(annotation = Component.class)
    String value() default "";
}
