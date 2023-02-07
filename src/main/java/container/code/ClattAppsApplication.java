package container.code;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
@OpenAPIDefinition
public class ClattAppsApplication {

	public static void main(String[] args) {
		SpringApplication.run(ClattAppsApplication.class, args);
	}

}
