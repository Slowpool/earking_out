package org.swetlokognatsk.earking_out.app.web;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TestController {

    @GetMapping("/")
    public ResponseEntity<String> requestMethodName() {
        return ResponseEntity.ok("here i go");
    }

}
