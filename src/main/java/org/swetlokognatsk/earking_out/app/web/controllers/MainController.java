package org.swetlokognatsk.earking_out.app.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import org.swetlokognatsk.earking_out.core.domain.model.identity.Languages;
import org.swetlokognatsk.earking_out.core.ports.identity.UserRepository;
import org.swetlokognatsk.earking_out.core.ports.identity.UserResolver;
import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
public class MainController {

    private final UserRepository userRepository;
    private final UserResolver userResolver;

    @GetMapping("/")
    public ModelAndView home() {
        var modelAndView = new ModelAndView("main");
        // TODO refactoring
        var user = userRepository.get(userResolver.getCurrentUserId());
        modelAndView.addObject("userName", user.getName());
        modelAndView.addObject("userPictureUrl", user.getPictureUrl());
        modelAndView.addObject("userIsGuest", user.isGuest());
        modelAndView.addObject("languages", Languages.values());
        modelAndView.addObject("selectedLanguage", user.getLang());

        return modelAndView;
    }
}
