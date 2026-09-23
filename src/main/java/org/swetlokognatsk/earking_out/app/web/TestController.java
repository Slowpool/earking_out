package org.swetlokognatsk.earking_out.app.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TestController {

    @GetMapping("/")
    public String requestMethodName() {
        return "here i go";
    }

}
