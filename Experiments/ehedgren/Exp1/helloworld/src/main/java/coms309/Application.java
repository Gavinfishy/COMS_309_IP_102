package coms309;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * PetClinic Spring Boot Application.
 * 
 * @author Vivek Bengre
 */

/**
 * put http://localhost:8080/ into web browser
 */

@SpringBootApplication
public class Application {
	
    public static void main(String[] args) throws Exception {
        SpringApplication.run(Application.class, args);
    }

}
