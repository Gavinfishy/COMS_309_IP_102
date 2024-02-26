package coms309;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
class WelcomeController {

    @GetMapping("/")
    public String welcome() {
        return "This is experiment one";
    }

    @GetMapping("/{name}/{major}")
    public String welcome(@PathVariable String name, @PathVariable String major) {
        return "My name is " + name + ", and my major is " + major;
    }
}
