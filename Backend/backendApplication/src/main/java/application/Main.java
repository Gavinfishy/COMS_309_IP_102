package application;

import application.Users.User;
import application.Users.UserRepository;
import application.Coach.Coach;
import application.Athlete.Athlete;
import application.Team.Team;
import application.Team.TeamRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
@EnableJpaRepositories
class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}
