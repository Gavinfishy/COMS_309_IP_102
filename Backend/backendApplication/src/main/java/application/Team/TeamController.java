package application.Team;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import application.Athlete.Athlete;
import application.Athlete.AthleteRepository;
import application.Coach.Coach;
import application.Coach.CoachRepository;
import application.Users.User;
import application.Users.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class TeamController {
    @Autowired
    UserRepository userRepository;

    @Autowired
    TeamRepository teamRepository;

    @Autowired
    AthleteRepository athleteRepository;

    @Autowired
    CoachRepository coachRepository;

    @Autowired
    TeamService teamService;

    private final String success = "{\"message\":\"success\"}";

    private final String failure = "{\"message\":\"failure\"}";

//    @GetMapping(path = "/teams")
//    List<Team> getAllTeams() {
//        return teamRepository.findAll();
//    }
    @GetMapping(path = "/teams/{teamName}")
    Team getTeamByTeamName(@PathVariable String teamName) {
        return teamRepository.findByTeamName(teamName);
    }


    @GetMapping(path = "/team/getAthletes/{teamName}")
    Set<Athlete> getAthleteOnTeam(@PathVariable String teamName){
        return teamRepository.findByTeamName(teamName).getAthletes();
    }

    @GetMapping(path = "/team/getCoaches/{teamName}")
    Set<Coach> getCoachesOnTeam(@PathVariable String teamName){
        return teamRepository.findByTeamName(teamName).getCoaches();
    }

    @GetMapping(path = "/team/getUsers/{teamName}")
    Set<User> getUsersOnTeam(@PathVariable String teamName) {
        Set<User> users = new HashSet<>();
        for (Athlete athlete : teamRepository.findByTeamName(teamName).getAthletes()) {
            users.add(userRepository.findByUserName(athlete.getUserName()));
        }

        for (Coach coach : teamRepository.findByTeamName(teamName).getCoaches()) {
            users.add(userRepository.findByUserName(coach.getUserName()));
        }

        return users;
    }

    @ResponseBody
    @PostMapping(path = "/teams")
    String createTeam(@RequestBody Team team) {
        if(team == null) {
            return failure;
        }
        String teamName = team.getTeamName();
        if (!teamRepository.existsByTeamName(teamName)) {
            teamRepository.save(team);
            return success;
        }
        return failure;
    }

//    @PutMapping("/teams/{teamName}")
//    Team updateTeam(@PathVariable String teamName, @RequestBody Team request) {
//        Team team = teamRepository.findByTeamName(teamName);
//        if(team == null) {
//            return null;
//        }
//        teamRepository.save(request);
//        return teamRepository.findByTeamName(teamName);
//    }

//    @DeleteMapping(path = "/teams/{teamName}")
//    String deleteTeam(@PathVariable String teamName) {
//        teamRepository.deleteByTeamName(teamName);
//        return success;
//    }


}
