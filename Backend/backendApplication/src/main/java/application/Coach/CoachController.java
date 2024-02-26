package application.Coach;

import application.Athlete.Athlete;
import application.Athlete.AthleteRepository;
import application.Team.Team;
import application.Team.TeamRepository;
import application.Workout.Workout;
import application.Workout.WorkoutRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class CoachController {
    @Autowired
    CoachRepository coachRepository;
    @Autowired
    TeamRepository teamRepository;
    @Autowired
    AthleteRepository athleteRepository;
    @Autowired
    WorkoutRepository workoutRepository;
    private final String success = "{\"message\":\"success\"}";
    private final String failure = "{\"message\":\"failure\"}";

//    @GetMapping(path = "/coaches")
//    List<Coach> getAllCoaches() {
//        return coachRepository.findAll();
//    }

    @GetMapping(path = "/coaches/{userName}")
    Coach getCoachByUserName(@PathVariable String userName) {
        return coachRepository.findByUserName(userName);
    }
//    @PostMapping(path = "/coaches")
//    String createCoach(@RequestBody Coach coach) {
//        if (coach == null) {
//            return failure;
//        }
//        coachRepository.save(coach);
//        return success;
//    }

    @PostMapping("/coaches/assignWorkout")
    public void assignWorkout(@RequestBody Workout workout) {
        Athlete athlete = athleteRepository.findByUserName(workout.getUserName());
        if (athlete != null) {
            workoutRepository.save(workout);
        }
    }


    @PutMapping(path = "/coach/joinTeam/{userName}/{teamName}/{coachInviteId}")
    public ResponseEntity<Object> joinTeam(@PathVariable String userName, @PathVariable String teamName, @PathVariable int coachInviteId) {
        Team team = teamRepository.findByTeamName(teamName);
        Coach coach = coachRepository.findByUserName(userName);

        Map<String, String> response = new HashMap<>();
        if (coachInviteId == team.getCoachInviteId()){
            coach.setTeamName(teamName);
            coachRepository.save(coach);
            response.put("teamName", coach.getTeamName());
        }
        response.put("isValid", "True");
        return new ResponseEntity<Object>(response, HttpStatus.OK);
    }

    @PutMapping(path = "/coach/leaveTeam/{userName}")
    public ResponseEntity<Object> leaveTeam(@PathVariable String userName) {
        Coach coach = coachRepository.findByUserName(userName);
        coach.setTeamName(null);
        coachRepository.save(coach);

        Map<String, String> response = new HashMap<>();
        response.put("teamName", coach.getTeamName());
        response.put("isValid", "True");

        return new ResponseEntity<Object>(response, HttpStatus.OK);
    }

//    @DeleteMapping(path = "/coaches/{userName}")
//    String deleteCoach(@PathVariable String userName) {
//        coachRepository.deleteByUserName(userName);
//        return success;
//    }

}
