package org.swetlokognatsk.earking_out.infrastructure.annotations;

import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.stereotype.Service;
import static org.swetlokognatsk.earking_out.SpringApp.*;
import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Service
@ConditionalOnExpression(IS_WEB_BUILD)
public @interface WebService {

}
