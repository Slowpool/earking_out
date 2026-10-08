package org.swetlokognatsk.earking_out.infrastructure.annotations;

import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Service;
import org.swetlokognatsk.earking_out.SpringProfiles;
import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Service
@Profile(SpringProfiles.TEST)
public @interface TestService {
    @AliasFor(annotation = Service.class)
    String value() default "";
}