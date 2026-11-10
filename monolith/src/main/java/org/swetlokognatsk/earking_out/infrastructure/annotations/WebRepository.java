package org.swetlokognatsk.earking_out.infrastructure.annotations;

import org.springframework.stereotype.Repository;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.AliasFor;
import org.swetlokognatsk.earking_out.SpringProfiles;
import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Repository
@Profile(SpringProfiles.WEB)
public @interface WebRepository {
    @AliasFor(annotation = Repository.class)
    String value() default "";
}
