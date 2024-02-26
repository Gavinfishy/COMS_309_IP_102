package application.Athlete;

import application.Team.Team;
import application.Team.TeamRepository;
import application.Workout.Workout;
import application.Workout.WorkoutRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class AthleteController {
    @Autowired
    AthleteRepository athleteRepository;

    @Autowired
    TeamRepository teamRepository;
    @Autowired
    WorkoutRepository workoutRepository;
    private final String success = "{\"message\":\"success\"}";

    private final String failure = "{\"message\":\"failure\"}";

//    @GetMapping(path = "/athletes")
//    List<Athlete> getAllAthletes() {
//        return athleteRepository.findAll();
//    }

    @GetMapping(path = "/athletes/{userName}")
    Athlete getAthleteByUserName(@PathVariable String userName) {
        return athleteRepository.findByUserName(userName);
    }

    @GetMapping("/athletes/{userName}/workoutHistory")
    public List<Workout> getWorkoutHistory(@PathVariable String userName) {
        Athlete athlete = athleteRepository.findByUserName(userName);
        if (athlete != null) {
            return athlete.getWorkoutHistory();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Athlete not found");
        }
    }

//    @PostMapping(path = "/athletes")
//    String createAthlete(@RequestBody Athlete athlete) {
//        if (athlete == null) {
//            return failure;
//        }
//        athleteRepository.save(athlete);
//        return success;
//    }

    @PutMapping("/athletes/logWorkout")
    public void logWorkout(@RequestBody Workout workoutInput) {
        Workout workout = workoutRepository.findByWorkoutName(workoutInput.getWorkoutName());
        if (workout != null) {
            workout.logWorkout(workoutInput.getActualReps(), workoutInput.getActualWeight());
            workoutRepository.save(workout);
        }
    }

    @PutMapping(path = "/athletes/setHeightWeight")
    public Athlete setHeightWeight(@RequestBody Athlete request) {
        String userName = request.getUserName();
        if (userName == null) {
            return null;
        }
        Athlete athlete = athleteRepository.findByUserName(userName);
        athlete.setHeight(request.getHeight());
        athlete.setWeight(request.getWeight());
        athleteRepository.save(athlete);
        return athleteRepository.findByUserName(userName);
    }

    @ResponseBody
    @PutMapping(path = "/athlete/joinTeam/{userName}/{teamName}/{athleteInviteId}")
    public ResponseEntity<Object> joinTeam(@PathVariable String userName, @PathVariable String teamName, @PathVariable int athleteInviteId) {
        Team team = teamRepository.findByTeamName(teamName);
        Athlete athlete = athleteRepository.findByUserName(userName);

        Map<String, String> response = new HashMap<>();

        if (athleteInviteId == team.getAthleteInviteId()){
            athlete.setTeamName(teamName);
            athleteRepository.save(athlete);
            response.put("teamName", athlete.getTeamName());
        }
        response.put("isValid", "True");
        return new ResponseEntity<Object>(response, HttpStatus.OK);
    }

    @PutMapping(path = "/athlete/leaveTeam/{userName}")
    @ResponseBody
    public ResponseEntity<Object> leaveTeam(@PathVariable String userName) {
        Athlete athlete = athleteRepository.findByUserName(userName);
        athlete.setTeamName(null);
        athleteRepository.save(athlete);

        Map<String, String> response = new HashMap<>();
        response.put("teamName", athlete.getTeamName());
        response.put("isValid", "True");

        return new ResponseEntity<Object>(response, HttpStatus.OK);
    }


//    @DeleteMapping(path = "/athletes/{userName}")
//    String deleteAthlete(@PathVariable String userName) {
//        athleteRepository.deleteByUserName(userName);
//        return success;
//    }

    @GetMapping(path = "/athletes/getWorkout/{userName}/{workoutName}")
    Workout getWorkout(@PathVariable String userName, @PathVariable String workoutName) {
        Athlete athlete = athleteRepository.findByUserName(userName);
        return athlete.getWorkout(workoutName);
    }
}
