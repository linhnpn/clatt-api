package container.code;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.MultipartConfigElement;
import java.io.IOException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Objects;

@SpringBootApplication
@OpenAPIDefinition
public class ClattAppsApplication {

	public static void main(String[] args) {
		SpringApplication.run(ClattAppsApplication.class, args);
	}
}
