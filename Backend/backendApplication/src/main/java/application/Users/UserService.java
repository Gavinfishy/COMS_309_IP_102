package application.Users;

import application.Athlete.Athlete;
import application.Athlete.AthleteRepository;
import application.Coach.Coach;
import application.Coach.CoachRepository;
import application.GymRat.GymRat;
import application.GymRat.GymRatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    GymRatRepository gymRatRepository;
    @Autowired
    AthleteRepository athleteRepository;
    @Autowired
    CoachRepository coachRepository;

    private String height;
    private String weight;

    public ResponseEntity<Object> login(String userName, String password){
        Map<String, String> response = new HashMap<>();

        User user = userRepository.findByUserName(userName);

        String accountType = user.getAccountType();
        height = "";
        weight = "";
        if (accountType.equals("Gym Rat")){
            GymRat gymRat = user.getGymRat();
            height = String.valueOf(gymRat.getHeight());
            weight = String.valueOf(gymRat.getWeight());
            response.put("height", height);
            response.put("weight", weight);
        }
        else if (accountType.equals("Athlete")) {
            Athlete athlete = user.getAthlete();
            height = String.valueOf(athlete.getHeight());
            weight = String.valueOf(athlete.getWeight());
            response.put("teamName", athlete.getTeamName());
            response.put("height", height);
            response.put("weight", weight);
        }
        else if (accountType.equals("Coach")) {
            Coach coach = user.getCoach();
            response.put("teamName", coach.getTeamName());
        }

        if (userRepository.existsByUserNameAndPassword(userName, password)){
            response.put("userName", userName);
            response.put("firstName", user.getFirstName());
            response.put("lastName", user.getLastName());
            response.put("accountType", accountType);
            response.put("isValid", "True");
        }
        else {
            response.put("isValid", "False");
        }
        return new ResponseEntity<Object>(response, HttpStatus.OK);
    }

    public ResponseEntity<Object> createUser(String userName, String password, String firstName, String lastName, String accountType, User user){
        Map<String, String> response = new HashMap<>();
        if (!userRepository.existsByUserName(userName)) {
            if (accountType.equals("Gym Rat") && !gymRatRepository.existsGymRatByUserName(userName)) {
                GymRat gymRat = new GymRat(userName);
                gymRatRepository.save(gymRat);
            }
            else if (accountType.equals("Athlete") && !athleteRepository.existsAthleteByUserName(userName)) {
                Athlete athlete = new Athlete(userName);
                athleteRepository.save(athlete);
            }
            else if (accountType.equals("Coach") && !coachRepository.existsCoachByUserName(userName)) {
                Coach coach = new Coach(userName);
                coachRepository.save(coach);
            }

            userRepository.save(user);
            response.put("userName", userName);
            response.put("firstName", firstName);
            response.put("lastName", lastName);
            response.put("accountType", accountType);
            response.put("isValid", "True");
        }
        else {
            response.put("isValid", "False");
        }
        return new ResponseEntity<Object> (response,HttpStatus.OK);
    }
}
