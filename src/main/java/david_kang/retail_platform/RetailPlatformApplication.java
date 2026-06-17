package david_kang.retail_platform;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class RetailPlatformApplication {

	public static void main(String[] args) {
		SpringApplication.run(RetailPlatformApplication.class, args);
	}
}
