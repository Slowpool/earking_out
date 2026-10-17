package org.swetlokognatsk.earking_out.app.web.controllers;

import org.springframework.session.SessionRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import org.swetlokognatsk.earking_out.app.web.GuestUserInterceptor;
import org.swetlokognatsk.earking_out.core.domain.model.identity.Languages;
import org.swetlokognatsk.earking_out.core.ports.di.DI;
import org.swetlokognatsk.earking_out.core.ports.identity.UserRepository;
import org.swetlokognatsk.earking_out.core.ports.identity.UserResolver;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import jakarta.servlet.http.Cookie;
import org.springframework.session.MapSession;
import static org.swetlokognatsk.earking_out.app.web.SessionAttributes.USER_ID;

@Controller
@AllArgsConstructor
public class MainController {

    @GetMapping("/")
    public ModelAndView home() {

        var userRepository = DI.get(UserRepository.class);
        var userResolver = DI.get(UserResolver.class);
        var user = userRepository.get(userResolver.getCurrentUserId());
        var selectedLanguage = user.getLang();
        
        var modelAndView = new ModelAndView("main");
        modelAndView.addObject("languages", Languages.values());
        modelAndView.addObject("selectedLanguage", selectedLanguage);
        return modelAndView;
    }
}
