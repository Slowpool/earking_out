package org.swetlokognatsk.earking_out.app.web.controllers;

import org.springframework.session.SessionRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.swetlokognatsk.earking_out.app.web.GuestUserInterceptor;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import jakarta.servlet.http.Cookie;
import org.springframework.session.MapSession;

@Controller
@AllArgsConstructor
public class MainController {

    @GetMapping("/")
    public String home(HttpSession session) {
        // TODO decouple user_id
        var userId = session.getAttribute(GuestUserInterceptor.USER_ID);

        return "main";
    }
}
