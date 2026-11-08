package org.swetlokognatsk.earking_out;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.jdbc.autoconfigure.DataSourceAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * EarkingOut SpringBootTest. Injects the context to app's {@link org.swetlokognatsk.earking_out.core.ports.di.DI } port
 * 
 */
@Target(ElementType.TYPE)
@Documented
@Retention (RetentionPolicy.RUNTIME)
@SpringBootTest
@ExtendWith(ContextInjectorExtension.class)
@EnableAutoConfiguration(exclude = DataSourceAutoConfiguration.class)
public @interface EOSpringBootTest {

}
