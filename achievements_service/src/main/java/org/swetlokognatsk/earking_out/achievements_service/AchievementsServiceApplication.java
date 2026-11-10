package org.swetlokognatsk.earking_out.achievements_service;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class AchievementsServiceApplication {

	@RequestMapping("/")
	public ResponseEntity<String> test() {
		return ResponseEntity.ok("bazinga");
	}

}
