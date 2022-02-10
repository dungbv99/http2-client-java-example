package com.example.servingwebcontent;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Controller
@Slf4j
public class GreetingController {

	private final RestTemplate restTemplate;

	public GreetingController(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	@GetMapping("/")
	public String greeting(@RequestParam(name="name", required=false, defaultValue="World") String name, Model model) throws InterruptedException {
		String url = "http://localhost:8080/hello";
		UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(url);
		for(int i = 0; i < 100; i++){
			Thread.sleep(1000);
			ResponseEntity<String> response = restTemplate.exchange(uriBuilder.build().toUri(),
					HttpMethod.GET, null, String.class);
			if(i == 0 ){
				log.info("resp {}", response);
			}
		}

//		log.info("resp {}", response);
		return "greeting";
	}

	@GetMapping("/hello")
	public String hello(@RequestParam(name="name", required=false, defaultValue="World") String name, Model model) throws InterruptedException {
		model.addAttribute("name", name);
		Thread.sleep(1000);

		return "greeting";
	}

}
