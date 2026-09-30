package org.swetlokognatsk.earking_out.app.web.controllers;

import java.time.LocalDateTime;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class TestController {

    @GetMapping("/")
    public String home() {
        return "main";
    }

    // @PostMapping("/clicked")
    // public String clicked(final Model model) {
    //     model.addAttribute("now", LocalDateTime.now()
    //             .toString());
    //     return "clicked";
    // }

}
