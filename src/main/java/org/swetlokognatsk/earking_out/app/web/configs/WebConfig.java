package org.swetlokognatsk.earking_out.app.web.configs;

import java.util.List;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import static org.swetlokognatsk.earking_out.SpringProfiles.WEB;
import org.swetlokognatsk.earking_out.app.web.ExerciseArgumentResolver;
import org.swetlokognatsk.earking_out.app.web.GuestUserInterceptor;
import lombok.AllArgsConstructor;

@Configuration
@Profile(WEB)
@AllArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final GuestUserInterceptor guestUserInterceptor;

    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new ExerciseArgumentResolver());
    }

    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // TODO what's the difference between /** and /*
        registry.addResourceHandler("/sounds/**")
                .addResourceLocations("classpath:/org/swetlokognatsk/sounds/");
    }

    public void addInterceptors(final InterceptorRegistry registry) {
        registry.addInterceptor(guestUserInterceptor)
                .addPathPatterns("/");
    }
}
