package org.swetlokognatsk.earking_out.app.web.configs;

import java.util.List;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.swetlokognatsk.earking_out.app.web.ExerciseArgumentResolver;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new ExerciseArgumentResolver());
    }

    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // TODO what's the difference between /** and /*
        registry.addResourceHandler("/sounds/**")
                .addResourceLocations("classpath:/org/swetlokognatsk/sounds/");
    }
}
