package application.Users;

import java.util.List;

import application.Athlete.Athlete;
import application.Athlete.AthleteRepository;
import application.Coach.Coach;
import application.Coach.CoachRepository;
import application.GymRat.GymRat;
import application.GymRat.GymRatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    private String success = "{\"message\":\"success\"}";
    private String failure = "{\"message\":\"failure\"}";
    private String userExists = "{\"message\":\"user exists\"}";

//    @GetMapping(path = "/users")
//    List<User> getAllUsers() {
//        return userRepository.findAll();
//    }

//    @GetMapping(path = "/users/{userName}")
//    User getUserByUserName( @PathVariable String userName) {
//        return userRepository.findByUserName(userName);
//    }


    @PostMapping (path = "/users/login")
    @ResponseBody
    ResponseEntity<Object> userLogin (@RequestBody User user){
        return userService.login(user.getUserName(), user.getPassword());
    }

    @PostMapping(path = "/users")
    @ResponseBody
    ResponseEntity<Object> createUser(@RequestBody User user){
        return userService.createUser(user.getUserName(), user.getPassword(), user.getFirstName(), user.getLastName(),
                user.getAccountType(), user);
    }

    @PutMapping("/users")
    User updateUser(@RequestBody User request){
        User user = userRepository.findByUserName(request.getUserName());
        if(user == null)
            return null;
        userRepository.save(request);
        return userRepository.findByUserName(request.getUserName());
    }

    @DeleteMapping(path = "/users/{userName}")
    String deleteUser(@PathVariable String userName){
        userRepository.deleteByUserName(userName);
        return success;
    }
}
